package incubyte.Kata_Sweet_Shop_Management_System;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SweetService {
    private final String FILE_PATH = "sweets.json";
    private List<Sweet> sweets = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();

    public SweetService() {
        loadData();
    }

    private void loadData() {
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                this.sweets = mapper.readValue(file, new TypeReference<List<Sweet>>() {});
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

    public void addSweet(Sweet sweet) {
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
        if (!found) System.out.println("No sweet found with that name.");
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
        if (!found) System.out.println("No sweet found in that category.");
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
        if (!found) System.out.println("No sweet found in that price range.");
    }

    public void purchaseSweet(int id, int quantity) {
        loadData();
        for (Sweet s : sweets) {
            if (s.getId() == id) {
                if (s.getQuantity() >= quantity) {
                    s.setQuantity(s.getQuantity() - quantity);
                    saveData();
                    System.out.println("Purchase successful. Remaining stock: " + s.getQuantity());
                    return;
                } else {
                    System.out.println("Error: Not enough stock.");
                    return;
                }
            }
        }
        System.out.println("Sweet not found.");
    }

    public void restockSweet(int id, int quantity) {
        loadData();
        for (Sweet s : sweets) {
            if (s.getId() == id) {
                s.setQuantity(s.getQuantity() + quantity);
                saveData();
                System.out.println("Sweet restocked. New quantity: " + s.getQuantity());
                return;
            }
        }
        System.out.println("Sweet not found.");
    }

    public List<Sweet> getSweets() {
        return sweets;
    }
}
