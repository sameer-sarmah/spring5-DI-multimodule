package category.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Product {
	@Id 
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String ProductID;
	private String ProductName;
	private String CategoryID;
	private String QuantityPerUnit;
	private String UnitPrice;

	public Product(String productID, String productName, String categoryID, String quantityPerUnit, String unitPrice) {
		super();
		this.ProductID = productID;
		this.ProductName = productName;
		this.CategoryID = categoryID;
		this.QuantityPerUnit = quantityPerUnit;
		this.UnitPrice = unitPrice;
	}

	
	
	public Product() {
		super();
	}



	public String getProductID() {
		return ProductID;
	}

	public String getProductName() {
		return ProductName;
	}

	public String getCategoryID() {
		return CategoryID;
	}

	public String getQuantityPerUnit() {
		return QuantityPerUnit;
	}

	public String getUnitPrice() {
		return UnitPrice;
	}
	
	
}
