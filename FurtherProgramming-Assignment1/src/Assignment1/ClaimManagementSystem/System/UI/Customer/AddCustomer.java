package System.UI.Customer;

import System.Model.Card;
import System.Model.Customer;
import System.Model.Dependant;
import System.Model.PolicyHolder;
import System.DataManager;
import System.Model.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class AddCustomer {
    public static void run() {
        displayOptions();
    }

    private static void exit() {
        CustomerPage.run();
    }

    private static String getCustomerRole(Scanner scanner) {
        while (true) {
            System.out.println("Is this customer a dependant or a policy holder? ('q' to exit)");
            System.out.println("1. Dependant ");
            System.out.println("2. Policy Holder");
            System.out.println("q to Exit");
            String id = scanner.nextLine();
            if (id.equalsIgnoreCase("q")) {
                exit();
                break;
            }
            if (id.equalsIgnoreCase("1")) return "D";
            if (id.equalsIgnoreCase("2")) return "PH";
            System.out.println("⚠️ Invalid option. Please select a valid option.");
        }
        return null;
    }

    private static Card getInsuranceCard(Scanner scanner, Customer customer) {
        System.out.println("Does this customer has an insurance card? (yes/no)");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("yes")) {
            return createCardForCustomer(scanner, customer);
        } else return null;
    }
    private static String getCustomerId(Scanner scanner) {
        while (true) {
            System.out.println("Enter customer id: ");
            String id = scanner.nextLine();
            if (id.matches("^c-\\d{7}$")) {
                if (DataManager.getCustomer(id) == null)
                    return id;
                else System.out.println("ID already taken");
            }
            else System.out.println("Invalid ID format, must be c-number (7 digits)!!!");
        }
    }

    private static String getCustomerName(Scanner scanner) {
        while (true) {
            System.out.println("Enter customer name: ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) return name;
            else System.out.println("Customer invalid");
        }
    }

    private static Card createCardForCustomer(Scanner scanner, Customer customer) {
        String cardNumber = getCardNumber(scanner);
        PolicyHolder policyOwner = getPolicyOwner(scanner, customer);
        LocalDate expirationDate = getExpirationDate(scanner);
        return new Card(cardNumber, customer, policyOwner, expirationDate);
    }


    private static String getCardNumber(Scanner scanner) {
        while (true) {
            System.out.println("Enter card number: ");
            String cardNumber = scanner.nextLine();
            if (cardNumber.matches("\\d{10}")) return cardNumber;
            else System.out.println("Card number invalid");
        }
    }

    private static PolicyHolder getPolicyOwner(Scanner scanner, Customer customer) {
        while (true) {
            System.out.println("Enter policy owner's id: ");
            String id = scanner.nextLine();

            if (id.matches("^c-\\d{7}$")) {
                Customer policyOwner = DataManager.getCustomer(id);
                // If user choose a dependant to be policy onwer
                if (policyOwner instanceof Dependant)
                    System.out.println("A dependant cannot become a policy owner. Please choose someone else");
                    // If there is no customer
                else if (policyOwner == null) System.out.println("Customer not found");
                    // If the policy owner is chosen successfully, and the customer is a dependant.
                    // The customer will become that policy owner's dependant
                else if (policyOwner instanceof PolicyHolder) {
                    if (customer instanceof Dependant)
                        ((PolicyHolder) policyOwner).addDependant((Dependant) customer);
                    return (PolicyHolder) policyOwner;
                }
                else System.out.println("This customer is a dependant. A dependant cannot be a policy owner");
            }
            else System.out.println("Invalid customer ID, must be c-number (7 digits)");
        }
    }

    private static LocalDate getExpirationDate(Scanner scanner) {
        LocalDate date;
        while (true) {
            System.out.println("Enter expiration date (yyyy-MM-dd): ");
            try {
                date = LocalDate.parse(scanner.nextLine());
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Date format invalid. Please enter the date in YYYY-MM-DD format.");
            }
        }
    }
    private static void displayOptions() {
        Scanner scanner = new Scanner(System.in);
        String role = getCustomerRole(scanner);
        String id = getCustomerId(scanner);
        String name = getCustomerName(scanner);

        // Decide what type of customer to create
        Customer customer;
        if (role.equals("D")) customer = new Dependant(id, name);
        else customer = new PolicyHolder(id, name);

        // Execute getInsuranceCard and set the customer's card
        Card card = getInsuranceCard(scanner, customer);
        customer.setInsuranceCard(card);
        DataManager.getCustomers().add(customer);
        DataManager.overWriteCustomer();

        // If the customer has no card, then don't add to a database
        if (card != null) {
            DataManager.getInsuranceCards().put(card.getCardNumber(), card);
//            DataManager.writeInsuranceCard(insuranceCard);
        }
    }
}
