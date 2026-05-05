package com.yupi.yuaiagent.agent;

import com.yupi.yuaiagent.advisor.MyLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * 鱼皮的 AI 超级智能体（拥有自主规划能力，可以直接使用）
 */
@Component
public class YuManus extends ToolCallAgent {

    public YuManus(ToolCallback[] allTools, ChatModel dashscopeChatModel) {
        super(allTools);
//      public YuManus(ToolCallback[] allTools,
//               @Nullable SyncMcpToolCallbackProvider mcpToolCallbackProvider,
//               ChatModel dashscopeChatModel) {
//               super(allTools, mcpToolCallbackProvider == null ? new ToolCallback[0] : mcpToolCallbackProvider.getToolCallbacks());
        this.setName("yuManus");
        String SYSTEM_PROMPT = """
                你是YuManus，一个全能的人工智能助手，旨在解决用户提出的任何任务。
                您可以使用各种工具来高效地完成复杂的请求。
                """;
        this.setSystemPrompt(SYSTEM_PROMPT);
        String NEXT_STEP_PROMPT = """
                根据用户需求，主动选择最合适的工具或工具组合。
                对于复杂的任务，您可以分解问题并逐步使用不同的工具来解决它。
                使用每个工具后，清楚地解释执行结果并建议下一步。
                如果你想在任何时候停止交互，请使用“terminate”工具/函数调用。
                """;
        this.setNextStepPrompt(NEXT_STEP_PROMPT);
        this.setMaxSteps(10);
        // 初始化 AI 对话客户端
        ChatClient chatClient = ChatClient.builder(dashscopeChatModel)
                //.defaultAdvisors(new MyLoggerAdvisor())
                .build();
        this.setChatClient(chatClient);
    }
}
