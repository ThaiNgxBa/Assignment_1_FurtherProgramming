package ClaimManagementSystem.UserInterface.Claim;
/**
 * @author <Nguyen Ba Lam Quang Thai - S3975154>
 */
import ClaimManagementSystem.DataManager;
import ClaimManagementSystem.Model.Claim;
import ClaimManagementSystem.Model.Customer;
import ClaimManagementSystem.UserInterface.MainPage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * <p>
 *     Updating a claim work by accessing the claim in the system data, and
 *     directly modify it. Then overwrite all data back into text file.
 * </p>
 *
 * */
public class UpdateClaim {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter claim id: ('q' to exit)");
            String id = scanner.nextLine();
            // Return to homepage
            if (id.equals("q")) {
                MainPage.run();
                break;
            }

            // Get claim from the system.
            Claim claim = DataManager.getClaim(id);
            if (claim == null) {
                System.out.println("This claim doesn't exist");
            } else {
                displayOptions(scanner, claim);
            }
        }
    }

    private static void displayOptions(Scanner scanner, Claim claim) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Select what you want to change in this claim:");
            System.out.println("1. Claim Date");
            System.out.println("2. Insured Person");
            System.out.println("3. Exam Date");
            System.out.println("4. Claim Amount");
            System.out.println("5. Claim Status");
            System.out.println("6. Bank Name");
            System.out.println("7. Receiver Name");
            System.out.println("8. Bank Number");
            System.out.println("0. Exit");
            System.out.println();
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    updateClaimDate(claim, getClaimDate(scanner));
                    break;
                case "2":
                    updateInsuredPerson(claim, getInsuredPerson(scanner));
                    break;
                case "3":
                    updateExamDate(claim, getExamDate(scanner));
                    break;
                case "4":
                    updateClaimAmount(claim, getClaimAmount(scanner));
                    break;
                case "5":
                    updateClaimStatus(claim, getClaimStatus(scanner));
                    break;
                case "6":
                    updateBankName(claim, getBankName(scanner));
                    break;
                case "7":
                    updateReceiver(claim, getReceiverName(scanner));
                    break;
                case "8":
                    updateBankNumber(claim, getBankNumber(scanner));
                    break;
                case "0":
                    exit = true; // Set exit to true to exit the loop
                    break;
                default:
                    System.out.println();
                    System.out.println("⚠️ Invalid choice. Please select a valid option.");
                    break;
            }

            DataManager.overWriteClaim();
            DataManager.overWriteCustomer();
        }
    }


    private static void updateClaimDate(Claim claim, LocalDate newDate) {
        claim.setClaimDate(newDate);
    }

    private static void updateInsuredPerson(Claim claim, Customer newInsuredPerson) {
        // Swap the claim from old insured person to new insured person
        swapClaim(claim, newInsuredPerson);
    }

    /**
     * <p>
     *     Helper function for swapping claim between 2 customers
     * </p>
     * */
    private static void swapClaim(Claim claim, Customer newInsuredPerson) {
        claim.getInsuredPerson().removeClaim(claim);
        claim.setInsuredPerson(newInsuredPerson);
        newInsuredPerson.addClaim(claim);
        // Update the card number in claim to new insured person's card
        updateCardNumber(claim, newInsuredPerson.getInsuranceCard().getCardNumber());
    }


    private static void updateCardNumber(Claim claim, String newCardNumber) {
        claim.setCardNumber(newCardNumber);
    }

    private static void updateExamDate(Claim claim, LocalDate newDate) {
        claim.setExamDate(newDate);
    }

    private static void updateClaimAmount(Claim claim, double newClaimAmount) {
        claim.setClaimAmount(newClaimAmount);
    }

    private static void updateClaimStatus(Claim claim, Claim.ClaimStatus newStatus) {
        claim.setStatus(newStatus);
    }

    private static void updateBankName(Claim claim, String newBankName) {
        claim.setBankName(newBankName);
    }

    private static void updateReceiver(Claim claim, String newReceiver) {
        claim.setReceiverName(newReceiver);
    }

    private static void updateBankNumber(Claim claim, String newBankNumber) {
        claim.setBankNumber(newBankNumber);
    }

    private static LocalDate getClaimDate(Scanner scanner) {
        LocalDate date;
        while (true) {
            System.out.println("Enter claim date (yyyy-MM-dd): ");
            try {
                date = LocalDate.parse(scanner.nextLine());
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
            }
        }
    }

    private static Customer getInsuredPerson(Scanner scanner) {
        while (true) {
            System.out.println("Please enter the customer id:");
            String id = scanner.nextLine();

            if (id.matches("^c-\\d{7}$")) {
                Customer customer = DataManager.getCustomer(id);
                if (customer == null) {
                    System.out.println("There is no customer with this id!");
                }
                // If the new insured person doesn't have a card. They are not eligible
                else if (customer.getInsuranceCard() == null) {
                    System.out.println("This customer doesn't have an insurance card. They are not eligible for this claim!");
                } else return customer;
            } else System.out.println("Wrong id format. Must be c-number (7 digits)");
        }
    }
    private static String getBankName(Scanner scanner) {
        while (true) {
            System.out.println("Enter receiver bank name:");
            String bankName = scanner.nextLine();
            if (!bankName.isEmpty()) {
                return bankName;
            }
        }
    }

    private static String getReceiverName(Scanner scanner) {
        while (true) {
            System.out.println("Enter receiver name:");
            String receiverName = scanner.nextLine();
            if (!receiverName.isEmpty()) {
                return receiverName;
            }
        }
    }

    private static String getBankNumber(Scanner scanner) {
        while (true) {
            System.out.println("Enter credit card number:");
            String bankNumber = scanner.nextLine();
            if (!bankNumber.isEmpty()) {
                return bankNumber;
            }
        }
    }
    private static String getCardNumber(Scanner scanner, Customer customer) {
        while (true) {
            System.out.println("Enter card number:");
            String cardNumber = scanner.nextLine();
            if (cardNumber.matches("^\\d{10}$")) {
                String customerCardNum = customer.getInsuranceCard().getCardNumber();
                // If the customer has a card with the input number
                if (customerCardNum.equals(cardNumber)) {
                    return cardNumber;
                }
                else {
                    System.out.println("The customer doesn't has any card with this number.");
                }
            } else {
                System.out.println("Invalid card number format. Please enter a 10-digit card number.");
            }
        }
    }

    private static LocalDate getExamDate(Scanner scanner) {
        LocalDate date;
        while (true) {
            System.out.println("Enter exam date (yyyy-MM-dd): ");
            try {
                date = LocalDate.parse(scanner.nextLine());
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
            }
        }
    }

    private static double getClaimAmount(Scanner scanner) {
        while (true) {
            System.out.println("Enter claim amount:");
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static Claim.ClaimStatus getClaimStatus(Scanner scanner) {
        while (true) {
            System.out.println("Enter claim status (New/Processing/Done):");
            String userInput = scanner.nextLine().trim().toLowerCase(); // Convert input to lowercase and trim whitespace
            switch (userInput) {
                case "new":
                    return Claim.ClaimStatus.New;
                case "processing":
                    return Claim.ClaimStatus.Processing;
                case "done":
                    return Claim.ClaimStatus.Done;
                default:
                    System.out.println("Invalid status. Please enter New, Processing, or Done.");
            }
        }
    }

}
