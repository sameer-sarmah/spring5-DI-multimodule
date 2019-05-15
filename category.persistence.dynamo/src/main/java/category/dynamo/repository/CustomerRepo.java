package category.dynamo.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.ConsistentReads;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;

import category.dynamo.table.Customer;

import org.springframework.data.repository.CrudRepository;


@Repository
public class CustomerRepo implements CrudRepository<Customer, String>{
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRepo.class);

	@Autowired
	private DynamoDBMapper mapper;

	@Override
	public Customer save(Customer entity) {
		mapper.save(entity);
		return mapper.load(Customer.class, entity.getCustomerID());
	}

	@Override
	public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Customer> findById(String id) {
		Customer customer = mapper.load(Customer.class, id);
		return Optional.of(customer);
	}

	@Override
	public boolean existsById(String id) {
		Customer customer = mapper.load(Customer.class, id);
		return customer != null;
	}

	@Override
	public Iterable<Customer> findAll() {
		return null;
	}

	@Override
	public Iterable<Customer> findAllById(Iterable<String> ids) {
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
		Customer customer = mapper.load(Customer.class, id);
		mapper.delete(customer);
		
	}

	@Override
	public void delete(Customer customer) {
		mapper.delete(customer);
	}

	@Override
	public void deleteAll(Iterable<? extends Customer> iterable) {

		for(Customer customer : iterable) {
			mapper.delete(customer);
		}
		
	}

	@Override
	public void deleteAll() {
		
		
	}
}
