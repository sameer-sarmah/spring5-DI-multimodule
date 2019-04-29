package category.mongo.txn;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;

import category.document.Category;
import category.document.Product;


@Service("transactionalService")
public class TransactionalService {

	private MongoTemplate mongoTemplate;

	private MongoClient mongoClient;

	public TransactionalService(@Qualifier("mongo-northwind") MongoTemplate mongoTemplate, MongoClient mongoClient) {
		super();
		this.mongoTemplate = mongoTemplate;
		this.mongoClient = mongoClient;
	}

	@Transactional
	public void insertProduct() {
		Category category = new Category("999", "Apparel", "Sports clothing");
		Product adidas = new Product("999","Adidas",category, "1 Tshirt", 10.0);
		mongoTemplate.insert(adidas);
//	for cluster replicated mongo db		
//		mongoTemplate.setWriteConcern(WriteConcern.MAJORITY);
//		ClientSession session=mongoClient.startSession();
//		MongoTemplate txnTemplate=mongoTemplate.withSession(session);
//		session.startTransaction();
//		txnTemplate.insert(adidas);
//		session.commitTransaction();
		
	}

	@Transactional
	public void deleteProduct() {
		mongoTemplate.findAndRemove(new Query(Criteria.where("_id").is("999")), Product.class);
		
//		mongoTemplate.setWriteConcern(WriteConcern.MAJORITY);
//		ClientSession session=mongoClient.startSession();
//		MongoTemplate txnTemplate=mongoTemplate.withSession(session);
//		session.startTransaction();
//		txnTemplate.findAndRemove(new Query(Criteria.where("_id").is("999")), Product.class);
//		session.commitTransaction();
	}

}
