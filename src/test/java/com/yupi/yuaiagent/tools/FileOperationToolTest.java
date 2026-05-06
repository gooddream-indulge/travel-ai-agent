package com.yupi.yuaiagent.tools;

import com.yupi.yuaiagent.storage.OssStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FileOperationToolTest {



    @Test
    void readFile(OssStorageService ossStorageService) {
        FileOperationTool fileOperationTool = new FileOperationTool(ossStorageService);
        String fileName = "编程导航.txt";
        String result = fileOperationTool.readFile(fileName);
        Assertions.assertNotNull(result);
    }

    @Test
    void writeFile(OssStorageService ossStorageService) {
        FileOperationTool fileOperationTool = new FileOperationTool(ossStorageService);
        String fileName = "编程导航.txt";
        String content = "https://www.codefather.cn 程序员编程学习交流社区";
        String result = fileOperationTool.writeFile(fileName, content);
        Assertions.assertNotNull(result);
    }
}
