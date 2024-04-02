package UI.Claim;

import UI.MainPage;

import java.util.Scanner;

public class ClaimPage {

    public static void run() {
        displayOptions();
    }

    private static void displayOptions() {
        System.out.println("What do you want to do with claims");
        System.out.println("1. Add Claim");
        System.out.println("2. Update Claim");
        System.out.println("3. Delete Claim");
        System.out.println("0. Go Back");
        System.out.print("Enter your choice: ");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
//                AddClaim.run();
                break;
            case "2":
//                UpdateClaim.run();
                break;
            case "3":
//                DeleteClaim.run();
                break;
            case "0":
//                MainPage.run();
                break;
            default:
                System.out.println();
                System.out.println("⚠️ Invalid choice. Please select a valid option.");
                displayOptions();
                break;
        }
    }


}