package com.winterhold;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    @Bean
    public LocaleResolver localeResolver(){
        Locale indonesia = new Locale("id","ID");
        SessionLocaleResolver session = new SessionLocaleResolver();
        session.setDefaultLocale(indonesia);
        return session;//membuat locale untuk thymeleaf
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/author/homePage");
        registry.addViewController("/author").setViewName("forward:/author/index");
        registry.addViewController("/customer").setViewName("forward:/customer/index");
        registry.addViewController("/loan").setViewName("forward:/loan/index");
        registry.addViewController("/category").setViewName("forward:/category/index");

    }

}
