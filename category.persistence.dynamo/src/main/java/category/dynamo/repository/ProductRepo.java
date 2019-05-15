package category.dynamo.repository;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDeleteExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import category.dynamo.table.Product;
import category.dynamo.table.Product;


@Repository
public class ProductRepo implements CrudRepository<Product, String>{
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRepo.class);

	@Autowired
	private DynamoDBMapper mapper;

	@Override
	public Product save(Product entity) {
		mapper.save(entity);
		return mapper.load(Product.class, entity.getProductID());
	}

	@Override
	public <S extends Product> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Product> findById(String id) {
		Product Product = mapper.load(Product.class, id);
		return Optional.of(Product);
	}

	@Override
	public boolean existsById(String id) {
		Product Product = mapper.load(Product.class, id);
		return Product != null;
	}

	@Override
	public Iterable<Product> findAll() {
		return null;
	}

	@Override
	public Iterable<Product> findAllById(Iterable<String> ids) {
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
		Product Product = mapper.load(Product.class, id);
		mapper.delete(Product);
		
	}

	@Override
	public void delete(Product Product) {
		mapper.delete(Product);
	}

	@Override
	public void deleteAll(Iterable<? extends Product> iterable) {

		for(Product Product : iterable) {
			mapper.delete(Product);
		}
		
	}

	@Override
	public void deleteAll() {
		
		
	}
}
