package category.cassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import category.cassandra.table.ShipperById;


@Repository
public interface ShipperByIdRepo extends CassandraRepository<ShipperById, String>{
}
