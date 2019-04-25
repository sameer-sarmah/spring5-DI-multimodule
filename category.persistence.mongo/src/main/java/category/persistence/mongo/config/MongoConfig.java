package category.persistence.mongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories (basePackages= {"category.mongo.repository"},mongoTemplateRef = "mongo-northwind")
public class MongoConfig {

	@Bean("mongo-northwind")
	public MongoTemplate mongoTemplate() {
		//mongodb is installed in the localhost and by default credentials are not required to perform CRUD
		MongoClient mongoClient = MongoClients.create("mongodb://localhost");
		return new MongoTemplate(mongoClient,"mongo-northwind");
	}
	
	@Bean("northwind")
	public MongoTemplate mongoTemplate2() {
		//mongodb is installed in the localhost and by default credentials are not required to perform CRUD
		MongoClient mongoClient = MongoClients.create("mongodb://localhost");
		return new MongoTemplate(mongoClient,"Northwind");
	}
	
}
