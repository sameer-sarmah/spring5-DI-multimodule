package category.jms.sub.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import api.IDataSourceProvider;


@PropertySource("classpath:derby.properties")
@Configuration
public class DerbyDataSourceProvider implements IDataSourceProvider {

	@Value( "${derby.jdbc.url}" )
	private String jdbcUrl;
	
	@Value( "${derby.jdbc.driver}" )
	private String driver;
	
	@Value( "${derby.jdbc.username}" )
	private String username;
	
	@Value( "${derby.jdbc.password}" )
	private String password;
	
	
	@Override
	@Bean("derby")
	public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driver);
        dataSourceBuilder.url(jdbcUrl);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
	}
	
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
	
	

}
