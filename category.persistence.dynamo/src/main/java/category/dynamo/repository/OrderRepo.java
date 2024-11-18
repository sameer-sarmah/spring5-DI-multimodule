package category.dynamo.repository;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import category.dynamo.table.Order;


@Repository
public class OrderRepo implements CrudRepository<Order, String>{
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderRepo.class);

	@Autowired
	private DynamoDBMapper mapper;

	@Override
	public Order save(Order entity) {
		mapper.save(entity);
		return mapper.load(Order.class, entity.getOrderID() ,entity.getOrderedDate());
	}

	@Override
	public <S extends Order> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Order> findById(String id) {
		Order Order = mapper.load(Order.class, id);
		return Optional.of(Order);
	}

	@Override
	public boolean existsById(String id) {
		Order Order = mapper.load(Order.class, id);
		return Order != null;
	}

	@Override
	public Iterable<Order> findAll() {
		return null;
	}

	@Override
	public Iterable<Order> findAllById(Iterable<String> ids) {
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
		Order Order = mapper.load(Order.class, id);
		mapper.delete(Order);
		
	}

	@Override
	public void delete(Order Order) {
		mapper.delete(Order);
	}

	@Override
	public void deleteAll(Iterable<? extends Order> iterable) {

		for(Order Order : iterable) {
			mapper.delete(Order);
		}
		
	}

	@Override
	public void deleteAll() {
		
		
	}

	@Override
	public void deleteAllById(Iterable<? extends String> ids) {
		// TODO Auto-generated method stub
		
	}
}
