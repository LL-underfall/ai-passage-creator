package com.yupi.template.config;

import com.volcengine.ark.runtime.model.images.generation.GenerateImagesRequest;
import com.volcengine.ark.runtime.model.images.generation.ResponseFormat;
import com.volcengine.ark.runtime.service.ArkService;
import lombok.Data;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * SeeDream 配置
 *
 * @author <a href="https://codefather.cn">编程导航学习圈</a>
 */
@Configuration
@ConfigurationProperties(prefix = "volcengine")
@Data
public class SeeDreamConfig {

    /**
     * API Key
     */
    private String ArkApiKey;

    /**
     * Base Url
     */
    private String baseUrl;

    /**
     * Model Name
     */
    private String model;

    private final ConnectionPool connectionPool = new ConnectionPool(5, 1, TimeUnit.SECONDS);

    private final Dispatcher dispatcher = new Dispatcher();

    @Bean
    public ArkServiceFactory arkServiceFactory() {
        return new ArkServiceFactory();
    }

    @Data
    public class ArkServiceFactory {
        private final String model = SeeDreamConfig.this.model;
        private final String responseFormat = ResponseFormat.Base64;

        public ArkService createArkService() {
            return ArkService.builder()
                    .baseUrl(baseUrl) // The base URL for model invocation
                    .dispatcher(dispatcher)
                    .connectionPool(connectionPool)
                    .apiKey(ArkApiKey)
                    .build();
        }

        public GenerateImagesRequest generateRequest(String prompt, String size) {
            return GenerateImagesRequest.builder()
                    .model(model) // Replace with Model ID
                    .prompt(prompt)
                    .size(size)
                    .sequentialImageGeneration("disabled")
                    .responseFormat(responseFormat)
                    .stream(false)
                    .watermark(false)
                    .build();
        }
    }
}