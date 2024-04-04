package ClaimManagementSystem;

import ClaimManagementSystem.Model.*;
import ClaimManagementSystem.Utility.CustomerComparator;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

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
