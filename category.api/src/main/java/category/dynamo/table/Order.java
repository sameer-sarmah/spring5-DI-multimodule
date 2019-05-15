package category.dynamo.table;

import java.util.Date;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Order")
public class Order {
	
	@DynamoDBHashKey(attributeName = "orderID")
	String orderID;
	@DynamoDBRangeKey(attributeName="orderedDate")
	String orderedDate;
	@DynamoDBAttribute
	String shippedDate;
	@DynamoDBAttribute
	Address shippedAddress;
	@DynamoDBAttribute
	Shipper shipper;
	@DynamoDBAttribute
	Customer customer;
	@DynamoDBAttribute
	List<OrderDetail> orderitems;
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getOrderedDate() {
		return orderedDate;
	}
	public void setOrderedDate(String orderedDate) {
		this.orderedDate = orderedDate;
	}
	public String getShippedDate() {
		return shippedDate;
	}
	public void setShippedDate(String shippedDate) {
		this.shippedDate = shippedDate;
	}
	public Address getShippedAddress() {
		return shippedAddress;
	}
	public void setShippedAddress(Address shippedAddress) {
		this.shippedAddress = shippedAddress;
	}
	public Shipper getShipper() {
		return shipper;
	}
	public void setShipper(Shipper shipper) {
		this.shipper = shipper;
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
	
	
}
