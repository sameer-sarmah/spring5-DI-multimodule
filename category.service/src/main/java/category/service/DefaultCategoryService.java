package category.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import api.ICategoryProvider;
import api.ICategoryService;
import category.entity.Category;
import category.entity.Product;

@Component(value="categoryService")
public class DefaultCategoryService implements ICategoryService {

	List<ICategoryProvider> categoriesProvider;
	
	@Qualifier("beverage")
	ICategoryProvider beverage;

	public DefaultCategoryService(List<ICategoryProvider> categories,ICategoryProvider beverage) {
		super();
		this.categoriesProvider = categories;
		this.beverage = beverage;
	}

	@Override
	public List<Category> findAll() {
		List<Category> categories = categoriesProvider.stream().map((provider) -> {
			return provider.getCategory();
		}).collect(Collectors.toList());
		return categories;
	}

	@Override
	public List<Product> findAllProductsByCategory(String categoryName) {
		List<ICategoryProvider> categories = categoriesProvider.stream().filter((category) -> {
			return category.getName().equalsIgnoreCase(categoryName);
		}).collect(Collectors.toList());
		List<Product> products = new ArrayList<>();
		if (categories != null && !categories.isEmpty()) {
			products = categories.get(0).getProducts();
		}
		return products;

	}

}
