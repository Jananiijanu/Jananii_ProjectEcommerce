package com.hexaware.ecommerce.user.test;

import com.hexaware.ecommerce.user.dao.OrderProcessorRepository;
import com.hexaware.ecommerce.user.entity.Customer;
import com.hexaware.ecommerce.user.entity.Product;
import com.hexaware.ecommerce.user.exception.CustomerNotFoundException;
import com.hexaware.ecommerce.user.exception.ProductNotFoundException;
import com.hexaware.ecommerce.user.service.OrderServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class OrderServiceImplTest {

    private OrderProcessorRepository repository;
    private OrderServiceImpl service;

    @Before
    public void setUp() {
       
        repository = new OrderProcessorRepository() {
            @Override
            public boolean createProduct(Product product) {
               
                return true;
            }

            @Override
            public boolean addToCart(Customer customer, Product product, int quantity) {
                
                return true;
            }

            @Override
            public boolean placeOrder(Customer customer, List<Map<Product, Integer>> cartItems, String paymentMethod) {
              
                return true;
            }
        };
        service = new OrderServiceImpl(repository);  
    }

  
    @Test
    public void testCreateProductSuccess() {
        Product product = new Product(1, "TV", 30000.0, "Smart TV", 10);
        assertTrue(service.createProduct(product));  
    }

   
    @Test
    public void testAddToCartSuccess() {
        Customer customer = new Customer(1, "Alice", "alice@mail.com", "pass123");
        Product product = new Product(1, "Phone", 15000.0, "Android", 5);
        assertTrue(service.addToCart(customer, product, 2));  
    }

 
    @Test
    public void testPlaceOrderSuccess() {
        Customer customer = new Customer(1, "Bob", "bob@mail.com", "securepass");
        Product product = new Product(2, "Laptop", 45000.0, "Gaming Laptop", 2);

        Map<Product, Integer> cartItem = new HashMap<>();
        cartItem.put(product, 1);
        List<Map<Product, Integer>> cartItems = Arrays.asList(cartItem);

        assertTrue(service.placeOrder(customer, cartItems, "COD"));  
    }

    
    @Test(expected = CustomerNotFoundException.class)
    public void testAddToCartInvalidCustomerThrowsException() {
        Product product = new Product(1, "Phone", 15000.0, "Android", 5);
        Customer invalidCustomer = new Customer(0, "X", "x@mail.com", "pass");
        service.addToCart(invalidCustomer, product, 1);
    }

    
    @Test(expected = ProductNotFoundException.class)
    public void testAddToCartInvalidProductThrowsException() {
        Customer customer = new Customer(1, "Alice", "alice@mail.com", "pass123");
        Product invalidProduct = new Product(0, "", 0.0, "", 0);
        service.addToCart(customer, invalidProduct, 1);
    }

   
    @Test(expected = IllegalArgumentException.class)
    public void testAddToCartWithZeroQuantityThrowsException() {
        Customer customer = new Customer(1, "Alice", "alice@mail.com", "pass123");
        Product product = new Product(1, "Phone", 15000.0, "Android", 5);
        service.addToCart(customer, product, 0);
    }
}