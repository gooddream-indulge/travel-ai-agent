package com.yupi.yuaiagent.tools;

import cn.hutool.http.HttpUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 网页搜索工具
 */
public class WebSearchTool {

    // SearchAPI 的搜索接口地址
    // private static final String SEARCH_API_URL = "https://www.searchapi.io/api/v1/search";
    // Serper API 的搜索接口地址
    private static final String SEARCH_API_URL = "https://google.serper.dev/search";

    private final String apiKey;

    public WebSearchTool(String apiKey) {
        this.apiKey = apiKey;
    }

    @Tool(description = "Search for information from Baidu Search Engine")
    public String searchWeb(
            @ToolParam(description = "Search query keyword") String query) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("q", query);
        paramMap.put("api_key", apiKey);
        paramMap.put("engine", "baidu");
        try {
            // Serper API: POST JSON with X-API-KEY header
            JSONObject payload = JSONUtil.createObj()
                    .set("q", query)
                    .set("num", 5)
                    .set("hl", "zh-cn")
                    .set("gl", "cn");
            String response = HttpRequest.post(SEARCH_API_URL)
                    .header("X-API-KEY", apiKey)
                    .header("Content-Type", "application/json")
                    .body(payload.toString())
                    .execute()
                    .body();
            // 取出返回结果的前 5 条
            JSONObject jsonObject = JSONUtil.parseObj(response);
            // Serper 使用 organic 字段，保留兼容 organic_results
            JSONArray organicResults = jsonObject.getJSONArray("organic");
            if (organicResults == null) {
                organicResults = jsonObject.getJSONArray("organic_results");
            }
            if (organicResults == null || organicResults.isEmpty()) {
                return "No organic results returned from Serper.";
            }
            List<Object> objects = organicResults.subList(0, Math.min(5, organicResults.size()));
            // 拼接搜索结果为字符串
            String result = objects.stream().map(obj -> {
                JSONObject tmpJSONObject = (JSONObject) obj;
                return tmpJSONObject.toString();
            }).collect(Collectors.joining(","));
            return result;

            // 旧实现：SearchAPI GET + Baidu
            // String response = HttpUtil.get(SEARCH_API_URL, paramMap);
            // JSONObject jsonObject = JSONUtil.parseObj(response);
            // JSONArray organicResults = jsonObject.getJSONArray("organic_results");
            // List<Object> objects = organicResults.subList(0, 5);
            // String result = objects.stream().map(obj -> {
            //     JSONObject tmpJSONObject = (JSONObject) obj;
            //     return tmpJSONObject.toString();
            // }).collect(Collectors.joining(","));
            // return result;
        } catch (Exception e) {
            return "Error searching Serper: " + e.getMessage();
        }
    }
}
