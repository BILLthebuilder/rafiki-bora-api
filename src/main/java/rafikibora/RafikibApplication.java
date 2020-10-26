package rafikibora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@EnableJpaAuditing
@SpringBootApplication
//@EnableSwagger2
public class RafikibApplication {

	public static void main(String[] args) {
		SpringApplication.run(RafikibApplication.class, args);
	}
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

//	@Bean
//	public Docket productApi() {
//		return new Docket(DocumentationType.SWAGGER_2).select()
//				.paths(PathSelectors.ant("/api/*"))
//				//package based configuration
//				.apis(RequestHandlerSelectors.basePackage("rafikibora"))
//				.build()
//				.apiInfo(apiInfo())
//				.securitySchemes(Arrays.asList(apiKey()));
//	}
//
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title("REST API")
//				.description("The REST API for demo swagger.").termsOfServiceUrl("")
//				.contact(new Contact("JEDIDAH WANGECI", "", "wangecimeruh@gmail.com"))
//				.license("Apache License Version 2.0")
//				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
//				.version("0.0.1")
//				.build();
//	}
//
//	private ApiKey apiKey() {
//		return new ApiKey("authkey", "Authorization", "header");
//	}


}
