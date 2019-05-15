package category.dynamo.table;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBDocument
@DynamoDBTable(tableName = "Shipper")
public class Shipper {
	@DynamoDBHashKey(attributeName = "shipperID")
	private String shipperID;
	@DynamoDBAttribute
	private String companyName;
	@DynamoDBAttribute
	private String phone;

	public Shipper(String shipperID, String companyName, String phone) {
		super();
		this.shipperID = shipperID;
		this.companyName = companyName;
		this.phone = phone;
	}
	
	public Shipper() {
		
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
