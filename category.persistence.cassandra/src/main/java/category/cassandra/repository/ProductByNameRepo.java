package category.cassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import category.cassandra.table.ProductByName;

public interface ProductByNameRepo extends CassandraRepository<ProductByName,String> {

}
