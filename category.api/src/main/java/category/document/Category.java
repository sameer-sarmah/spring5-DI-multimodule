package category.document;

import java.util.List;

public class Category {

	private String categoryID;
	private String categoryName;
	private String description;


	public Category(String categoryID, String categoryName, String description) {
		this.categoryID = categoryID;
		this.categoryName = categoryName;
		this.description = description;
	}

	public Category() {
		super();
	}

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
