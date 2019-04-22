package category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api.ICategoryService;
import category.entity.Category;
import category.entity.Product;

@RestController
public class CategoryController {

	@Qualifier("categoryService")
	@Autowired
	private ICategoryService categoryService;
	
	@ResponseBody
    @GetMapping("/category")
    public List<Category> getCategories(){
    	return categoryService.findAll();
    }
	
	
	@ResponseBody
    @GetMapping("/products/{name}")
    public List<Product> getProductsByCategory(@PathVariable("name") String name){
    	return categoryService.findAllProductsByCategory(name);
    }
	
}
