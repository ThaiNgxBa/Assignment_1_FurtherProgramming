package System;

import System.Model.*;
import System.Utility.Compare;
import System.Utility.IDCompare;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class DataManager{
    private static final String CUSTOMER_FILE_PATH = "Assigment1/ClaimManagementSystem/Data/customers.txt";
    private static final String INSURANCE_FILE_PATH = "Assigment1/ClaimManagementSystem/Data/insurances.txt";
    private static final String CLAIM_FILE_PATH = "Assigment1/ClaimManagementSystem/Data/claims.txt";
    private static TreeSet<Customer> customers = new TreeSet<>(new Compare());
    private static TreeMap<String, Card> insuranceCards = new TreeMap<>(new IDCompare());
    private static TreeMap<String, Claim> claims = new TreeMap<>(new IDCompare());
    public static TreeSet<Customer> getCustomers() {
        return customers;
    }
    public static TreeMap<String, Card> getInsuranceCards() {
        return insuranceCards;
    }
    public static TreeMap<String, Claim> getClaims() {
        return claims;
    }
    public static void readCustomer() {
        File file = new File(CUSTOMER_FILE_PATH);
        // Create a new file if file does not exist
        try {
            if (file.createNewFile()) {
                System.out.println("Customers file created!");
            }
            // If file already exists, read from file and populate to ClaimProcessSystem
            else {
                loadCustomer(file);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Since we have to add dependants to policyholder. There will be cases where the policyholder
     * appears before the dependants in the text file. Because reading is linear, getCustomer()
     * can only return customers that are read before. So a practical approach is to load all
     * dependants first before policyholders. Another approach is to restructure text file so that.
     * All dependants are written first before policyholders.
     * */
    private static void loadCustomer(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.ready()) {
                String[] str = reader.readLine().split(",");
                customers.add(createCustomer(str));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Customer createCustomer(String[] str) {
        String type = str[0];
        String id = str[1];
        String name = str[2];

        // If is dependant
        if (type.equals("D")) {
            return new Dependant(id, name);
        } else {
            PolicyHolder customer = new PolicyHolder(id, name);
            // Add all dependants if there are any
            for (int i = 3; i < str.length; i++) {
                if (str[i].startsWith("c-")) {
                    customer.addDependant((Dependant) getCustomer(str[i]));
                }
            }
            return customer;
        }
    }

    public static Customer getCustomer(String id) {
        for (Customer customer : customers) {
            if (customer.getId().equals(id))
                return customer;
        }
        return null;
    }

    public static void overWriteCustomer() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(CUSTOMER_FILE_PATH));
            StringBuilder content = new StringBuilder();

            for (Customer customer : customers) {
                if (customer instanceof PolicyHolder) {
                    content.append("PH,");
                } else if (customer instanceof Dependant) {
                    content.append("D,");
                }
                content.append(customer.toData());
                content.append("\n");
            }

            bufferedWriter.write(content.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>
     *     We are getting the customer from the HashMap and they will be pointing
     *     to the same object.
     * </p>
     * @param str array of attributes taken from the txt file
     * @return Insurance card of the input id
     * */
    private static Card createCard(String[] str) {
        try {
            String cardNumber = str[0];
            Customer cardHolder = getCustomer(str[1]);
            PolicyHolder policyOwner = (PolicyHolder) getCustomer(str[2]);
            LocalDate expirationDate = LocalDate.parse(str[3]);
            // If the cardholder is a dependant and he/she is not inside the policy owner's list of dependant
            if (cardHolder instanceof Dependant && policyOwner != null) {
                if (!policyOwner.getDependantList().contains(cardHolder)) {
                    throw new IllegalArgumentException("Error in creating InsuranceCard: The dependant must be in the policy owner's list of dependants");
                }
            } else if (cardHolder instanceof PolicyHolder) {
                if (policyOwner != cardHolder) {
                    throw new IllegalArgumentException("Error in creating InsuranceCard: The card holder is a PolicyHolder, so the policy owner should match");
                }
            }
            assert cardHolder != null;
            return new Card(cardNumber, cardHolder, policyOwner, expirationDate);
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in creating InsuranceCard: PolicyOwner must be of type PolicyHolder. Please check for flaws in database!");
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Error in creating InsuranceCard: Invalid expiration date format");
        }
    }

}
