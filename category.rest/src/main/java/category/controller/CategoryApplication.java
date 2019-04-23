package category.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"category"})
@EntityScan(basePackages = {"category"})
@ComponentScan(basePackages = {"api","client","category"})
@EnableJms
public class CategoryApplication  extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CategoryApplication.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(CategoryApplication.class, args);
	}

}
