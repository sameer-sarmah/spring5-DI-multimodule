import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

import category.dynamo.repository.CustomerRepo;
import category.dynamo.repository.OrderRepo;
import category.dynamo.repository.ProductRepo;
import category.dynamo.repository.ShipperRepo;
import category.dynamo.table.Address;
import category.dynamo.table.Category;
import category.dynamo.table.Customer;
import category.dynamo.table.Order;
import category.dynamo.table.OrderDetail;
import category.dynamo.table.Product;
import category.dynamo.table.Shipper;
import category.persistence.dynamo.config.DynamoDbConfig;
import category.persistence.mongo.config.MongoConfig;

public class Runner {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(DynamoDbConfig.class, MongoConfig.class);
//		insertOrder(ctx);
//		createTable(ctx,Customer.class);
//		createTable(ctx,Shipper.class);
//		createTable(ctx,Product.class);
//		createTable(ctx,Order.class);
//		insertOrder(ctx);
		scanCustomer(ctx);

	}

	private static void queryCustomer(ApplicationContext ctx) {
		DynamoDBMapper mapper = ctx.getBean(DynamoDBMapper.class);
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS("MORGK"));

        DynamoDBQueryExpression<Customer> queryExpression = new DynamoDBQueryExpression<Customer>()
        		 											.withKeyConditionExpression("customerID = :val1")
												            .withExpressionAttributeValues(eav);
        List<Customer> customers = mapper.query(Customer.class, queryExpression);
        System.out.println(customers.size());
	}
	
	private static void scanCustomer(ApplicationContext ctx) {
		DynamoDBMapper mapper = ctx.getBean(DynamoDBMapper.class);
        Condition equalityCondition= new Condition().withComparisonOperator(ComparisonOperator.EQ)
        		.withAttributeValueList(Collections.singletonList(new AttributeValue("Alexander Feuer")));
        DynamoDBScanExpression queryExpression = new DynamoDBScanExpression()
        		 											.withFilterConditionEntry("customerName",equalityCondition);									           
        List<Customer> customers = mapper.scan(Customer.class, queryExpression);
        System.out.println(customers.size());
        Condition containsCondition= new Condition().withComparisonOperator(ComparisonOperator.CONTAINS)
        		.withAttributeValueList(Collections.singletonList(new AttributeValue("Alexander")));
												           
        customers = mapper.scan(Customer.class, queryExpression);
        System.out.println(customers.size());
	}
	

	
	private static void createTable(ApplicationContext ctx,Class klass) {
		DynamoDBMapper mapper = ctx.getBean(DynamoDBMapper.class);
		AmazonDynamoDB dynamoDB = ctx.getBean(AmazonDynamoDB.class);
		CreateTableRequest tableRequest = mapper.generateCreateTableRequest(klass);
		tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
		dynamoDB.createTable(tableRequest);
	}

	
	private static void insertCustomers(ApplicationContext ctx) {
		
		CustomerRepo repo = ctx.getBean(CustomerRepo.class);
		category.mongo.repository.CustomerRepo mongoCustomerRepo = ctx.getBean("mongoCustomerRepo",
				category.mongo.repository.CustomerRepo.class);
		Iterable<category.document.Customer> iterable = mongoCustomerRepo.findAll();
		for (category.document.Customer customerFromMongo : iterable) {
			System.err.println(customerFromMongo.getCustomerName());
			repo.save(convertMongoCustomerToDynamoCustomer(customerFromMongo));
		}
	}
	

	
	private static void insertShipper(ApplicationContext ctx) {
		
		ShipperRepo repo = ctx.getBean(ShipperRepo.class);
		category.mongo.repository.ShipperRepo mongoCustomerRepo = ctx.getBean("mongoShipperRepo",
				category.mongo.repository.ShipperRepo.class);
		Iterable<category.document.Shipper> iterable = mongoCustomerRepo.findAll();
		for (category.document.Shipper shipperFromMongo : iterable) {
			repo.save(convertMongoShipperToDynamoShipper(shipperFromMongo));
		}
	}

	
	private static void insertProduct(ApplicationContext ctx) {
		
		ProductRepo repo = ctx.getBean(ProductRepo.class);
		category.mongo.repository.ProductRepo mongoProductRepo = ctx.getBean("mongoProductRepo",
				category.mongo.repository.ProductRepo.class);
		Iterable<category.document.Product> iterable = mongoProductRepo.findAll();
		for (category.document.Product productFromMongo : iterable) {
			repo.save(convertMongoProductToDynamoProduct(productFromMongo));
		}
	}
	

	private static void insertOrder(ApplicationContext ctx) {
		
		OrderRepo repo = ctx.getBean(OrderRepo.class);
		category.mongo.repository.OrderRepo mongoOrderRepo = ctx.getBean("mongoOrderRepo",
				category.mongo.repository.OrderRepo.class);
		Iterable<category.document.Order> iterable = mongoOrderRepo.findAll();
		for (category.document.Order orderFromMongo : iterable) {
			repo.save(convertMongoOrderToDynamoOrder(orderFromMongo));
		}
	}
	

	private static Customer convertMongoCustomerToDynamoCustomer(category.document.Customer customer) {
		Customer dynamoCustomer = new Customer(customer.getCustomerID(), customer.getCustomerName(),
				customer.getPhone(), convertMongoAddressToDynamoAddress(customer.getAddress()));
		return dynamoCustomer;
	}

	private static Address convertMongoAddressToDynamoAddress(category.document.Address address) {
		Address dynamoAddress = new Address(address.getstreet(), address.getCity(), address.getCountry(),
				address.getZIP());
		return dynamoAddress;

	}

	private static Category convertMongoCategoryToDynamoCategory(category.document.Category category) {
		Category dynamoCategory = new Category(category.getCategoryID(), category.getCategoryName(),
				category.getDescription());
		return dynamoCategory;

	}

	private static Product convertMongoProductToDynamoProduct(category.document.Product product) {
		Product dynamoProduct = new Product(product.getProductID(), product.getProductName(),
				convertMongoCategoryToDynamoCategory(product.getCategory()), product.getQuantityPerUnit(),
				product.getUnitPrice());
		return dynamoProduct;

	}
	
	private static Shipper convertMongoShipperToDynamoShipper(category.document.Shipper shipper) {
		Shipper dynamoShipper = new Shipper(shipper.getShipperID(),shipper.getCompanyName(),shipper.getPhone());
		return dynamoShipper;
	}
	
	private static OrderDetail convertMongoOrderDetailToDynamoOrderDetail(category.document.OrderDetail orderDetail) {
		OrderDetail dynamoOrderDetail = new OrderDetail(orderDetail.getProductID(),orderDetail.getQuantity(),orderDetail.getDiscount(),orderDetail.getunitPrice());
		return dynamoOrderDetail;
	}
	
	private static Order convertMongoOrderToDynamoOrder(category.document.Order order) {
		Order dynamoOrder = new Order();
		List<OrderDetail> orderDetails = order.getOrderitems().stream()
				.map(Runner::convertMongoOrderDetailToDynamoOrderDetail)
				.collect(Collectors.toList());
		dynamoOrder.setOrderitems(orderDetails);
		dynamoOrder.setCustomer(convertMongoCustomerToDynamoCustomer(order.getCustomer()));
		dynamoOrder.setShipper(convertMongoShipperToDynamoShipper(order.getShipper()));
		dynamoOrder.setShippedAddress(convertMongoAddressToDynamoAddress(order.getShippedAddress()));
		String orderedDate = order.getOrderedDate() != null ? order.getOrderedDate().toString() : null;
		dynamoOrder.setOrderedDate(orderedDate);
		System.err.println(orderedDate);
		String shippedDate = order.getShippedDate() != null ? order.getShippedDate().toString() : null;
		dynamoOrder.setShippedDate(shippedDate);
		dynamoOrder.setOrderID(order.getOrderID());
		return dynamoOrder;
	}

}
