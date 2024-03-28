package com.warney.application.adapters.out;

import com.warney.application.domain.ports.GenerativeAiService;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "openAiChatApi", url = "${openai.base-url}", configuration = OpenAiChatService.Config.class)
public interface OpenAiChatService extends GenerativeAiService {

    @PostMapping("/v1/chat/completions")
    OpenAiCompletionRes chatCompletion(OpenAiCompletionReq req);

    @Override
    default String generativeContent(String objective, String context){
        String model = "gpt-3.5-turbo";
        List<Message> messages = List.of(
                new Message("system", objective),
                new Message("user", context)
        );
        OpenAiCompletionReq req = new OpenAiCompletionReq(model, messages);
        OpenAiCompletionRes response = chatCompletion(req);
        return response.choices().getFirst().message().content();
    }

    record OpenAiCompletionReq(String model, List<Message> messages){}
    record Message(String role, String content){}

    record OpenAiCompletionRes(List<Choice> choices){}
    record Choice(Message message){}

    class Config {
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${openai.api-key}") String apiKey){
            return requestTemplate -> requestTemplate.header(
                    HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiKey)
            );
        }
    }
}
