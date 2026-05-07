package com.yupi.yuaiagent.tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.esotericsoftware.minlog.Log;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFontFactory.EmbeddingStrategy;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.yupi.yuaiagent.constant.FileConstant;
import com.yupi.yuaiagent.storage.OssStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * PDF 生成工具
 */
@Slf4j
public class PDFGenerationTool {

    private final OssStorageService ossStorageService;

    public PDFGenerationTool(OssStorageService ossStorageService) {
        this.ossStorageService = ossStorageService;
    }

    @Tool(description = "Generate a PDF file with given content", returnDirect = false)
    public String generatePDF(
            @ToolParam(description = "Name of the file to save the generated PDF") String fileName,
            @ToolParam(description = "Content to be included in the PDF") String content) {
        String fileDir = FileConstant.FILE_SAVE_DIR + "/pdf";
        // 原始文件名路径（保留逻辑）
        // String filePath = fileDir + "/" + fileName;
        // 生成带时间戳的文件名
        String safeFileName = (fileName == null || fileName.trim().isEmpty()) ? "document.pdf" : fileName.trim();
        int dot = safeFileName.lastIndexOf('.');
        String base = (dot > 0) ? safeFileName.substring(0, dot) : safeFileName;
        String ext = (dot > 0) ? safeFileName.substring(dot) : "";
        String ts = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileNameWithTs = base + "_" + ts + ext;
        String filePath = fileDir + "/" + fileNameWithTs;

        try {
            // 创建目录
            FileUtil.mkdir(fileDir);
            // 创建 PdfWriter 和 PdfDocument 对象
            try (PdfWriter writer = new PdfWriter(filePath);
                 PdfDocument pdf = new PdfDocument(writer);
                 Document document = new Document(pdf)) {
                // 自定义字体（需要人工下载字体文件到特定目录）
//                String fontPath = Paths.get("src/main/resources/static/fonts/simsun.ttf")
//                        .toAbsolutePath().toString();
                // 优先使用本地中文字体，缺失时降级到内置字体
                String fontPath = resolveFontPath();
                PdfFont font;
                if (StrUtil.isNotBlank(fontPath) && FileUtil.exist(fontPath)) {
                    font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H,
                            EmbeddingStrategy.PREFER_EMBEDDED);
                } else {
                    font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H");
                }
                document.setFont(font);
                // 创建段落
                Paragraph paragraph = new Paragraph(content);
                // 添加段落并关闭文档
                document.add(paragraph);
            }
            // 原本本地返回逻辑保留为注释
            // return "PDF generated successfully to: " + filePath;
            // String url = ossStorageService.uploadAndDeleteLocalFile(filePath, "pdf", fileName);
            String url = ossStorageService.uploadAndDeleteLocalFile(filePath, "pdf", fileNameWithTs);
            return "PDF uploaded successfully to: " + url;
        } catch (Exception e) {
            return "Error generating PDF: " + e.getMessage();
        }
    }

    private String resolveFontPath() {
        // 优先读取 classpath 资源中的字体文件
        String classpathFont = "static/fonts/simhei.ttf";
        try {
            ClassPathResource resource = new ClassPathResource(classpathFont);
            if (resource.exists()) {
                //log.info("找到字体了");
                return resource.getFile().getAbsolutePath();
            }
        } catch (IOException ignored) {
            // Ignore: fall back to common system paths.
        }
        // 常见系统字体路径（按需补充）
        List<String> candidates = Arrays.asList(
                Paths.get("src/main/resources/static/fonts/simsun.ttf").toAbsolutePath().toString(),
                "C:/Windows/Fonts/simsun.ttc",
                "C:/Windows/Fonts/simsun.ttf"
        );
        for (String candidate : candidates) {
            if (FileUtil.exist(candidate)) {
                return candidate;
            }
        }
        return null;
    }
}
