//package rafikibora.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * This class configures the default Swagger Documentation
// */
//@Configuration
//@EnableSwagger2
//@Import(BeanValidatorPluginsConfiguration.class)
//public class Swagger2Config
//{
//    /**
//     * Configures what to document using Swagger
//     *
//     * @return A Docket which is the primary interface for Swagger configuration
//     */
//    @Bean
//    public Docket api()
//    {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors
//                        .basePackage("rafikibora"))
//                .paths(PathSelectors.regex("/.*"))
//                .build()
//                .apiInfo(apiEndPointsInfo());
//    }
//
//    private ApiInfo apiEndPointsInfo()
//    {
//        return new ApiInfoBuilder().title("Rafikibora MicroFinance Project")
//                .description("A microfinance project")
//                .contact(new Contact("John Paul Mulongo",
//                        "http://rafikibora.com",
//                        "mulongojohnpaul@gmail.com"))
//                .license("MIT")
//                .licenseUrl("https://github.com/rafiki-bora-test/blob/master/LICENSE")
//                .version("1.0.0")
//                .build();
//    }
//}