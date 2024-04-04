package System.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer {
    private String id;
    private String fullName;
    private Card insuranceCard;
    private List<Claim> claims;
    public Customer(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
        claims = new ArrayList<>();
    }
    public String getId() {return id;}
    public String getName() {return fullName;}
    public Card getInsuranceCard() {return insuranceCard;}
    public List<Claim> getClaims() {return claims;}

    public void setInsuranceCard(Card insuranceCard) {
        this.insuranceCard = insuranceCard;
    }


    public void update(Claim oldClaim, Claim newClaim) {
        for (Claim claim : claims) {
            if (claim == oldClaim) {
                claims.remove(claim);
                return;
            }
        }
        claims.add(newClaim);
    }
    public void removeClaim(Claim claim) {
        claims.remove(claim);
    }

    public String toData() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id).append(",");
        stringBuilder.append(fullName).append(",");
        stringBuilder.append(insuranceCard == null ? null : insuranceCard.getCardNumber()).append(",");
        for (Claim claim : claims) {
            stringBuilder.append(claim.getId()).append(",");
        }
        if (this instanceof PolicyHolder) {
            for (String id : ((PolicyHolder) this).getDependantsIDS()) {
                stringBuilder.append(id).append(",");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        Customer customer = (Customer) object;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this instanceof PolicyHolder) {
            stringBuilder.append("Customer Details (Policy Holder): \n");
        } else if (this instanceof Dependant) {
            stringBuilder.append("Customer Details (Dependant): \n");
        }
        stringBuilder.append(String.format("  - ID: %s\n", id));
        stringBuilder.append(String.format("  - Name: %s\n", fullName));
        stringBuilder.append(String.format("  - Insurance Card: %s\n", insuranceCard == null ? "None" : insuranceCard.getCardNumber()));
        stringBuilder.append("  - Claims:\n");
        if (claims.isEmpty()) {
            stringBuilder.append("      None\n");
        } else {
            for (Claim claim : claims) {
                stringBuilder.append(String.format("      - %s\n", claim.getId()));
            }
        }
        return stringBuilder.toString();
    }
}
