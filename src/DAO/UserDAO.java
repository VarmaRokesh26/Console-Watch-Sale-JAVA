package DAO;

public class UserDAO {
    private String userId;
    private String userName;
    private String mobileNumber;
    private String emailId;
    private String address;
    private String password;

    public UserDAO() {}

    public UserDAO(String userId, String userName, String mobileNumber, String emailId, String address, String password) {
        this.userId = userId;
        this.userName = userName;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
        this.address = address;
        this.password = password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDAO(String loginEmail, String loginPassword) {
        emailId = loginEmail;
        password = loginPassword; 
    }

    public UserDAO(String userId) {
        this.userId = userId;
    }
    
    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }
}