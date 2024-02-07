package Model;

public class Profile {
	private String Name;
	private String emailId;
	private String mobileNumber;
	private String address;
	
	public Profile(String Name, String mobileNumber, String emailId) {
		this.Name = Name;
		this.mobileNumber = mobileNumber;
		this.emailId = emailId;
	}
	
	public Profile(String Name, String mobileNumber, String emailId, String address) {
		this.Name = Name;
		this.mobileNumber = mobileNumber;
		this.emailId = emailId;
		this.address = address;
	}
	
	public String getName() {
		return Name;
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
}