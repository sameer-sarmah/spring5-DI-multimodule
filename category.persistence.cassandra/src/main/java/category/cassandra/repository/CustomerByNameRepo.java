package category.cassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import category.cassandra.table.CustomerByName;


@Repository
public interface CustomerByNameRepo extends CassandraRepository<CustomerByName, String>{
	CustomerByName findByCustomerName(String customerName);
}
