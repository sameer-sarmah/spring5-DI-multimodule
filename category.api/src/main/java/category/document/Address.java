package category.document;

public class Address {

	private String street;
	private String city;
	private String country;
	private long ZIP;

	public Address(String street, String city, String country, long zIP) {
		super();
		this.street = street;
		this.city = city;
		this.country = country;
		ZIP = zIP;
	}

	public String getstreet() {
		return street;
	}

	public void setstreet(String street) {
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
