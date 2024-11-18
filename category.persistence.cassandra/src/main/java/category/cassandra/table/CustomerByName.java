package category.cassandra.table;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;


@Table("CustomerByName")
public class CustomerByName {
	
	private String customerID;
	@PrimaryKeyColumn(name="customerName",type=PrimaryKeyType.PARTITIONED)
	private String customerName;
	private String phone;
	@CassandraType(type=Name.UDT,userTypeName="address")
	private Address address;
	
	public CustomerByName(String customerID, String customerName, String phone, Address address) {
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
