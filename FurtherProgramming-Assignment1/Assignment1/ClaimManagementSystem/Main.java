package ClaimManagementSystem;
/**
 * @author <Nguyen Ba Lam Quang Thai - S3975154>
 */

public class Main {
    public static void main(String[] args) {
        DataManager.readCustomer();
        DataManager.readInsuranceCard();
        DataManager.readClaim();
        ClaimSystem.displayCustomers();
        ClaimSystem.displayClaims();
        ClaimSystem.run();
    }
}
