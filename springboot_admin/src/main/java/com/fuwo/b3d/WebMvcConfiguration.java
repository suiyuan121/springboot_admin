package com.fuwo.b3d;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.SpringDataWebConfiguration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jerry on 2017/5/31.
 */
@Configuration
public class WebMvcConfiguration extends SpringDataWebConfiguration {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/static/" + Application.RELEASE + "/**")
                .addResourceLocations("/static/")
                .setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS));
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/static/")
                .setCacheControl(CacheControl.noCache());
    }

    @Override
    public void addFormatters(final FormatterRegistry registry) {
        super.addFormatters(registry);
        registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm"));
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/").setViewName("redirect:/admin/modelpack/list");
        registry.addViewController("/accessDenied").setViewName("access_denied");
    }

    @Bean
    public PageableHandlerMethodArgumentResolver pageableResolver() {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver(sortResolver());
        resolver.setFallbackPageable(new PageRequest(0, 2));
        return resolver;
    }

}
