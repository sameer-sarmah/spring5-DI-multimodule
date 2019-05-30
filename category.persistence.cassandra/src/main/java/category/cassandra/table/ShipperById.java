package category.cassandra.table;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class ShipperById {
	@PrimaryKeyColumn(name="shipperID",type=PrimaryKeyType.PARTITIONED)
	private String shipperID;
	private String companyName;
	private String phone;

	public ShipperById(String shipperID, String companyName, String phone) {
		super();
		this.shipperID = shipperID;
		this.companyName = companyName;
		this.phone = phone;
	}

	public String getShipperID() {
		return shipperID;
	}

	public void setShipperID(String shipperID) {
		this.shipperID = shipperID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
