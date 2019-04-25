package category.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import category.document.Customer;

@Repository("mongoCustomerRepo")
public interface CustomerRepo extends MongoRepository<Customer, String>{

}
