package category.persistence.mongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@ComponentScan("category")
@EnableMongoRepositories(basePackages = { "category.mongo.repository" }, mongoTemplateRef = "mongo-northwind")
public class MongoConfig {

	@Bean("mongo-northwind")
	@DependsOn("mongoClient")
	public MongoTemplate mongoTemplate() {
		// mongodb is installed in the localhost and by default credentials are not
		// required to perform CRUD
		MongoClient mongoClient = mongoClient();
		return new MongoTemplate(mongoClient, "mongo-northwind");
	}


	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create("mongodb://localhost");
	}

	@Bean
	public MongoDbFactory mongoDbFactory() {
		com.mongodb.MongoClient mongoClient = new com.mongodb.MongoClient("localhost", 27017);
		return new SimpleMongoDbFactory(mongoClient, "northwind");
	}

	@Bean
	MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}
}
