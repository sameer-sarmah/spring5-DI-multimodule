package category.cassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import category.cassandra.table.ShipperByName;

public interface ShipperByNameRepo  extends CassandraRepository<ShipperByName, String>{

}
