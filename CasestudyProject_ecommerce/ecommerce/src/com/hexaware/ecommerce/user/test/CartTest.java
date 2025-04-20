package com.hexaware.ecommerce.user.test;

import com.hexaware.ecommerce.user.dao.OrderProcessorRepositoryImpl;
import com.hexaware.ecommerce.user.entity.Customer;
import com.hexaware.ecommerce.user.entity.Product;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CartTest {

    OrderProcessorRepositoryImpl repo = new OrderProcessorRepositoryImpl();

    @Test
    public void testAddToCart() {
        Customer customer = new Customer(1, "Alice", "alice@mail.com", "password123");
        Product product = new Product(1, "Laptop", 1200.0, "Gaming Laptop", 5);
        boolean result = repo.addToCart(customer, product, 1);
        assertTrue(result);
    }

    @Test
    public void testRemoveFromCart() {
        Customer customer = new Customer(1, "Alice", "alice@mail.com", "password123");
        Product product = new Product(1, "Laptop", 1200.0, "Gaming Laptop", 5);
        boolean result = repo.removeFromCart(customer, product);
        assertTrue(result);
    }

    @Test
    public void testGetAllFromCart() {
        Customer customer = new Customer(1, "Alice", "alice@mail.com", "password123");
        List<Product> products = repo.getAllFromCart(customer);
        assertNotNull(products);
    }

    @Test
    public void testAddToCartWithZeroQuantity() {
        Customer customer = new Customer(1, "Alice", "alice@mail.com", "password123");
        Product product = new Product(2, "Phone", 700.0, "Smartphone", 10);
        try {
            repo.addToCart(customer, product, 0);
        } catch (IllegalArgumentException e) {
            assertEquals("Quantity must be greater than 0.", e.getMessage());
        }
    }

    @Test
    public void testAddToCartWithNullProduct() {
        Customer customer = new Customer(1, "Alice", "alice@mail.com", "password123");
        try {
            repo.addToCart(customer, null, 1);
            fail("Null product should not be allowed");
        } catch (RuntimeException e) {
            
        }
    }

    @Test
    public void testRemoveFromCartWithInvalidProduct() {
        Customer customer = new Customer(1, "Alice", "alice@mail.com", "password123");
        Product invalidProduct = new Product(0, "", 0.0, "", 0);
        try {
            repo.removeFromCart(customer, invalidProduct);
          
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Invalid product"));
        }
    }

    @Test
    public void testGetCartForNonExistentCustomer() {
        Customer customer = new Customer(0, "Ghost", "ghost@mail.com", "ghost123");
        try {
            repo.getAllFromCart(customer);
          
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid customer.", e.getMessage());
        }
    }
}
