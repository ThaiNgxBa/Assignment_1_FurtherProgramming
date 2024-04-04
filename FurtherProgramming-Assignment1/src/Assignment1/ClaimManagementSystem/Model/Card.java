package Model;

import java.time.LocalDate;

public class Card {
    private String cardNumber;
    private Customer cardHolder;
    private Policy policyOwner;
    private LocalDate expirationDate;

    public Card(String cardNumber, Customer cardHolder, Policy policyOwner, LocalDate expirationDate) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.policyOwner = policyOwner;
        this.expirationDate = expirationDate;
        cardHolder.setInsuranceCard(this);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Customer getCardHolder() {
        return cardHolder;
    }

    public Policy getPolicyOwner() {
        return policyOwner;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardHolder=" + cardHolder +
                ", policyOwner=" + policyOwner +
                ", expirationDate=" + expirationDate +
                '}';
    }
    public String toData() {
        return String.format("%s,%s,%s,%s",
                cardNumber,
                cardHolder.getId(),
                policyOwner.getId(),
                expirationDate);
    }
}
