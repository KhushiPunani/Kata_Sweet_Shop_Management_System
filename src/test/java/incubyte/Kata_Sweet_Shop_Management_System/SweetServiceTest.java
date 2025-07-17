package incubyte.Kata_Sweet_Shop_Management_System;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

public class SweetServiceTest {

    private SweetService service;
    private final String TEST_FILE_PATH = "temp_sweets_test.json";

    @BeforeEach
    void setUp() {

        System.setOut(new PrintStream(outContent));
        File file = new File(TEST_FILE_PATH);
        if (file.exists())
            file.delete();

        service = new SweetService(TEST_FILE_PATH);

        service.addSweet(new Sweet(301, "Barfi", "Milk", 120.0, 10));
        service.addSweet(new Sweet(302, "Jalebi", "Desi", 90.0, 15));
        service.addSweet(new Sweet(303, "Rasgulla", "Milk", 100.0, 20));
        service.addSweet(new Sweet(304, "Ladoo", "Festival", 80.0, 12));

        outContent.reset();
    }

    // --------------------------------------------- Operation

    @Test
    public void testNegativeID_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Sweet(-1, "Barfi", "Indian", 60.0, 10);
        });
    }

    @Test
    public void testEmptyName_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Sweet(203, "", "Desi", 150.0, 5);
        });
    }

    @Test
    public void testEmptyCategory_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Sweet(204, "Jalebi", "", 120.0, 5);
        });
    }

    @Test
    public void testNegativePrice_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Sweet(201, "Mysore Pak", "South", -100.0, 10);
        });
    }

    @Test
    public void testNegativeQuantity_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Sweet(202, "Imarti", "Desi", 150.0, -5);
        });
    }

    @Test
    public void testZeroPrice_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Sweet(205, "Kheer", "Milk", 0.0, 10);
        });
    }

    @Test
    public void testNullSweetObject() {
        Sweet sweet = null;
        assertFalse(service.isValidSweet(sweet));
    }

    // --------------------------------------------- for sorting and Searching

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @AfterEach
    void tearDown() {
        System.setOut(originalOut); // restore System.out
        outContent.reset();
    }

    // Search by Namee
    @Test
    public void testSearchByName_found() {
        service.searchByName("Jalebi");
        String output = outContent.toString().trim();
        assertTrue(output.contains("Jalebi"));
    }

    @Test
    public void testSearchByName_notFound() {
        service.searchByName("Kaju Katli");
        String output = outContent.toString().trim();
        assertEquals("No sweet found with that name.", output);
    }

    // Search by Category
    @Test
    public void testSearchByCategory_found() {
        service.searchByCategory("Milk");
        String output = outContent.toString().trim();
        assertTrue(output.contains("Barfi") && output.contains("Rasgulla"));
    }

    @Test
    public void testSearchByCategory_notFound() {
        service.searchByCategory("Chocolate");
        String output = outContent.toString().trim();
        assertEquals("No sweet found in that category.", output);
    }

    // Search by Price Range
    @Test
    public void testSearchByPriceRange_found() {
        service.searchByPriceRange(90.0, 120.0);
        String output = outContent.toString().trim();
        assertTrue(output.contains("Barfi") && output.contains("Jalebi") && output.contains("Rasgulla"));
    }

    @Test
    public void testSearchByPriceRange_notFound() {
        service.searchByPriceRange(200.0, 300.0);
        String output = outContent.toString().trim();
        assertEquals("No sweet found in that price range.", output);
    }

    // -------------------------------------------------- INVENTORY MANAGEMENT

    @Test
    public void testPurchaseSweet_quantityExceedsStock() {
        service.purchaseSweet(302, 20); // Jalebi has 15
        String output = outContent.toString().trim();
        assertEquals("Not enough stock to complete the purchase.", output);
    }

    @Test
    public void testPurchaseSweet_invalidId() {
        service.purchaseSweet(999, 5);
        String output = outContent.toString().trim();
        assertEquals("Sweet with this ID does not exist.", output);
    }

    @Test
    public void testPurchaseSweet_zeroQuantity() {
        service.purchaseSweet(303, 0);
        String output = outContent.toString().trim();
        assertEquals("Quantity must be greater than zero.", output);
    }

    @Test
    public void testPurchaseSweet_negativeQuantity() {
        service.purchaseSweet(303, -3);
        String output = outContent.toString().trim();
        assertEquals("Quantity must be greater than zero.", output);
    }

    @Test
    public void testRestockSweet_invalidId() {
        service.restockSweet(999, 5);
        String output = outContent.toString().trim();
        assertEquals("Sweet with this ID does not exist.", output);
    }

    @Test
    public void testRestockSweet_zeroQuantity() {
        service.restockSweet(301, 0);
        String output = outContent.toString().trim();
        assertEquals("Quantity must be greater than zero.", output);
    }

    @Test
    public void testRestockSweet_negativeQuantity() {
        service.restockSweet(301, -4);
        String output = outContent.toString().trim();
        assertEquals("Quantity must be greater than zero.", output);
    }

}