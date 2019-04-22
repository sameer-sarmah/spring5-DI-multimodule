package api;

import java.util.List;

import category.entity.Category;
import category.entity.Product;

public interface ICategoryService {

	List<Category> findAll();
	List<Product> findAllProductsByCategory(String categoryName);
}
