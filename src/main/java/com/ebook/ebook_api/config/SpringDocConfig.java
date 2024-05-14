package com.ebook.ebook_api.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("OpenApi的Swagger")
                        .description("springdoc-openapi-starter-webmvc-ui")
                        .version("springdoc-openapi v2.5.0")
                        .license(new License()
                                .name("SprigBoot3.2.5")
                                //url，这里写的是SpringBoot的地址
                                .url("https://spring.io/projects/spring-boot"))
                ).externalDocs(new ExternalDocumentation()
                        .description("springdoc-openapi v2.5.0")
                        //url,写的是springdoc-openapi的地址
                        .url("https://springdoc.org/#google_vignette"));
    }
}

