package incubyte.Kata_Sweet_Shop_Management_System;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Sweet {
    private int id;
    private String name;
    private String category;
    private double price;
    private int quantity;

    public Sweet() {}

    //Constructor with validation ane Jackson annotations
    @JsonCreator
    public Sweet(
        @JsonProperty("id") int id,
        @JsonProperty("name") String name,
        @JsonProperty("category") String category,
        @JsonProperty("price") double price,
        @JsonProperty("quantity") int quantity
    ) {
        if (id <= 0)
            throw new IllegalArgumentException("ID must be greater than 0.");
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Name must not be empty.");
        if (category == null || category.trim().isEmpty())
            throw new IllegalArgumentException("Category must not be empty.");
        if (price <= 0)
            throw new IllegalArgumentException("Price must be greater than 0.");
        if (quantity < 0)
            throw new IllegalArgumentException("Quantity must be 0 or more.");

        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    //Getters
    public int getId() {    return id;      }
    public String getName() {   return name;    }
    public String getCategory() {   return category;    }
    public double getPrice() {  return price;   }
    public int getQuantity() {  return quantity;    }

    //Setter 
    public void setQuantity(int quantity) {
        if (quantity < 0)
            throw new IllegalArgumentException("Quantity cannot be negative.");
        this.quantity = quantity;
    }

    //displaying
    @Override
    public String toString() {
        return String.format("ID: %-6d | Name: %-15s | Category: %-10s | Price: ₹%.2f | Stock: %d",
                id, name, category, price, quantity);
    }
}