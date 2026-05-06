package com.yupi.yuaiagent.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * OSS configuration properties.
 */
@Data
@Component
@ConfigurationProperties(prefix = "oss")
public class OssProperties {

    private String endpoint;
    private String bucket;
    private String accessKeyId;
    private String accessKeySecret;
    private String baseDir = "ai-agent";
    private String publicUrlPrefix;
}

