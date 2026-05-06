package com.yupi.yuaiagent.tools;

import com.yupi.yuaiagent.storage.OssStorageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PDFGenerationToolTest {

    @Test
    void generatePDF() {
        OssStorageService ossStorageService = Mockito.mock(OssStorageService.class);
        Mockito.when(ossStorageService.uploadAndDeleteLocalFile(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenAnswer(invocation -> {
                    String filePath = invocation.getArgument(0);
                    File file = new File(filePath);
                    // Simulate OSS behavior by removing the local file after "upload".
                    if (file.exists() && !file.delete()) {
                        throw new IllegalStateException("Failed to delete local file in test: " + filePath);
                    }
                    String fileName = invocation.getArgument(2);
                    return "https://example.com/" + fileName;
                });

        PDFGenerationTool tool = new PDFGenerationTool(ossStorageService);
        String fileName = "编程导航原创项目.pdf";
        String content = "编程导航原创项目 https://www.codefather.cn";
        String result = tool.generatePDF(fileName, content);
        assertNotNull(result);
        assertTrue(result.contains("https://example.com/"));
    }
}