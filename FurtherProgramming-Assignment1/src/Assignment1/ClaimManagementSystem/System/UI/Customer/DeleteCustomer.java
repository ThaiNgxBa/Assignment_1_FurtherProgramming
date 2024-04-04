package System.UI.Customer;

import System.Model.Card;
import System.Model.Claim;
import System.Model.Customer;
import System.Model.PolicyHolder;
import System.DataManager;

import java.util.List;
import java.util.Scanner;

public class DeleteCustomer {
    public static void run() {
        displayOptions();
    }

    private static void exit() {
        CustomerPage.run();
    }

    private static void displayOptions() {
        Scanner scanner = new Scanner(System.in);
        Customer customer = getCustomerId(scanner);
        deleteCustomer(customer);
    }
    private static Customer getCustomerId(Scanner scanner) {
        while (true) {
            System.out.println("Enter the customer id that you want to delete: ('q' to exit)");
            String id = scanner.nextLine();
            if (id.equalsIgnoreCase("q")) {
                exit();
                break;
            }

            if (id.matches("^c-\\d{7}$")) {
                Customer customer = DataManager.getCustomer(id);
                if (customer != null) return customer;
                else System.out.println("Customer not found");
            } else {
                System.out.println("Invalid customer ID, must be in format: c-number (7 digits).");
            }

        }
        return null;
    }

    private static void deleteCustomer(Customer customer) {
        // If the customer have cards or claims, delete everything related to them.
        // Get all the cards and claims
        Card card = null;
        List<Claim> claims = null;
        if (customer != null) {
            card = customer.getInsuranceCard();
            claims = customer.getClaims();
        }
        // If the deleted customer is a dependant then delete them from the policyholder's list of dependants
        for (Customer c : DataManager.getCustomers()) {
            if (c instanceof PolicyHolder) {
                ((PolicyHolder) c).getDependantList().remove(customer);
            }
        }

        // Delete cards and claims from system and database
        if (card != null) DataManager.getInsuranceCards().remove(card.getCardNumber());
        if (claims != null && !claims.isEmpty()) {
            claims.forEach(claim -> DataManager.getClaims().remove(claim.getId()));
        }

        // Remove the customer after all related cards and claims are removed
        DataManager.getCustomers().remove(customer);

        // Overwrite all data
        DataManager.overWriteCustomer();
        DataManager.overWriteInsuranceCards();
        DataManager.overWriteClaim();
    }
}
