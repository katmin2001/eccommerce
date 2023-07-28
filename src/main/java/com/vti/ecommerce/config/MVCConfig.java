package com.vti.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
    public void addResourceHandlers(final ResourceHandlerRegistry registry){
        registry.addResourceHandler("/image/**").addResourceLocations("classpath:/image/");
        registry.addResourceHandler("/category-images/**").addResourceLocations("classpath:/category-images/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + "upload/");
    }
}
