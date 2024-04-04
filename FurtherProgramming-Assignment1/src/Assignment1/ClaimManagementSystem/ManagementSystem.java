import Model.Customer;

public class ManagementSystem {
    public static void displayCustomers() {
        System.out.println("CUSTOMER DETAILS TABLE");
        System.out.printf("%-25s %-35s %-25s %-25s %-25s\n", "ID", "Name", "Insurance Card", "Claims", "Dependant");

//        for (Customer customer : DataController.getCustomers()) {
//            System.out.printf("%-25s %-35s %-25s %-25s %-25s\n",
//                    customer.getId(),
//                    customer.getName(),
//                    customer.getInsuranceCard() == null ? "None" : customer.getInsuranceCard().getCardNumber(),
//                    customer.getClaims().isEmpty() ? "None" : customer.getClaims().size(),
//                    customer instanceof PolicyHolder ? ((PolicyHolder) customer).getDependantsIDS() : "Not a policy holder");
//        }
        System.out.println();
    }
}
