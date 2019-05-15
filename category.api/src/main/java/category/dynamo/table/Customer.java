package category.dynamo.table;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/*
 * We are embedding Address in Customer
 * 1.Our use case is that Address will not be queried independently thus need not be a independent entity,that is Address is a dependent entity
 * 2.Address is not volatile
 * 3.Customer and his Address, both are queried together
 * 4.Our use case is that one customer can have only one address 
 * */
@DynamoDBDocument
@DynamoDBTable(tableName = "Customer")
public class Customer {

	@DynamoDBHashKey(attributeName = "customerID")
	private String customerID;
	@DynamoDBAttribute
	private String customerName;
	@DynamoDBAttribute
	private String phone;
	@DynamoDBAttribute
	private Address address;
	
	public Customer(String customerID, String customerName, String phone, Address address) {
		super();
		this.customerID = customerID;
		this.customerName = customerName;
		this.phone = phone;
		this.address = address;
	}
	
	public Customer() {
		
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	
}
