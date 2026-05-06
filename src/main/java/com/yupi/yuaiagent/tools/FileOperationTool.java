package com.yupi.yuaiagent.tools;

import cn.hutool.core.io.FileUtil;
import com.yupi.yuaiagent.constant.FileConstant;
import com.yupi.yuaiagent.storage.OssStorageService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

/**
 * 文件操作工具类（提供文件读写功能）
 */
public class FileOperationTool {

    private final String FILE_DIR = FileConstant.FILE_SAVE_DIR + "/file";
    private final OssStorageService ossStorageService;

    public FileOperationTool(OssStorageService ossStorageService) {
        this.ossStorageService = ossStorageService;
    }

    @Tool(description = "Read content from a file")
    public String readFile(@ToolParam(description = "Name of a file to read") String fileName) {
        String filePath = FILE_DIR + "/" + fileName;
        try {
            // 原本本地读取逻辑保留为注释
            // return FileUtil.readUtf8String(filePath);
            return ossStorageService.downloadText("file", fileName);
        } catch (Exception e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    @Tool(description = "Write content to a file")
    public String writeFile(@ToolParam(description = "Name of the file to write") String fileName,
                            @ToolParam(description = "Content to write to the file") String content
    ) {
        String filePath = FILE_DIR + "/" + fileName;

        try {
            // 原本本地写入逻辑保留为注释
            // FileUtil.mkdir(FILE_DIR);
            // FileUtil.writeUtf8String(content, filePath);
            // return "File written successfully to: " + filePath;
            FileUtil.mkdir(FILE_DIR);
            FileUtil.writeUtf8String(content, filePath);
            String url = ossStorageService.uploadAndDeleteLocalFile(filePath, "file", fileName);
            return "File uploaded successfully to: " + url;
        } catch (Exception e) {
            return "Error writing to file: " + e.getMessage();
        }
    }
}
