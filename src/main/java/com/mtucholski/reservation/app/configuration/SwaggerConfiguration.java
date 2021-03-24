package com.mtucholski.reservation.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@Configuration
@ComponentScan(basePackages = "com.mtucholski.reservation.app.controller")
public class SwaggerConfiguration {

    Contact contact = new Contact("Mateusz Tucholski",
            "https://github.com",
            "mateusz.tucholski@atoxik.net");

    @Bean
    public Docket customDocket() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }


    private ApiInfo getApiInfo() {

        return new ApiInfo(
                "REST clinic backend Api docs",
                "This is rest api for clinic reservation",
                "1.0",
                "clinic terms",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                Collections.emptyList());
    }
}
