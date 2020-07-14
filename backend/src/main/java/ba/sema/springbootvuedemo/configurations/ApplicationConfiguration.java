package ba.sema.springbootvuedemo.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class ApplicationConfiguration
{
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Value("#{'${application.settings.cors.allowed-origins}'.split(',')}")
    private List<String> allowedOrigins;
    @Value("#{'${application.settings.cors.allowed-methods}'.split(',')}")
    private List<String> allowedMethods;

    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurerAdapter()
        {
            @Override
            public void addCorsMappings(CorsRegistry registry)
            {
                String[] origins = cleanList(allowedOrigins);
                String[] methods = cleanList(allowedMethods);
                logger.info("Setting application allowed origins: {}", (Object) origins);
                logger.info("Setting application allowed methods: {}", (Object) methods);

                registry.addMapping("/**")
                        .allowedOrigins(origins)
                        .allowedMethods(methods);

            }
        };
    }

    private String[] cleanList(List<String> list)
    {
        return list.stream().map(String :: trim).toArray(String[] :: new);
    }
}
