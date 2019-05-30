package category.cassandra.table;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType(value="customer")
public class Customer {
	private String customerID;
	private String customerName;
	private String phone;
	private Address address;
	public Customer(String customerID, String customerName, String phone, Address address) {
		super();
		this.customerID = customerID;
		this.customerName = customerName;
		this.phone = phone;
		this.address = address;
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
