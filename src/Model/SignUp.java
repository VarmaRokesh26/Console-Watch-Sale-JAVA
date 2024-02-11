package Model;

public class SignUp {
    private String userName;
    private String mobileNumber;
    private String emailId;
    private String address;
    private String password;

    public SignUp(String userName, String mobileNumber, String emailId, String address, String password) {
        this.userName = userName;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
        this.address = address;
        this.password = password;
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