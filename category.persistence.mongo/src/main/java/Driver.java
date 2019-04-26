import static com.mongodb.client.model.Filters.eq;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.bson.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import category.document.Address;
import category.document.Category;
import category.document.Customer;
import category.document.Order;
import category.document.OrderDetail;
import category.document.Product;
import category.document.Shipper;
import category.persistence.mongo.config.MongoConfig;

public class Driver {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfig.class);
		MongoTemplate mongoRead = (MongoTemplate) ctx.getBean("northwind", MongoTemplate.class);
		MongoTemplate mongoWrite = (MongoTemplate) ctx.getBean("mongo-northwind", MongoTemplate.class);
		insertDenormalizedProductDocs(mongoRead, mongoWrite);
		insertCustomerDocs(mongoRead, mongoWrite);
		insertShipperDocs(mongoRead, mongoWrite);
		insertDenormalizedOrderDocs(mongoRead, mongoWrite);
	    

	}

	/*
	 * db.customers.update({PostalCode:{$type:"double"}},{$set:{PostalCode:NumberInt
	 * (785005)}},{multi: true})
	 * db.customers.update({Country:{$type:"int"}},{$set:{Country:"India"}},{multi:
	 * true})
	 * db.customers.update({City:{$type:"int"}},{$set:{City:"Bangalore"}},{multi:
	 * true})
	 * db.customers.update({Address:{$type:"int"}},{$set:{Address:"Brookfield"}},{
	 * multi: true})
	 * 
	 * After executing the above the below scripts should give empty results
	 * db.customers.find({PostalCode:{$type:"string"}})
	 * db.customers.find({Country:{$type:"int"}})
	 * db.customers.find({City:{$type:"int"}})
	 * db.customers.find({Address:{$type:"int"}})
	 */
	public static void insertCustomerDocs(MongoTemplate mongoRead, MongoTemplate mongoWrite) {
		MongoCollection<Document> customersToRead = mongoRead.getCollection("customers");
		boolean customerExist = mongoWrite.collectionExists("customer");
		if (!customerExist) {
			mongoWrite.createCollection(Customer.class);
		}
		long countOfCustomersInTarget = mongoWrite.getCollection("customer").countDocuments();

		if (countOfCustomersInTarget == 0) {
			FindIterable<Document> iterator = customersToRead.find();
			Consumer<Document> consumer =(Document customerDoc) -> {
				try {
					Customer customer = createCustomer(customerDoc);
					mongoWrite.insert(customer, "customer");
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			};
			iterator.forEach(consumer);
		}
	}

	public static void insertShipperDocs(MongoTemplate mongoRead, MongoTemplate mongoWrite) {
		MongoCollection<Document> shippersToRead = mongoRead.getCollection("shippers");
		boolean shipperExist = mongoWrite.collectionExists("shipper");
		if (!shipperExist) {
			mongoWrite.createCollection(Shipper.class);
		}
		long countOfShippersInTarget = mongoWrite.getCollection("shipper").countDocuments();

		if (countOfShippersInTarget == 0) {
			FindIterable<Document> iterator = shippersToRead.find();
			Consumer<Document> consumer =(Document shipperDoc) -> {
				try {
					Shipper shipper = createShipper(shipperDoc);
					mongoWrite.insert(shipper, "shipper");
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			};
			iterator.forEach(consumer);
		}
	}

	public static void insertDenormalizedProductDocs(MongoTemplate mongoRead, MongoTemplate mongoWrite) {
		MongoCollection<Document> categoriesToRead = mongoRead.getCollection("categories");
		MongoCollection<Document> productsToRead = mongoRead.getCollection("products");
		boolean productExist = mongoWrite.collectionExists("product");
		if (!productExist) {
			mongoWrite.createCollection(Product.class);
		}
		long countOfProductsInTarget = mongoWrite.getCollection("product").countDocuments();

		if (countOfProductsInTarget == 0) {
			FindIterable<Document> iterator = categoriesToRead.find();
			Consumer<Document> consumer = (Document categoryDoc) -> {

				String categoryName = (String) categoryDoc.get("CategoryName");
				Integer categoryID = (Integer) categoryDoc.get("CategoryID");
				String description = (String) categoryDoc.get("Description");
				Category category = new Category(categoryID.toString(), categoryName, description);
				System.out.println(categoryName);
				Consumer<Document> productsConsumer =  (Document productDoc) -> {
					String productName = (String) productDoc.get("ProductName");
					Integer productID = (Integer) productDoc.get("ProductID");
					String quantityPerUnit = (String) productDoc.get("QuantityPerUnit");
					Double unitPrice = (Double) productDoc.get("UnitPrice");
					Product product = new Product(productID.toString(), productName, category, quantityPerUnit,
							unitPrice);
					mongoWrite.insert(product, "product");
				};
				productsToRead.find(eq("CategoryID", categoryID)).forEach(productsConsumer);
			};
			iterator.forEach(consumer);
		}
	}
/*
 * 
db.orders.find({ShipPostalCode:{$type:"string"}})
db.orders.find({ShipCountry:{$type:"int"}})
db.orders.find({ShipCity:{$type:"int"}})
db.orders.find({ShipAddress:{$type:"int"}})
db.orders.update({ShipPostalCode:{$type:"string"}},{$set:{ShipPostalCode:NumberInt(0)}},{multi: true})
db.orders.update({ShipCountry:{$type:"int"}},{$set:{ShipCountry:""}},{multi: true})
db.orders.update({ShipCity:{$type:"int"}},{$set:{ShipCity:""}},{multi: true})
db.orders.update({ShipAddress:{$type:"int"}},{$set:{ShipAddress:""}},{multi: true})
 * 
 * */
	public static void insertDenormalizedOrderDocs(MongoTemplate mongoRead, MongoTemplate mongoWrite) {
		MongoCollection<Document> ordersToRead = mongoRead.getCollection("orders");
		MongoCollection<Document> orderDetailsToRead = mongoRead.getCollection("orderdetails");
		MongoCollection<Document> customersToRead = mongoRead.getCollection("customers");
		MongoCollection<Document> shippersToRead = mongoRead.getCollection("shippers");

		boolean orderExist = mongoWrite.collectionExists("order");
		if (!orderExist) {
			mongoWrite.createCollection(Order.class);
		}
		long countOfOrdersInTarget = mongoWrite.getCollection("order").countDocuments();

		if (countOfOrdersInTarget == 0) {
			FindIterable<Document> iterator = ordersToRead.find();
			Consumer<Document> consumer = (Document orderDoc) -> {
				List<OrderDetail> orderitems = new ArrayList<>();
				Order order =new Order();
				Integer orderID = (Integer) orderDoc.get("OrderID");
				order.setOrderID(orderID.toString());
				String customerID =(String)orderDoc.get("CustomerID");
				Consumer<Document> orderDetailsConsumer =(Document orderDetailDoc) -> {
					orderitems.add(createOrderDetail(orderDetailDoc));
				};
				orderDetailsToRead.find(eq("OrderID", orderID)).forEach(orderDetailsConsumer);
				order.setOrderitems(orderitems);
				
				Consumer<Document> customerConsumer =(Document customerDoc) -> {
					order.setCustomer(createCustomer(customerDoc));
				};
				customersToRead.find(eq("CustomerID", customerID)).forEach(customerConsumer);
				int shipperID = (Integer) orderDoc.get("ShipVia");
				
				Consumer<Document> shipperConsumer =(Document shipperDoc) -> {
					order.setShipper(createShipper(shipperDoc));
				};
				shippersToRead.find(eq("ShipperID", shipperID)).forEach(shipperConsumer);
				
				String street = (String) orderDoc.get("ShipAddress");
				String city = (String) orderDoc.get("ShipCity");
				String country = (String) orderDoc.get("ShipCountry");
				long zip = (Integer) orderDoc.get("ShipPostalCode");
				Address address = new Address(street, city, country, zip);
				order.setShippedAddress(address);
				
			    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");  
			    Date shippedDate,orderedDate;
				try {
					String shippedDateStr = (String) orderDoc.get("ShippedDate");
					String orderedDateStr = (String) orderDoc.get("OrderDate");
					shippedDate = formatter.parse(shippedDateStr);
					orderedDate = formatter.parse(orderedDateStr);
					order.setShippedDate(shippedDate);
					order.setOrderedDate(orderedDate);
				} catch (ParseException e) {
					e.printStackTrace();
				} 
				mongoWrite.insert(order, "order");
			};
			iterator.forEach(consumer);
		}
	}

	private static Customer createCustomer(Document customerDoc) {
		String street = (String) customerDoc.get("Address");
		String city = (String) customerDoc.get("City");
		String country = (String) customerDoc.get("Country");
		long zip = (Integer) customerDoc.get("PostalCode");
		Address address = new Address(street, city, country, zip);

		String customerName = (String) customerDoc.get("ContactName");
		String customerID = (String) customerDoc.get("CustomerID");
		String phone = (String) customerDoc.get("Phone");
		Customer customer = new Customer(customerID.toString(), customerName, phone, address);
		return customer;
	}

	private static Shipper createShipper(Document shipperDoc) {
		Integer shipperID = (Integer) shipperDoc.get("ShipperID");
		String companyName = (String) shipperDoc.get("CompanyName");
		String phone = (String) shipperDoc.get("Phone");
		Shipper shipper = new Shipper(shipperID.toString(), companyName, phone);
		return shipper;
	}

	private static OrderDetail createOrderDetail(Document orderDetailDoc) {
		Integer productID = (Integer) orderDetailDoc.get("ProductID");
		int quantity = (Integer) orderDetailDoc.get("Quantity");
		Object discountObj = orderDetailDoc.get("Discount");
		double discount = 0;
		if(discountObj instanceof Integer) {
			discount = (Integer) orderDetailDoc.get("Discount");
		}
		else if(discountObj instanceof Double) {
			discount = (Double) orderDetailDoc.get("Discount");
		}
		double unitPrice = (Double) orderDetailDoc.get("UnitPrice");
		OrderDetail orderDetail = new OrderDetail(productID.toString(), quantity, discount, unitPrice);
		return orderDetail;
	}

	/*
	 * Sql to get the total amount per order
	 * 
	 * SELECT order_summary.order_id,SUM(order_summary.total) as total FROM (SELECT
	 * od.order_id,od.quantity*od.unit_price as total FROM northwind.order_details
	 * od JOIN northwind.products p on od.product_id=p.id JOIN northwind.orders o on
	 * o.id=od.order_id group by od.order_id,p.product_name) as order_summary group
	 * by order_summary.order_id;
	 */

}