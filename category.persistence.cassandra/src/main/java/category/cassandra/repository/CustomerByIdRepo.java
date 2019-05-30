package category.cassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import category.cassandra.table.CustomerById;


@Repository
public interface CustomerByIdRepo extends CassandraRepository<CustomerById, String>{
	
}
