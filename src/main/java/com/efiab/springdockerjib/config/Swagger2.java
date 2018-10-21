package com.efiab.springdockerjib.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

/**
 * The type Swagger2 config
 *
 * <p>Swagger2 class
 *
 * @author Sebex
 * @date 2018/10/20
 */
@Configuration
@EnableSwagger2
class Swagger2 {

  @Bean
  Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.efiab.springdockerjib.resource"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo())
        .securitySchemes(Arrays.asList(apiKey()));
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("SpringBoot Swagger2 RESTful APIs")
        .description("More info：http://blog.my.com/")
        .termsOfServiceUrl("http://blog.my.com/")
        .contact(new Contact("SebastianX", "www.wcc.com", "megabites@foxmail.com"))
        .version("1.0")
        .build();
  }

  private ApiKey apiKey() {
    return new ApiKey("apikey", "Authorization", "header");
  }
}
