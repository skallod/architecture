package ru.galuzin.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.galuzin.store.domain.*;
import ru.galuzin.store.domain.security.Role;
import ru.galuzin.store.service.BookService;
import ru.galuzin.store.service.CommerceOrderService;
import ru.galuzin.store.service.CustomerService;
import ru.galuzin.store.service.OrderItemService;

import java.util.HashSet;

@SpringBootApplication
public class StoreApplication {

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(StoreApplication.class, args);
		CustomerService customerService = context.getBean(CustomerService.class);
		PasswordEncoder encoder = context.getBean(PasswordEncoder.class);
		run(customerService,
				encoder,
				context.getBean(BookService.class),
				context.getBean(CommerceOrderService.class),
				context.getBean(OrderItemService.class));
	}

	public static void run(CustomerService customerService,
						   PasswordEncoder encoder,
						   BookService bookService,
						   CommerceOrderService orderService,
						   OrderItemService orderItemService
						   ) throws Exception {

		Customer customer1 = new Customer();
		customer1.setLastName("Adams");
		customer1.setName("j");
		customer1.setPassword(encoder.encode("p"));
		String email = "JAdams@gmail.com";
		customer1.setEmail(email);
		customer1.setRole(Role.USER);
		customerService.createUser(customer1);
		customer1 = customerService.findByEmail(email);

		Customer customer2 = new Customer();
		customer2.setLastName("Admin");
		customer2.setName("admin");
		customer2.setPassword(encoder.encode("p"));
		customer2.setEmail("Admin@gmail.com");
		customer2.setRole(Role.ADMIN);
		customerService.createUser(customer2/*, userRoles*/);

		Book book = new Book();
		book.setActive(true);
		book.setAuthor("Artur Conan Doel");
		book.setCategory("Detective");
		book.setDescription("Famous detective");
		book.setFormat("A4");
		book.setInStockQuantity(0);
		book.setTitle("Sherlok Holmes adventure");
		book.setPrice(88700);
		book.setShippingWeight(345);
		bookService.save(book);

		CommerceOrder order = new CommerceOrder();
		order.setCustomer(customer1);
		order.setOrderStatus(OrderStatus.NEW);
		orderService.save(order);
		customer1 = customerService.findCustomerWithOrders(email);
		order = customer1.getOrderSet().iterator().next();

		OrderItem orderItem = new OrderItem();
		orderItem.setQuantity(2);
		orderItem.setCommerceOrder(order);
		orderItem.setPrice(177400);
		orderItemService.save(orderItem);

	}

}
