import Model.Card;
import Model.Claim;
import Model.Customer;
import Utility.Compare;
import Utility.IDCompare;

import java.util.TreeMap;
import java.util.TreeSet;

public class DataController {

    private static TreeSet<Customer> customers = new TreeSet<>(new Compare());
    private static TreeMap<String, Card> insuranceCards = new TreeMap<>(new IDCompare());
    private static TreeMap<String, Claim> claims = new TreeMap<>(new IDCompare());
    private static final String CUSTOMER_FILE_PATH = "Assigment1/ClaimManagementSystem/Data/customers.txt";
    private static final String INSURANCE_FILE_PATH = "Assigment1/ClaimManagementSystem/Data/insurances.txt";
    private static final String CLAIM_FILE_PATH = "Assigment1/ClaimManagementSystem/Data/claims.txt";

    public static TreeSet<Customer> getCustomers() {
        return customers;
    }

    public static TreeMap<String, Card> getInsuranceCards() {
        return insuranceCards;
    }

    public static TreeMap<String, Claim> getClaims() {
        return claims;
    }
}
