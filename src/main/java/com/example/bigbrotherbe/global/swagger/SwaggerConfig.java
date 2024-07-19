//package com.example.bigbrotherbe.global.swagger;
//
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import org.springdoc.core.models.GroupedOpenApi;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//            .info(new Info().title("My API")
//                .version("1.0")
//                .description("My API Description"));
//    }
//
//    @Bean
//    public GroupedOpenApi publicApi() {
//        return GroupedOpenApi.builder()
//            .group("springshop-public")
//            .pathsToMatch("/bigBrother/**")
//            .build();
//    }
//}
