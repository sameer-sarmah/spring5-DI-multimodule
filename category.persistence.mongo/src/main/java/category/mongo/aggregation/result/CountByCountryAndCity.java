package category.mongo.aggregation.result;

public class CountByCountryAndCity {
	private String country, city;
	private int count;

	public CountByCountryAndCity(String country, String city, int count) {
		super();
		this.country = country;
		this.city = city;
		this.count = count;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "[country=" + country + ", city=" + city + ", count=" + count + "]";
	}

	
	
}
