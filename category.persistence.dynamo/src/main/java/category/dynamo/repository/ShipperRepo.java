package category.dynamo.repository;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import category.dynamo.table.Shipper;


@Repository
public class ShipperRepo implements CrudRepository<Shipper, String>{
	private static final Logger LOGGER = LoggerFactory.getLogger(ShipperRepo.class);

	@Autowired
	private DynamoDBMapper mapper;

	@Override
	public Shipper save(Shipper entity) {
		mapper.save(entity);
		return mapper.load(Shipper.class, entity.getShipperID());
	}

	@Override
	public <S extends Shipper> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Shipper> findById(String id) {
		Shipper Shipper = mapper.load(Shipper.class, id);
		return Optional.of(Shipper);
	}

	@Override
	public boolean existsById(String id) {
		Shipper Shipper = mapper.load(Shipper.class, id);
		return Shipper != null;
	}

	@Override
	public Iterable<Shipper> findAll() {
		return null;
	}

	@Override
	public Iterable<Shipper> findAllById(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(String id) {
		DynamoDBDeleteExpression deleteExpression = new DynamoDBDeleteExpression();
		deleteExpression.addExpressionAttributeNamesEntry("customerID", id);
		Shipper Shipper = mapper.load(Shipper.class, id);
		mapper.delete(Shipper);
		
	}

	@Override
	public void delete(Shipper Shipper) {
		mapper.delete(Shipper);
	}

	@Override
	public void deleteAll(Iterable<? extends Shipper> iterable) {

		for(Shipper Shipper : iterable) {
			mapper.delete(Shipper);
		}
		
	}

	@Override
	public void deleteAll() {
		
		
	}
}
