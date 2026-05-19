package com.yupi.yuaiagent.tools;

import com.yupi.yuaiagent.constant.FileConstant;
import com.yupi.yuaiagent.storage.OssStorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
//matches 非空且必须完全匹配也就是填自己的值，才能执行测试
@EnabledIfEnvironmentVariable(named = "OSS_ENDPOINT", matches = ".+")
@EnabledIfEnvironmentVariable(named = "OSS_BUCKET", matches = "j.+")
@EnabledIfEnvironmentVariable(named = "OSS_ACCESS_KEY_ID", matches = ".+")
@EnabledIfEnvironmentVariable(named = "OSS_ACCESS_KEY_SECRET", matches = ".+")
class PDFGenerationToolOssIT {

    @Autowired
    private OssStorageService ossStorageService;

    @Test
    void generatePdf_uploadToOss_thenDeleteLocal() {
        PDFGenerationTool tool = new PDFGenerationTool(ossStorageService);
        String fileName = "oss-integration-test.pdf";
        String content = "杭州很好玩呀";

        String result = tool.generatePDF(fileName, content);
        assertNotNull(result);
        assertTrue(result.contains("http"));

        File localFile = new File(FileConstant.FILE_SAVE_DIR + "/pdf/" + fileName);
        assertFalse(localFile.exists(), "Local PDF should be deleted after upload");
    }
}

