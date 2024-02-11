package Model;

public class Dealer {
    
    private String dealerName;
    private String dealerLocation;
    private String contactNumer;

    public Dealer(String dealerName, String dealerLocation, String contactNumber) {
        this.dealerName = dealerName;
        this.dealerLocation = dealerLocation;
        this.contactNumer = contactNumber;
    }

    public String getDealerName() {
        return dealerName;
    }

    public String getDealerLocation() {
        return dealerLocation;
    }

    public String getContactNumer() {
        return contactNumer;
    }
}
