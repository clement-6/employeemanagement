package tek.getarrays.employeemanagement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tek.getarrays.employeemanagement.entity.Employee;
import tek.getarrays.employeemanagement.entity.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("tek.getarrays.employeemanagement"))
                .paths(PathSelectors.regex("/.*"))
                .build()
                .apiInfo(apiInfoMetaData())
                .protocols(new HashSet<>(List.of("https")))
                .ignoredParameterTypes(Employee.class, User.class);
    }

    private ApiInfo apiInfoMetaData() {
        return new ApiInfoBuilder().title("Employee Management")
                .description("API Endpoint Decoration")
                .contact(new Contact("Daniel", "https://www.dev-team.com/", "tekamtenda@gmail.com"))
                .license("Apache 2.0")
                .version("1.0.0")
                .build();
    }

}
