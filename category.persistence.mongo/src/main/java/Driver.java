import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import category.document.Address;
import category.document.Category;
import category.document.Customer;
import category.document.Product;
import category.document.Shipper;
import category.persistence.mongo.config.MongoConfig;
public class Driver {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
		MongoTemplate mongoRead = (MongoTemplate) ctx.getBean("northwind", MongoTemplate.class);
		MongoTemplate mongoWrite = (MongoTemplate) ctx.getBean("mongo-northwind", MongoTemplate.class);
		insertDenormalizedProductDocs( mongoRead, mongoWrite);
		insertCustomerDocs( mongoRead, mongoWrite);
		insertShipperDocs( mongoRead, mongoWrite);
	}
	
	/*db.customers.update({PostalCode:{$type:"double"}},{$set:{PostalCode:NumberInt(785005)}},{multi: true})
	 *db.customers.update({Country:{$type:"int"}},{$set:{Country:"India"}},{multi: true})
	db.customers.update({City:{$type:"int"}},{$set:{City:"Bangalore"}},{multi: true})
	db.customers.update({Address:{$type:"int"}},{$set:{Address:"Brookfield"}},{multi: true})
	
	After executing the above the below scripts should give empty results
	db.customers.find({PostalCode:{$type:"string"}})
	db.customers.find({Country:{$type:"int"}})
	db.customers.find({City:{$type:"int"}})
	db.customers.find({Address:{$type:"int"}})
	 * */
	public static void insertCustomerDocs(MongoTemplate mongoRead,MongoTemplate mongoWrite){
		MongoCollection<Document> customersToRead= mongoRead.getCollection("customers");
		boolean customerExist=mongoWrite.collectionExists("customer");
		if(!customerExist) {
			mongoWrite.createCollection(Customer.class);	
		}
		long countOfCustomersInTarget = mongoWrite.getCollection("customer").countDocuments();
		
		if(countOfCustomersInTarget == 0) {
			FindIterable<Document> iterator = customersToRead.find();
			iterator.forEach((Document customerDoc)->{
				try {
				String street =(String) customerDoc.get("Address");
				String city =(String) customerDoc.get("City");
				String country =(String) customerDoc.get("Country");
				long zip =(Integer) customerDoc.get("PostalCode");
				Address address= new Address(street,city, country, zip);

				String customerName =(String) customerDoc.get("ContactName");
				String customerID = (String)customerDoc.get("CustomerID");
				String phone = (String)customerDoc.get("Phone");
				Customer customer =new Customer(customerID.toString(),customerName,phone,address);
	            mongoWrite.insert(customer, "customer");
				}
				catch(Exception e) {
					System.err.println(e.getMessage());
				}
			});
		}
	}
	
	public static void insertShipperDocs(MongoTemplate mongoRead,MongoTemplate mongoWrite){
		MongoCollection<Document> shippersToRead= mongoRead.getCollection("shippers");
		boolean shipperExist=mongoWrite.collectionExists("shipper");
		if(!shipperExist) {
			mongoWrite.createCollection(Shipper.class);	
		}
		long countOfShippersInTarget = mongoWrite.getCollection("shipper").countDocuments();
		
		if(countOfShippersInTarget == 0) {
			FindIterable<Document> iterator = shippersToRead.find();
			iterator.forEach((Document shipperDoc)->{
				try {
				Integer shipperID =(Integer) shipperDoc.get("ShipperID");
				String companyName  =(String) shipperDoc.get("CompanyName");
				String phone  =(String) shipperDoc.get("Phone");
				Shipper shipper =new Shipper(shipperID.toString(),companyName,phone);
	            mongoWrite.insert(shipper, "shipper");
				}
				catch(Exception e) {
					System.err.println(e.getMessage());
				}
			});
		}
	}
	
	public static void insertDenormalizedProductDocs(MongoTemplate mongoRead,MongoTemplate mongoWrite){
		MongoCollection<Document> categoriesToRead= mongoRead.getCollection("categories");
		MongoCollection<Document> productsToRead= mongoRead.getCollection("products");
		boolean productExist=mongoWrite.collectionExists("product");
		if(!productExist) {
			mongoWrite.createCollection(Product.class);	
		}
		long countOfProductsInTarget = mongoWrite.getCollection("product").countDocuments();
		
		if(countOfProductsInTarget == 0) {
			FindIterable<Document> iterator = categoriesToRead.find();
			iterator.forEach((Document categoryDoc)->{
				
				String categoryName =(String) categoryDoc.get("CategoryName");
				Integer categoryID = (Integer)categoryDoc.get("CategoryID");
				String description = (String)categoryDoc.get("Description");
				Category category =new Category(categoryID.toString(),categoryName,description);
				System.out.println(categoryName);
				productsToRead.find(eq("CategoryID", categoryID)).forEach((Document productDoc)->{
					String productName = (String) productDoc.get("ProductName");
					Integer productID= (Integer)productDoc.get("ProductID");
					String quantityPerUnit= (String)productDoc.get("QuantityPerUnit");
					Double unitPrice= (Double)productDoc.get("UnitPrice");
	                Product product = new Product( productID.toString(),productName, category,quantityPerUnit,unitPrice);
	                mongoWrite.insert(product, "product");
				});
			});
		}
	}
	
}