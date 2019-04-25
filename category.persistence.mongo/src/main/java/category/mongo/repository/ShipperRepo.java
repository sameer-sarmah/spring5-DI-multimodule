package category.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import category.document.Shipper;

@Repository("mongoShipperRepo")
public interface ShipperRepo extends MongoRepository<Shipper, String>{

}
