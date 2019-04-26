package category.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import category.document.Order;

@Repository("mongoOrderRepo")
public interface OrderRepo extends MongoRepository<Order, String>{

}
