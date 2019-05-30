package category.cassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import category.cassandra.table.ProductById;


@Repository
public interface ProductByIdRepo extends CassandraRepository<ProductById,String>{
	
}
