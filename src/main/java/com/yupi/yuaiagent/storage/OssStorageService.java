package com.yupi.yuaiagent.storage;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * OSS storage service for uploading and reading files.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OssStorageService {

    private final OssProperties properties;

    public String uploadAndDeleteLocalFile(String filePath, String category, String fileName) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalStateException("Local file not found: " + filePath);
        }
        String objectKey = buildObjectKey(category, fileName);
        OSS client = buildClient();
        try {
            client.putObject(properties.getBucket(), objectKey, file);
        } finally {
            client.shutdown();
        }
        String url = buildPublicUrl(objectKey);
        if (!FileUtil.del(file)) {
            log.warn("Failed to delete local file after OSS upload: {}", filePath);
        }
        log.info("生成的文件url：{}" + url);
        return url;
    }

    public String downloadText(String category, String fileName) {
        String objectKey = buildObjectKey(category, fileName);
        OSS client = buildClient();
        try (InputStream input = client.getObject(properties.getBucket(), objectKey).getObjectContent()) {
            return IoUtil.read(input, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to read OSS object: " + objectKey, e);
        } finally {
            client.shutdown();
        }
    }

    public String buildObjectKey(String category, String fileName) {
        String baseDir = StrUtil.blankToDefault(properties.getBaseDir(), "ai-agent");
        if (StrUtil.isBlank(category)) {
            return baseDir + "/" + fileName;
        }
        return baseDir + "/" + category + "/" + fileName;
    }

    private OSS buildClient() {
        return new OSSClientBuilder().build(
                properties.getEndpoint(),
                properties.getAccessKeyId(),
                properties.getAccessKeySecret()
        );
    }

    private String buildPublicUrl(String objectKey) {
        String prefix = properties.getPublicUrlPrefix();
        if (StrUtil.isNotBlank(prefix)) {
            return normalizePrefix(prefix) + "/" + objectKey;
        }
        String endpoint = properties.getEndpoint();
        String host = endpoint.replaceFirst("^https?://", "");
        String protocol = endpoint.startsWith("https://") ? "https://" : "http://";
        if (!endpoint.startsWith("http")) {
            protocol = "https://";
        }
        return protocol + properties.getBucket() + "." + host + "/" + objectKey;
    }

    private String normalizePrefix(String prefix) {
        return prefix.endsWith("/") ? prefix.substring(0, prefix.length() - 1) : prefix;
    }
}
