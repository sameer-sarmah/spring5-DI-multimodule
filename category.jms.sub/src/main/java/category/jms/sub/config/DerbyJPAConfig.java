package category.jms.sub.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DerbyJPAConfig {

	@Bean(name = "entityManagerFactoryDerby")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("derby") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("category.entity").persistenceUnit("northwindDerby").build();
	}

	@Bean(name = "transactionManagerDerby")
	public PlatformTransactionManager transactionManager(
			@Qualifier("entityManagerFactoryDerby") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
