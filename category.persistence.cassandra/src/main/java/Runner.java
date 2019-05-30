import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.datastax.driver.core.LocalDate;

import category.cassandra.repository.CustomerByIdRepo;
import category.cassandra.repository.CustomerByNameRepo;
import category.cassandra.repository.OrderRepo;
import category.cassandra.repository.ProductByIdRepo;
import category.cassandra.repository.ProductByNameRepo;
import category.cassandra.repository.ShipperByIdRepo;
import category.cassandra.repository.ShipperByNameRepo;
import category.cassandra.table.Address;
import category.cassandra.table.Category;
import category.cassandra.table.Customer;
import category.cassandra.table.CustomerById;
import category.cassandra.table.CustomerByName;
import category.cassandra.table.Order;
import category.cassandra.table.OrderDetail;
import category.cassandra.table.ProductById;
import category.cassandra.table.ProductByName;
import category.cassandra.table.Shipper;
import category.cassandra.table.ShipperById;
import category.cassandra.table.ShipperByName;
import category.persistence.cassandra.config.CassandraDbConfig;
import category.persistence.mongo.config.MongoConfig;

public class Runner {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(CassandraDbConfig.class, MongoConfig.class);
		CustomerByNameRepo customerRepo = ctx.getBean(CustomerByNameRepo.class);
		Optional<CustomerByName> manuel = customerRepo.findById("Manuel Pereira");
		System.out.println(manuel.get().getCustomerName());
	}

	private static void populateTables(ApplicationContext ctx) {
		insertCustomerById(ctx);
		insertCustomerByName(ctx);
		insertShipperById(ctx);
		insertShipperByName(ctx);
		insertProductByName(ctx);
		insertProductById(ctx);
		insertOrder(ctx);
	}

	private static void insertCustomerById(ApplicationContext ctx) {

		CustomerByIdRepo repo = ctx.getBean(CustomerByIdRepo.class);
		category.mongo.repository.CustomerRepo mongoCustomerRepo = ctx.getBean("mongoCustomerRepo",
				category.mongo.repository.CustomerRepo.class);
		Iterable<category.document.Customer> iterable = mongoCustomerRepo.findAll();
		for (category.document.Customer customerFromMongo : iterable) {
			System.err.println(customerFromMongo.getCustomerName());
			repo.save(convertMongoCustomerToCassandraCustomerById(customerFromMongo));
		}
	}

	private static void insertCustomerByName(ApplicationContext ctx) {

		CustomerByNameRepo repo = ctx.getBean(CustomerByNameRepo.class);
		category.mongo.repository.CustomerRepo mongoCustomerRepo = ctx.getBean("mongoCustomerRepo",
				category.mongo.repository.CustomerRepo.class);
		Iterable<category.document.Customer> iterable = mongoCustomerRepo.findAll();
		for (category.document.Customer customerFromMongo : iterable) {
			System.err.println(customerFromMongo.getCustomerName());
			repo.save(convertMongoCustomerToCassandraCustomerByName(customerFromMongo));
		}
	}

	private static void insertShipperById(ApplicationContext ctx) {

		ShipperByIdRepo repo = ctx.getBean(ShipperByIdRepo.class);
		category.mongo.repository.ShipperRepo mongoCustomerRepo = ctx.getBean("mongoShipperRepo",
				category.mongo.repository.ShipperRepo.class);
		Iterable<category.document.Shipper> iterable = mongoCustomerRepo.findAll();
		for (category.document.Shipper shipperFromMongo : iterable) {
			repo.save(convertMongoShipperToCassandraShipperById(shipperFromMongo));
		}
	}

	private static void insertShipperByName(ApplicationContext ctx) {

		ShipperByNameRepo repo = ctx.getBean(ShipperByNameRepo.class);
		category.mongo.repository.ShipperRepo mongoCustomerRepo = ctx.getBean("mongoShipperRepo",
				category.mongo.repository.ShipperRepo.class);
		Iterable<category.document.Shipper> iterable = mongoCustomerRepo.findAll();
		for (category.document.Shipper shipperFromMongo : iterable) {
			repo.save(convertMongoShipperToCassandraShipperByName(shipperFromMongo));
		}
	}

	private static void insertProductById(ApplicationContext ctx) {

		ProductByIdRepo repo = ctx.getBean(ProductByIdRepo.class);
		category.mongo.repository.ProductRepo mongoProductRepo = ctx.getBean("mongoProductRepo",
				category.mongo.repository.ProductRepo.class);
		Iterable<category.document.Product> iterable = mongoProductRepo.findAll();
		for (category.document.Product productFromMongo : iterable) {
			repo.save(convertMongoProductToCassandraProductById(productFromMongo));
		}
	}

	private static void insertProductByName(ApplicationContext ctx) {

		ProductByNameRepo repo = ctx.getBean(ProductByNameRepo.class);
		category.mongo.repository.ProductRepo mongoProductRepo = ctx.getBean("mongoProductRepo",
				category.mongo.repository.ProductRepo.class);
		Iterable<category.document.Product> iterable = mongoProductRepo.findAll();
		for (category.document.Product productFromMongo : iterable) {
			repo.save(convertMongoProductToCassandraProductByName(productFromMongo));
		}
	}

	private static void insertOrder(ApplicationContext ctx) {

		OrderRepo repo = ctx.getBean(OrderRepo.class);
		category.mongo.repository.OrderRepo mongoOrderRepo = ctx.getBean("mongoOrderRepo",
				category.mongo.repository.OrderRepo.class);
		Iterable<category.document.Order> iterable = mongoOrderRepo.findAll();
		for (category.document.Order orderFromMongo : iterable) {
			try {
			repo.save(convertMongoOrderToCassandraOrder(orderFromMongo));
			}
			catch(Exception e) {
				System.err.println("Unable to insert "+ orderFromMongo.getOrderID());
				System.err.println(e.getMessage());
			}
		}
	}

	private static Customer convertMongoCustomerToCassandraCustomer(category.document.Customer customer) {
		Customer cassandraCustomer = new Customer(customer.getCustomerID(), customer.getCustomerName(),
				customer.getPhone(), convertMongoAddressToCassandraAddress(customer.getAddress()));
		return cassandraCustomer;
	}

	private static CustomerById convertMongoCustomerToCassandraCustomerById(category.document.Customer customer) {
		CustomerById cassandraCustomer = new CustomerById(customer.getCustomerID(), customer.getCustomerName(),
				customer.getPhone(), convertMongoAddressToCassandraAddress(customer.getAddress()));
		return cassandraCustomer;
	}

	private static CustomerByName convertMongoCustomerToCassandraCustomerByName(category.document.Customer customer) {
		CustomerByName cassandraCustomer = new CustomerByName(customer.getCustomerID(), customer.getCustomerName(),
				customer.getPhone(), convertMongoAddressToCassandraAddress(customer.getAddress()));
		return cassandraCustomer;
	}

	private static Address convertMongoAddressToCassandraAddress(category.document.Address address) {
		Address cassandraAddress = new Address(address.getstreet(), address.getCity(), address.getCountry(),
				address.getZIP());
		return cassandraAddress;

	}

	private static Category convertMongoCategoryToCassandraCategory(category.document.Category category) {
		Category cassandraCategory = new Category(category.getCategoryID(), category.getCategoryName(),
				category.getDescription());
		return cassandraCategory;

	}

	private static ProductById convertMongoProductToCassandraProductById(category.document.Product product) {
		ProductById cassandraProduct = new ProductById(product.getProductID(), product.getProductName(),
				convertMongoCategoryToCassandraCategory(product.getCategory()), product.getQuantityPerUnit(),
				product.getUnitPrice());
		return cassandraProduct;

	}

	private static ProductByName convertMongoProductToCassandraProductByName(category.document.Product product) {
		ProductByName cassandraProduct = new ProductByName(product.getProductID(), product.getProductName(),
				convertMongoCategoryToCassandraCategory(product.getCategory()), product.getQuantityPerUnit(),
				product.getUnitPrice());
		return cassandraProduct;

	}

	private static ShipperById convertMongoShipperToCassandraShipperById(category.document.Shipper shipper) {
		ShipperById cassandraShipper = new ShipperById(shipper.getShipperID(), shipper.getCompanyName(),
				shipper.getPhone());
		return cassandraShipper;
	}

	private static ShipperByName convertMongoShipperToCassandraShipperByName(category.document.Shipper shipper) {
		ShipperByName cassandraShipper = new ShipperByName(shipper.getShipperID(), shipper.getCompanyName(),
				shipper.getPhone());
		return cassandraShipper;
	}

	private static Shipper convertMongoShipperToCassandraShipper(category.document.Shipper shipper) {
		Shipper cassandraShipper = new Shipper(shipper.getShipperID(), shipper.getCompanyName(), shipper.getPhone());
		return cassandraShipper;
	}

	private static OrderDetail convertMongoOrderDetailToCassandraOrderDetail(
			category.document.OrderDetail orderDetail) {
		OrderDetail cassandraOrderDetail = new OrderDetail(orderDetail.getProductID(), orderDetail.getQuantity(),
				orderDetail.getDiscount(), orderDetail.getunitPrice());
		return cassandraOrderDetail;
	}

	private static Order convertMongoOrderToCassandraOrder(category.document.Order order) {
		Order cassandraOrder = new Order();
		List<OrderDetail> orderDetails = order.getOrderitems().stream()
				.map(Runner::convertMongoOrderDetailToCassandraOrderDetail).collect(Collectors.toList());
		cassandraOrder.setOrderitems(orderDetails);
		cassandraOrder.setCustomer(convertMongoCustomerToCassandraCustomer(order.getCustomer()));
		cassandraOrder.setShipper(convertMongoShipperToCassandraShipper(order.getShipper()));
		cassandraOrder.setShippedAddress(convertMongoAddressToCassandraAddress(order.getShippedAddress()));
		
		Date orderedDate = order.getOrderedDate() != null ? order.getOrderedDate() : new Date();
		cassandraOrder.setOrderedDate(orderedDate);
		System.out.println("orderedDate java "+order.getOrderedDate());
		System.out.println("orderedDate cassandra "+orderedDate);
		Date shippedDate = order.getShippedDate() != null ? order.getOrderedDate(): new Date();
		cassandraOrder.setShippedDate(shippedDate);
		cassandraOrder.setOrderID(order.getOrderID());
		return cassandraOrder;
	}

}
