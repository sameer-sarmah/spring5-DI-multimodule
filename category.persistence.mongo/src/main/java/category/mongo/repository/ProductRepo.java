package category.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import category.document.Product;

@Repository("mongoProductRepo")
public interface ProductRepo extends MongoRepository<Product, String>{

}
