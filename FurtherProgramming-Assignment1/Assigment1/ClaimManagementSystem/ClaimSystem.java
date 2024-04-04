package ClaimManagementSystem;

import ClaimManagementSystem.Model.*;
import ClaimManagementSystem.UI.MainPage;

import java.util.ArrayList;
import java.util.List;

public class ClaimSystem {

    public static void run() {
        MainPage.run();
    }

    public static void displayCustomers() {
        System.out.println("CUSTOMER DETAILS");
        System.out.println();
        System.out.printf("%-20s %-30s %-20s %-20s %-20s\n", "ID", "Name", "Insurance Card", "Claims", "Dependant");

        for (Customer customer : DataManager.getCustomers()) {
            System.out.printf("%-20s %-30s %-20s %-20s %-20s\n",
                    customer.getId(),
                    customer.getName(),
                    customer.getInsuranceCard() == null ? "None" : customer.getInsuranceCard().getCardNumber(),
                    customer.getClaims().isEmpty() ? "None" : customer.getClaims().size(),
                    customer instanceof PolicyHolder ? ((PolicyHolder) customer).getDependantsIDS() : "Not a policy holder");
        }
        System.out.println();
    }

    public static void displayClaims() {
        System.out.printf("%-15s %-15s %-20s %-15s %-15s %-10s %-15s %-10s %-15s %-20s %-15s\n",
                "ID", "Claim Date", "Insured Person", "Card Number",
                "Exam Date", "Documents", "Claim Amount", "Status",
                "Bank", "Receiver", "Bank Number");

        for (Claim claim : DataManager.getClaims().values()) {
            System.out.printf("%-15s %-15s %-20s %-15s %-15s %-10s %-15s %-10s %-15s %-20s %-15s\n",
                    claim.getId(), claim.getClaimDate(), claim.getInsuredPerson().getName(), claim.getCardNumber(),
                    claim.getExamDate(), claim.getDocuments().size(), claim.getClaimAmount(), claim.getStatus(),
                    claim.getBankName(), claim.getReceiverName(), claim.getBankNumber());
        }
        System.out.println();
    }

    private static List<String> getDependantsIDS(PolicyHolder policyHolder) {
        List<String> ids = new ArrayList<>();
        for (Dependant d : policyHolder.getDependantList()) {
            ids.add(d.getId());
        }
        return ids;
    }
}
