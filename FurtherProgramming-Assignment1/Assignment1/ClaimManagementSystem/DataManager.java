package ClaimManagementSystem;
/**
 * @author <Nguyen Ba Lam Quang Thai - S3975154>
 */
import ClaimManagementSystem.Model.*;
import ClaimManagementSystem.Utilities.IDComparator;
import ClaimManagementSystem.Utilities.Comparator;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class DataManager{
    private static final String CUSTOMER_FILE_PATH = "Assignment1/ClaimManagementSystem/Data/customers.txt";
    private static final String INSURANCE_FILE_PATH = "Assignment1/ClaimManagementSystem/Data/insurances.txt";
    private static final String CLAIM_FILE_PATH = "Assignment1/ClaimManagementSystem/Data/claims.txt";
    private static TreeSet<Customer> customers = new TreeSet<>(new Comparator());
    private static TreeMap<String, InsuranceCard> insuranceCards = new TreeMap<>(new IDComparator());
    private static TreeMap<String, Claim> claims = new TreeMap<>(new IDComparator());

    public static TreeSet<Customer> getCustomers() {
        return customers;
    }

    public static Map<String, InsuranceCard> getInsuranceCards() {
        return insuranceCards;
    }

    public static Map<String, Claim> getClaims() {
        return claims;
    }
    public static void readCustomer() {
        File file = new File(CUSTOMER_FILE_PATH);
        // If file doesn't exist, we create a new file.
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
     * <p>
     *     Since we are getting the customer from the HashMap itself. They will be pointing
     *     to the same object.
     * </p>
     * @param str array of attributes taken from the txt file
     * @return Insurance card of the input id
     * */
    private static InsuranceCard createCard(String[] str) {
        try {
            String cardNumber = str[0];
            Customer cardHolder = getCustomer(str[1]);
            PolicyHolder policyOwner = (PolicyHolder) getCustomer(str[2]);
            LocalDate expirationDate = LocalDate.parse(str[3]);

            // If the cardholder is a dependant and he/she is not inside the policy owner's list of dependant
            if (cardHolder instanceof Dependant && policyOwner != null) {
                if (!policyOwner.getDependantList().contains(cardHolder)) {
                    throw new IllegalArgumentException("Error creating InsuranceCard: The dependant must be in the policy owner's list of dependants");
                }
                // If the cardholder is a policyholder, then the policy owner must be himself
            } else if (cardHolder instanceof PolicyHolder) {
                if (policyOwner != cardHolder) {
                    throw new IllegalArgumentException("Error creating InsuranceCard: The card holder is a PolicyHolder, so the policy owner should match");
                }
            }

            assert cardHolder != null;
            return new InsuranceCard(cardNumber, cardHolder, policyOwner, expirationDate);

        } catch (ClassCastException e) {
            throw new ClassCastException("Error creating InsuranceCard: PolicyOwner must be of type PolicyHolder. Please check for flaws in database!");
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Error creating InsuranceCard: Invalid expiration date format");
        }
    }
    public static void writeInsuranceCard(InsuranceCard card) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(INSURANCE_FILE_PATH, true));
            String content = card.toData() +
                    "\n";
            bufferedWriter.write(content);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void overWriteInsuranceCards() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(INSURANCE_FILE_PATH));
            StringBuilder content = new StringBuilder();

            for (InsuranceCard card : insuranceCards.values()) {

                content.append(card.toData());
                content.append("\n");
            }

            bufferedWriter.write(content.toString());
            bufferedWriter.close();
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

    public static void readInsuranceCard() {
        File file = new File(INSURANCE_FILE_PATH);
        // If file doesn't exists, we create a new file.
        try {
            if (file.createNewFile()) {
                System.out.println("Insurances file created!");
            }
            // If file already exists, read from file and populate to ClaimProcessSystem
            else {
                loadInsuranceCard(file);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadInsuranceCard(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.ready()) {
                String[] str = reader.readLine().split(",");
                insuranceCards.put(str[0], createCard(str));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InsuranceCard getInsuranceCard(String id) {
        return insuranceCards.get(id);
    }


    public static void readClaim() {
        File file = new File(CLAIM_FILE_PATH);
        // If file doesn't exist, we create a new file.
        try {
            if (file.createNewFile()) {
                System.out.println("Claims file created!");
            }
            // If file already exists, read from file and populate to ClaimProcessSystem
            else {
                loadClaim(file);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadClaim(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.ready()) {
                String[] str = reader.readLine().split(",");
                claims.put(str[0], createClaim(str));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Claim getClaim(String id) {
        return claims.get(id);
    }

    private static Claim createClaim(String[] str) {
        String id = str[0];
        LocalDate claimDate = LocalDate.parse(str[1]);
        Customer insuredPerson = getCustomer(str[2]);
        assert insuredPerson != null;
        System.out.println("Insured person for claim: " + id + " is " + insuredPerson.getId());
        String cardNumber = str[3];
        LocalDate examDate = LocalDate.parse(str[4]);

        int i = 5;
        List<String> documents = new ArrayList<>();
        while (i < str.length) {
            if (str[i].endsWith(".pdf")) {
                documents.add(str[i]);
                i++;
            } else break;
        }

        double claimAmount = Double.parseDouble(str[i++]);
        Claim.ClaimStatus claimStatus = Claim.ClaimStatus.valueOf(str[i++]);
        String bankName = str[i++];
        String receiverName = str[i++];
        String bankNumber = str[i];

        return new Claim(id, claimDate, insuredPerson, cardNumber, examDate, documents, claimAmount, claimStatus, bankName, receiverName, bankNumber);
    }

    public static void writeClaim(Claim claim) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(CLAIM_FILE_PATH, true));
            String content = claim.toData() +
                    "\n";
            bufferedWriter.write(content);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void overWriteClaim() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(CLAIM_FILE_PATH));
            StringBuilder content = new StringBuilder();

            for (Claim claim : claims.values()) {

                content.append(claim.toData());
                content.append("\n");
            }

            bufferedWriter.write(content.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
