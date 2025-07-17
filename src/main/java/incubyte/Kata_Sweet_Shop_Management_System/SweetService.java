package incubyte.Kata_Sweet_Shop_Management_System;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SweetService {
    private String FILE_PATH;
    private List<Sweet> sweets = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();

    public SweetService() {
        this.FILE_PATH = "sweets.json";
        loadData();
    }

    // ✅ This constructor is only for testing
    public SweetService(String filePath) {
        this.FILE_PATH = filePath;
        loadData();
    }

    private void loadData() {
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                this.sweets = mapper.readValue(file, new TypeReference<List<Sweet>>() {
                });
            }
        } catch (IOException e) {
            System.out.println("Error loading sweets: " + e.getMessage());
        }
    }

    private void saveData() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), sweets);
        } catch (IOException e) {
            System.out.println("Error saving sweets: " + e.getMessage());
        }
    }

    public boolean isValidSweet(Sweet sweet) {
        return sweet != null &&
                sweet.getId() > 0 &&
                sweet.getName() != null &&
                !sweet.getName().trim().isEmpty() &&
                sweet.getCategory() != null &&
                !sweet.getCategory().trim().isEmpty() &&
                sweet.getPrice() > 0 &&
                sweet.getQuantity() >= 0; // Allow zero quantity for out-of-stock
    }

    public void addSweet(Sweet sweet) {
        if (!isValidSweet(sweet)) {
            throw new IllegalArgumentException(
                    "Invalid sweet data. Please ensure ID > 0, Name/Category are non-empty, and Price/Quantity > 0.");
        }

        // Check for duplicate ID
        for (Sweet s : sweets) {
            if (s.getId() == sweet.getId()) {
                throw new IllegalArgumentException("Sweet with this ID already exists.");
            }
        }

        sweets.add(sweet);
        saveData();
    }

    public void deleteSweet(int id) {
        int originalSize = sweets.size();
        sweets.removeIf(s -> s.getId() == id);
        if (originalSize == sweets.size()) {
            System.out.println("No sweet found with ID: " + id);
        } else {
            saveData();
            System.out.println("Sweet deleted successfully.");
        }
    }

    public void viewSweets() {
        loadData();
        if (sweets.isEmpty()) {
            System.out.println("No sweets available.");
            return;
        }
        for (Sweet sweet : sweets) {
            System.out.println(sweet);
        }
    }

    public void searchByName(String name) {
        loadData();
        boolean found = false;
        for (Sweet s : sweets) {
            if (s.getName().equalsIgnoreCase(name.trim())) {
                System.out.println(s);
                found = true;
            }
        }
        if (!found)
            System.out.println("No sweet found with that name.");
    }

    public void searchByCategory(String category) {
        loadData();
        boolean found = false;
        for (Sweet s : sweets) {
            if (s.getCategory().equalsIgnoreCase(category.trim())) {
                System.out.println(s);
                found = true;
            }
        }
        if (!found)
            System.out.println("No sweet found in that category.");
    }

    public void searchByPriceRange(double min, double max) {
        loadData();
        boolean found = false;
        for (Sweet s : sweets) {
            if (s.getPrice() >= min && s.getPrice() <= max) {
                System.out.println(s);
                found = true;
            }
        }
        if (!found)
            System.out.println("No sweet found in that price range.");
    }

    public void purchaseSweet(int id, int qty) {
    if (qty <= 0) {
        System.out.println("Quantity must be greater than zero.");
        return;
    }

    Optional<Sweet> optionalSweet = sweets.stream().filter(s -> s.getId() == id).findFirst();

    if (optionalSweet.isEmpty()) {
        System.out.println("Sweet with this ID does not exist.");
        return;
    }

    Sweet sweet = optionalSweet.get();

    if (sweet.getQuantity() < qty) {
        System.out.println("Not enough stock to complete the purchase.");
        return;
    }

    sweet.setQuantity(sweet.getQuantity() - qty);
    saveData();
    System.out.println("Purchase successful. Remaining stock: " + sweet.getQuantity());
}


    public void restockSweet(int id, int qty) {
    if (qty <= 0) {
        System.out.println("Quantity must be greater than zero.");
        return;
    }

    Optional<Sweet> optionalSweet = sweets.stream().filter(s -> s.getId() == id).findFirst();

    if (optionalSweet.isEmpty()) {
        System.out.println("Sweet with this ID does not exist.");
        return;
    }

    Sweet sweet = optionalSweet.get();
    sweet.setQuantity(sweet.getQuantity() + qty);
    saveData();
    System.out.println("Sweet restocked. New quantity: " + sweet.getQuantity());
}


    public List<Sweet> getSweets() {
        return sweets;
    }
}