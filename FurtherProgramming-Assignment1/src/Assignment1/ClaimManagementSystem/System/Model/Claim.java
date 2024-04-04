package System.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Claim {

    private String id;
    private LocalDate claimDate;
    private Customer insuredPerson;
    private String cardNumber;
    private LocalDate examDate;
    private List<String> documents;
    private double claimAmount;
    private ClaimStatus status;
    private String bankName;
    private String receiverName;
    private String bankNumber;
    public enum ClaimStatus {New, Processing, Done}

    public Claim(String id, LocalDate claimDate, Customer insuredPerson, String cardNumber, LocalDate examDate,
                 List<String> documents, double claimAmount, ClaimStatus status, String bankName, String receiverName,
                 String bankNumber) {
        this.id = id;
        this.claimDate = claimDate;
        this.insuredPerson = insuredPerson;
        this.cardNumber = cardNumber;
        this.examDate = examDate;
        this.documents = documents;
        this.claimAmount = claimAmount;
        this.status = status;
        this.bankName = bankName;
        this.receiverName = receiverName;
        this.bankNumber = bankNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(LocalDate claimDate) {
        this.claimDate = claimDate;
    }

    public Customer getInsuredPerson() {
        return insuredPerson;
    }

    public void setInsuredPerson(Customer insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public void setDocuments(List<String> documents) {
        this.documents = documents;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Claim Details:\n");
        sb.append(String.format("  - ID: %s\n", id));
        sb.append(String.format("  - Claim Date: %s\n", claimDate));
        sb.append(String.format("  - Insured Person: %s\n", insuredPerson.getName()));
        sb.append(String.format("  - Card Number: %s\n", cardNumber));
        sb.append(String.format("  - Exam Date: %s\n", examDate));
        sb.append(String.format("  - Claim Amount: %.2f\n", claimAmount));
        sb.append(String.format("  - Status: %s\n", status));
        return sb.toString();
    }

    public String toData() {
        StringBuilder documentsString = new StringBuilder();
        for (String document : documents) {
            documentsString.append(document).append(",");
        }
        // Remove the trailing comma if there are documents present
        if (!documentsString.isEmpty()) {
            documentsString.deleteCharAt(documentsString.length() - 1);
        }

        return String.format("%s,%s,%s,%s,%s,%s,%.2f,%s,%s,%s,%s",
                id,
                claimDate,
                insuredPerson.getId(),
                cardNumber,
                examDate,
                documentsString,
                claimAmount,
                status,
                bankName,
                receiverName,
                bankNumber
        );
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Claim claim = (Claim) obj;
        return id.equals(claim.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
