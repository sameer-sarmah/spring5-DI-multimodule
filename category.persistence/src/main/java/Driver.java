import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import category.entity.Category;
import category.persistence.config.MysqlDataSourceProvider;
import category.persistence.config.MysqlJPAConfig;
import category.repository.CategoryRepo;
 
public class Driver {
 
    public static void main(String[] args) {
    	testJPA() ;
    }
    
    static void testDataSource() {
    	ApplicationContext ctx = new AnnotationConfigApplicationContext
				(MysqlDataSourceProvider.class);
    	DataSource datasource = (DataSource)ctx.getBean("mysql", DataSource.class);
    	try {
			System.out.println(datasource.getConnection().getMetaData().getURL());
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    static void testJPA() {
    	ApplicationContext ctx = new AnnotationConfigApplicationContext
				(MysqlDataSourceProvider.class,MysqlJPAConfig.class);
    	CategoryRepo categoryRepo = (CategoryRepo)ctx.getBean("categoryRepo", CategoryRepo.class);
    	Category category =new Category("1","Beverage","Refreshing...");
    	categoryRepo.save(category);
    	List<Category> categories = categoryRepo.findAll();
    	categories.stream().forEach(c -> System.out.println(c.getCategoryName()));
    }
}