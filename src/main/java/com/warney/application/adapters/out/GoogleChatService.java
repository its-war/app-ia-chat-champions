package com.warney.application.adapters.out;

import com.warney.application.domain.ports.GenerativeAiService;
import feign.FeignException;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "GEMINI", matchIfMissing = true)
@FeignClient(name = "googleChatApi", url = "${gemini.base-url}", configuration = GoogleChatService.Config.class)
public interface GoogleChatService extends GenerativeAiService {

    @PostMapping("/v1beta/models/gemini-pro:generateContent")
    GoogleGeminiRes textOnlyInput(GoogleGeminiReq req);

    @Override
    default String generativeContent(String objective, String context){
        String prompt = """
                %s
                %s
                """.formatted(objective, context);

        GoogleGeminiReq req = new GoogleGeminiReq(
                List.of(new Content(List.of(new Part(prompt))))
        );
        try {
            GoogleGeminiRes response = textOnlyInput(req);
            return response.candidates().getFirst().content().parts().getFirst().text();
        } catch (FeignException httpError){
            return "Erro de comunicação com o Google Gemini";
        } catch (Exception e){
            return "Erro inesperado no sistema.";
        }
    }

    record GoogleGeminiReq(List<Content> contents){}
    record Content(List<Part> parts){}
    record Part(String text){}
    record GoogleGeminiRes(List<Candidate> candidates){}
    record Candidate(Content content){}

    class Config {
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${gemini.api-key}") String apiKey){
            return requestTemplate -> requestTemplate.query("key", apiKey);
        }
    }
}
