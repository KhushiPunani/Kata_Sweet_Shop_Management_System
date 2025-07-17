package incubyte.Kata_Sweet_Shop_Management_System;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SweetService service = new SweetService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n╔═══════════════════════════════════════════╗");
            System.out.println("║        SWEET SHOP MANAGEMENT SYSTEM       ║");
            System.out.println("╠═══════════════════════════════════════════╣");
            System.out.println("║ 1. Operations                             ║");
            System.out.println("║ 2. Search & Sort                          ║");
            System.out.println("║ 3. Inventory Management                   ║");
            System.out.println("║ 4. Exit                                   ║");
            System.out.println("╚═══════════════════════════════════════════╝");
            System.out.print("Please choose a section: ");

            String mainChoice = scanner.nextLine();

            switch (mainChoice) {
                case "1":
                    showOperationsMenu(scanner, service);
                    break;
                case "2":
                    showSearchMenu(scanner, service);
                    break;
                case "3":
                    showInventoryMenu(scanner, service);
                    break;
                case "4":
                    System.out.println("Thank you! Goodbye!");
                    return;
                default:
                    System.out.println("Invalid section. Please try again.");
            }
        }
    }

    private static int getValidatedInt(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
                continue;
            }
            try {
                value = Integer.parseInt(input);
                if (value <= 0) {
                    System.out.println("Value must be greater than zero. Please try again.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid integer.");
            }
        }
    }
    private static int getValidatedInt(Scanner scanner, String prompt, SweetService service) {
    int value;
    while (true) {
        value = getValidatedInt(scanner, prompt); 
        if (prompt.equalsIgnoreCase("Enter ID: ") && service.idExists(value)) {
            System.out.println("ID already exists. Please enter a different ID.");
        } else {
            return value;
        }
    }
}


    private static double getValidatedDouble(Scanner scanner, String prompt) {
        double value;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
                continue;
            }
            try {
                value = Double.parseDouble(input);
                if (value <= 0) {
                    System.out.println("Value must be greater than zero. Please try again.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid decimal number.");
            }
        }
    }
    

    private static void showOperationsMenu(Scanner scanner, SweetService service) {
        while (true) {
            System.out.println("\n╔═════════════════════╗");
            System.out.println("║     OPERATIONS      ║");
            System.out.println("╠═════════════════════╣");
            System.out.println("║ 1. Add Sweet        ║");
            System.out.println("║ 2. View Sweets      ║");
            System.out.println("║ 3. Delete Sweet     ║");
            System.out.println("║ 4. Back             ║");
            System.out.println("╚═════════════════════╝");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    int id = getValidatedInt(scanner, "Enter ID: ", service);
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Category: ");
                    String category = scanner.nextLine();
                    double price = getValidatedDouble(scanner, "Enter Price: ");
                    int qty = getValidatedInt(scanner, "Enter Quantity: ");

                    Sweet newSweet = new Sweet(id, name, category, price, qty);
                    service.addSweet(newSweet);
                    System.out.println("Sweet added successfully.");
                    break;

                case "2":
                    service.viewSweets();
                    break;

                case "3":
                    int delId = getValidatedInt(scanner, "Enter Sweet ID to delete: ");
                    service.deleteSweet(delId);
                    break;

                case "4":
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void showSearchMenu(Scanner scanner, SweetService service) {
        while (true) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║          SEARCH & SORT MENU                ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║ 1. Search by Name                          ║");
            System.out.println("║ 2. Search by Category                      ║");
            System.out.println("║ 3. Search by Price Range                   ║");
            System.out.println("║ 4. Sort sweets by name                     ║");
            System.out.println("║ 5. Sort sweets by price (Low to High)      ║");
            System.out.println("║ 6. Sort sweets by price (High to Low)      ║");
            System.out.println("║ 7. Back to Main Menu                       ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter name to search: ");
                    service.searchByName(scanner.nextLine());
                    break;
                case "2":
                    System.out.print("Enter category to search: ");
                    service.searchByCategory(scanner.nextLine());
                    break;
                    
                case "3":
                    double min = getValidatedDouble(scanner, "Enter minimum price: ");
                    double max = getValidatedDouble(scanner, "Enter maximum price: ");
                    service.searchByPriceRange(min, max);
                    break;
                
                case "4":
                    List<Sweet> sortedByName = service.sortSweetsByName();
                    sortedByName.forEach(System.out::println);
                    break;

                case "5":
                    List<Sweet> sortedByPriceAsc = service.sortSweetsByPriceAscending();
                    sortedByPriceAsc.forEach(System.out::println);
                    break;

                case "6":
                    List<Sweet> sortedByPriceDesc = service.sortSweetsByPriceDescending();
                    sortedByPriceDesc.forEach(System.out::println);
                    break;

                case "7":
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void showInventoryMenu(Scanner scanner, SweetService service) {
        while (true) {
            System.out.println("\n╔═════════════════════════════╗");
            System.out.println("║    INVENTORY MANAGEMENT     ║");
            System.out.println("╠═════════════════════════════╣");
            System.out.println("║ 1. Purchase Sweet           ║");
            System.out.println("║ 2. Restock Sweet            ║");
            System.out.println("║ 3. Back                     ║");
            System.out.println("╚═════════════════════════════╝");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    int pid = getValidatedInt(scanner, "Enter Sweet ID to purchase: ");
                    int pqty = getValidatedInt(scanner, "Enter quantity to purchase: ");
                    service.purchaseSweet(pid, pqty);
                    break;

                case "2":
                    int rid = getValidatedInt(scanner, "Enter Sweet ID to restock: ");
                    int rqty = getValidatedInt(scanner, "Enter quantity to add: ");
                    service.restockSweet(rid, rqty);
                    break;

                case "3":
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
