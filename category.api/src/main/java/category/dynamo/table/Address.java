package category.dynamo.table;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class Address {

	@DynamoDBAttribute
	private String street;
	@DynamoDBAttribute
	private String city;
	@DynamoDBAttribute
	private String country;
	@DynamoDBAttribute
	private long ZIP;

	public Address(String street, String city, String country, long ZIP) {
		super();
		this.street = street;
		this.city = city;
		this.country = country;
		this.ZIP = ZIP;
	}

	public Address() {
		
	}
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public long getZIP() {
		return ZIP;
	}

	public void setZIP(long zIP) {
		ZIP = zIP;
	}

}
