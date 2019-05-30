package category.persistence.cassandra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraEntityClassScanner;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import category.cassandra.table.Address;

@EnableCassandraRepositories(basePackages= {"category"})
@ComponentScan(basePackages= {"category"})
@Configuration
@PropertySource("classpath:cassandra.properties")
public class CassandraDbConfig extends AbstractCassandraConfiguration{

	@Value("${cassandra.host}")
	private String host;

	@Value("${cassandra.port}")
	private int port;
	
	@Value("${cassandra.namespace}")
	private String namespace;

	
    @Override
    protected String getKeyspaceName() {
        return namespace;
    }
 
    @Bean
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setJmxReportingEnabled(false);
        cluster.setContactPoints(host);
        cluster.setPort(port);
        return cluster;
    }
 
    
    @Override
    @Bean
    public CassandraMappingContext cassandraMapping() throws ClassNotFoundException {
        CassandraMappingContext ctx = new CassandraMappingContext();
        ctx.setInitialEntitySet(CassandraEntityClassScanner.scan("category.cassandra.table"));
        ctx.setUserTypeResolver(new SimpleUserTypeResolver(cluster().getObject(), getKeyspaceName()));
        return ctx;
    }
    
    @Bean
    public CassandraConverter converter() throws ClassNotFoundException {
        return new MappingCassandraConverter(cassandraMapping());
    }
    
    @Override
    public SchemaAction getSchemaAction() {
      return SchemaAction.CREATE_IF_NOT_EXISTS;
    }
}