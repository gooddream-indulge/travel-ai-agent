package com.yupi.yuaiagent.agent;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YuManusTest {

    @Resource
    private YuManus yuManus;

    @Test
    public void run() {
//        String userPrompt = """
//                我的另一半居住在深圳大学附近，请帮我找到 10 公里内合适的约会地点，
//                并结合一些网络图片，制定一份详细的约会计划，约会从下午2点开始，持续到晚上8点，包含吃饭、娱乐、散步等活动，
//                并以 PDF 格式输出，要求生成中文PDF
//                """;
        String userPrompt = """
                搜索杭州的一个景点，编写一个txt文件给我，我要url链接。
                """;
        String answer = yuManus.run(userPrompt);
        Assertions.assertNotNull(answer);
    }
}