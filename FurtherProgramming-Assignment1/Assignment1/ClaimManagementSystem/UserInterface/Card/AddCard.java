package ClaimManagementSystem.UserInterface.Card;
/**
 * @author <Nguyen Ba Lam Quang Thai - S3975154>
 */
import ClaimManagementSystem.DataManager;
import ClaimManagementSystem.Model.Customer;
import ClaimManagementSystem.Model.Dependant;
import ClaimManagementSystem.Model.InsuranceCard;
import ClaimManagementSystem.Model.PolicyHolder;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class AddCard {
    public static void run() {
        displayOptions();
    }

    private static void displayOptions() {
        Scanner scanner = new Scanner(System.in);
        String cardNumber = getCardNumber(scanner);
        Customer cardHolder = getCardHolder(scanner);
        PolicyHolder policyOwner = getPolicyOwner(scanner, cardHolder);
        LocalDate expirationDate = getExpirationDate(scanner);
        InsuranceCard card = new InsuranceCard(cardNumber, cardHolder, policyOwner, expirationDate);
        addCard(card);
    }
    private static String getCardNumber(Scanner scanner) {
        String cardNumber;
        while (true) {
            System.out.println("Enter card number: ");
            cardNumber = scanner.nextLine();
            if (cardNumber.matches("^\\d{10}$")) {
                if (DataManager.getInsuranceCards().containsKey(cardNumber)) {
                    System.out.println("There is already an insurance card with this number!");
                } else return cardNumber;
            } else {
                System.out.println("Number is invalid. Must be a 10 digits number.");
            }
        }
    }
    private static PolicyHolder getPolicyOwner(Scanner scanner, Customer cardHolder) {
        while (true) {
            System.out.println("Enter the policy owner's id:");
            String id = scanner.nextLine();
            if (id.matches("^c-\\d{7}$")) {
                try {
                    PolicyHolder policyOwner = (PolicyHolder) DataManager.getCustomer(id);
                    if (policyOwner == null) {
                        System.out.println("No customer found");
                    } else if (cardHolder instanceof Dependant) {
                        if (!policyOwner.getDependantList().contains(cardHolder)) {
                            System.out.println("The card holder is a dependant but he/she is not a dependant of the policy owner!");
                        } else return policyOwner;
                        // If the cardholder is a policyholder, then the policy owner must be himself
                    } else if (cardHolder instanceof PolicyHolder) {
                        if (policyOwner != cardHolder) {
                            System.out.println("The card holder is a policy holder so the policy owner must also be him/herself");
                        } else return policyOwner;
                    }
                } catch (ClassCastException e) {
                    System.out.println("This customer is not of policy holder type");
                }
            } else System.out.println("ID format invalid. Must be c-number (7 digits)");
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
                System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
            }
        }
    }
    private static void addCard(InsuranceCard card) {
        DataManager.getInsuranceCards().put(card.getCardNumber(), card);
        Customer customer = DataManager.getCustomer(card.getCardHolder().getId());
        if (customer != null) {
            customer.setInsuranceCard(card);
            DataManager.writeInsuranceCard(card);
            DataManager.overWriteCustomer();
        }
    }
    private static Customer getCardHolder(Scanner scanner) {
        while (true) {
            System.out.println("Enter the card holder's id:");
            String id = scanner.nextLine();
            if (id.matches("^c-\\d{7}$")) {
                Customer customer = DataManager.getCustomer(id);
                if (customer == null) {
                    System.out.println("No customer found");
                }
                // They are not eligible if they do not have a card
                else if (customer.getInsuranceCard() != null) {
                    System.out.println("This customer already had a card. A customer can only have one card!");
                } else return customer;
            } else System.out.println("ID format invalid. Must be c-number (7 digits)");
        }
    }
}
