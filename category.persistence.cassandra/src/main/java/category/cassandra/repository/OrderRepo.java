package category.cassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import category.cassandra.table.Order;

@Repository
public interface OrderRepo extends CassandraRepository<Order, String>{

}
