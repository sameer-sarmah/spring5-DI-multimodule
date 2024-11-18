package category.repository.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import category.entity.Category;
import category.entity.Product;
import category.repository.CategoryRepo;
import category.repository.ProductRepo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
@AutoConfigureTestDatabase(replace=Replace.NONE)
@DataJpaTest
//@SpringBootTest(classes = {TestConfig.class})
public class RepositoryTest {

	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	EntityManager entityManager;
	
	@BeforeEach
	public void setup() {
		Category beverage = new Category("1","Beverages","Refreshing");
		Product product =  new Product();
		product.setCategoryID("1");
		product.setProductName("Chai");
		product.setQuantityPerUnit("10 boxes x 20 bags");
		product.setUnitPrice("18.0000");
		Product savedProduct = productRepo.save(product);
		System.out.println(savedProduct.getProductID());
		List<Product> products =	productRepo.findAll();
		System.out.println(products.size());
		categoryRepo.save(beverage);
	}
	
	@Test
	public void readProductTest() {
		List<Product> products =	productRepo.findAll();
		Assertions.assertEquals(products.size(), 1);
		Assertions.assertEquals("Chai", products.get(0).getProductName());
	}
	
	@Test
	public void readCategoryTest() {
		List<Category> categories=  categoryRepo.findAll();
		Assertions.assertEquals(categories.size(), 1);
		Assertions.assertEquals("Beverages", categories.get(0).getCategoryName());
	}
	
	@Test
	public void readCategoryJPQLTest() {
		TypedQuery <Category> query=  entityManager.createQuery("SELECT c FROM Category c", Category.class);
		List<Category> categories= query.getResultList();
		Assertions.assertEquals(categories.size(), 1);
		Assertions.assertEquals("Beverages", categories.get(0).getCategoryName());
	}
}
