package com.sena.JustMe.config.copy;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Indica que cualquier URL que empiece con /uploads/
        // buscará archivos en la carpeta física "uploads" del proyecto
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}
