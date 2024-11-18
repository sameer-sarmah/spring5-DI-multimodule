package category.cassandra.table;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

/*
 * We are embedding Category in Product
 * 1.Our use case is that category will not be queried independently thus need not be a independent entity,that is category is a dependent entity
 * 2.Category is not volatile
 * 3.When products are queried then their category is also desired,that is both are queried together
 * 4.Our use case is that one product can belong to only one category 
 * */

@Table
public class ProductById {
	@PrimaryKeyColumn(name="productID",type=PrimaryKeyType.PARTITIONED)
	private String productID;
	private String productName;
	@CassandraType(type=Name.UDT,userTypeName="category")
	private Category category;
	private String quantityPerUnit;
	@PrimaryKeyColumn(name="unitPrice",type=PrimaryKeyType.CLUSTERED)
	private double unitPrice;
	
	public ProductById(String productID, String productName, Category category, String quantityPerUnit, double unitPrice) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.category = category;
		this.quantityPerUnit = quantityPerUnit;
		this.unitPrice = unitPrice;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getQuantityPerUnit() {
		return quantityPerUnit;
	}

	public void setQuantityPerUnit(String quantityPerUnit) {
		this.quantityPerUnit = quantityPerUnit;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	
	
}
