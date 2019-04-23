import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import category.persistence.config.MysqlDataSourceProvider;
 
public class Driver {
 
    public static void main(String[] args) {
    	ApplicationContext ctx = new AnnotationConfigApplicationContext
				(MysqlDataSourceProvider.class);
    	DataSource datasource = (DataSource)ctx.getBean("mysql", DataSource.class);
    	try {
			System.out.println(datasource.getConnection().getMetaData().getURL());
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
}