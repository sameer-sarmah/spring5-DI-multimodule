package category.cassandra.table;

import java.util.Date;
import java.util.List;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType.Name;
import com.datastax.driver.core.LocalDate;

@Table
public class Order {
	
	@PrimaryKeyColumn(name="orderID",type=PrimaryKeyType.PARTITIONED)
	String orderID;
	@CassandraType(type=Name.TIMESTAMP)
	@PrimaryKeyColumn(name="orderedDate",type=PrimaryKeyType.CLUSTERED)
	Date orderedDate;
	@CassandraType(type=Name.TIMESTAMP)
	Date shippedDate;
	@CassandraType(type=Name.UDT,userTypeName="address")
	Address shippedAddress;
	@CassandraType(type=Name.UDT,userTypeName="shipper")
	Shipper shipper;
	@CassandraType(type=Name.UDT,userTypeName="customer")
	Customer customer;
	@CassandraType(type=Name.UDT,userTypeName="orderDetail")
	List<OrderDetail> orderitems;
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public Date getOrderedDate() {
		return orderedDate;
	}
	public void setOrderedDate(Date orderedDate) {
		this.orderedDate = orderedDate;
	}
	public Date getShippedDate() {
		return shippedDate;
	}
	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}
	public Address getShippedAddress() {
		return shippedAddress;
	}
	public void setShippedAddress(Address shippedAddress) {
		this.shippedAddress = shippedAddress;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<OrderDetail> getOrderitems() {
		return orderitems;
	}
	public void setOrderitems(List<OrderDetail> orderitems) {
		this.orderitems = orderitems;
	}
	
	public Shipper getShipper() {
		return shipper;
	}
	public void setShipper(Shipper shipper) {
		this.shipper = shipper;
	}
}
