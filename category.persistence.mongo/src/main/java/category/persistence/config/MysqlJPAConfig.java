package category.persistence.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MysqlJPAConfig {

	@Primary
	@Bean(name = "entityManagerFactoryMysql")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("mysql") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("category.entity").persistenceUnit("northwindMysql").build();
	}

	@Primary
	@Bean(name = "transactionManagerMysql")
	public PlatformTransactionManager transactionManager(
			@Qualifier("entityManagerFactoryMysql") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
