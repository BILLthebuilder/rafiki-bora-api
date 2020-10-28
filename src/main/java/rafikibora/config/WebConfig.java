/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package rafikibora.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.*;

/**
*
* @author bkariuki
*/
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter implements WebMvcConfigurer {

@Override
public void addCorsMappings(CorsRegistry registry) {
registry.addMapping("/**").allowedOrigins("*");
}

@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
registry.addResourceHandler("swagger-ui.html")
.addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
registry.addResourceHandler("/webjars/**")
.addResourceLocations("classpath:/META-INF/resources/webjars/");
super.addResourceHandlers(registry);
}

@Override
public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
configurer.defaultContentType(MediaType.APPLICATION_JSON);
}
}
