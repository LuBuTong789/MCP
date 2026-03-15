package com.example.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaaLLMConfig {

//    @Value("$spring.ai.dashscope.api-key")
//    private String apiKey;
//
//    @Bean
//    public DashScopeApi dashScopeApi()
//    {
//        return DashScopeApi.builder().apiKey(apiKey).build();
//    }

    private final String DEEPSEEK_MODEL = "deepseek-v3";
    private final String QWEN_MODEL = "qwen-max";

    @Bean(name = "deepseek")
    public ChatModel deepSeek(){
       return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("aliQwen-api")).build())
               .defaultOptions(DashScopeChatOptions.builder().withModel(DEEPSEEK_MODEL).build())
               .build();
    }

    @Bean(name = "qwen")
    public ChatModel qwen(){
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("aliQwen-api")).build())
                .defaultOptions(DashScopeChatOptions.builder().withModel(QWEN_MODEL).build())
                .build();
    }
    @Bean(name = "deepseekChatClient")
    public ChatClient deepSeekChatClient(@Qualifier("deepseek")  ChatModel deepseek){
        return
                ChatClient.builder(deepseek)
                        //.defaultOptions(ChatOptions.builder().model(DEEPSEEK_MODEL).build())
                        .build();
    }
    @Bean(name = "qwenChatClient")
    public ChatClient qwenChatClient(@Qualifier("qwen")  ChatModel qwen){
        return
                ChatClient.builder(qwen)
                        //.defaultOptions(ChatOptions.builder().model(QWEN_MODEL).build())
                        .build();
    }
}
