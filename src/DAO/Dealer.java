package DAO;

public class Dealer {
    
    private String dealerId;
    private String dealerName;
    private String dealerMailid;
    private String dealerLocation;
    private String contactNumer;
    private String password;

    public Dealer(String dealerId, String dealerName, String dealerMailId, String dealerLocation, String contactNumber, String password) {
        
        this.dealerId = dealerId;
        this.dealerName = dealerName;
        this.dealerMailid = dealerMailId;
        this.dealerLocation = dealerLocation;
        this.contactNumer = contactNumber;
        this.password = password;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public void setDealerMailid(String dealerMailid) {
        this.dealerMailid = dealerMailid;
    }

    public void setDealerLocation(String dealerLocation) {
        this.dealerLocation = dealerLocation;
    }

    public void setContactNumer(String contactNumer) {
        this.contactNumer = contactNumer;
    }

    public void setPassword(String password) {
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

    public String getPassword() {
        return password;
    }
}
