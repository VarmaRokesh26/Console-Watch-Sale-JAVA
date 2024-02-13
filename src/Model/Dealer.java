package Model;

public class Dealer {
    
    private String dealerName;
    private String dealerMailid;
    private String dealerLocation;
    private String contactNumer;
    private String password;

    public Dealer(String dealerName, String dealerMailId, String dealerLocation, String contactNumber, String password) {
        this.dealerName = dealerName;
        this.dealerMailid = dealerMailId;
        this.dealerLocation = dealerLocation;
        this.contactNumer = contactNumber;
        this.password = password;
    }

    public String getDealerName() {
        return dealerName;
    }

    public String getDealerMailid() {
        return dealerMailid;
    }

    public String getDealerLocation() {
        return dealerLocation;
    }

    public String getContactNumer() {
        return contactNumer;
    }

    public String getPassWord() {
        return password;
    }
}
