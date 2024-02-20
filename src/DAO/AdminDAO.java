package DAO;

public class AdminDAO {
    
    private String adminId;
    private String adminName;
    private String adminMobileNumber;
    private String adminMailid;
    private String adminRole;
    private String password;
    
    public AdminDAO(String adminId, String adminName, String adminMobileNumber, String adminMailid, String adminRole, String password) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminMobileNumber = adminMobileNumber;
        this.adminMailid = adminMailid;
        this.adminRole = adminRole;
        this.password = password;
    }

    public AdminDAO(){}

    public AdminDAO(String adminName, String adminMobileNumber, String adminMailid) {
        this.adminName = adminName;
        this.adminMobileNumber = adminMobileNumber;
        this.adminMailid = adminMailid;
    }

    public AdminDAO(String password) {
        this.password = password;
    }

    public String getAdminId() {
        return adminId;
    }    

    public String getAdminName() {
        return adminName;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public void setAdminMobileNumber(String adminMobileNumber) {
        this.adminMobileNumber = adminMobileNumber;
    }

    public void setAdminMailid(String adminMailid) {
        this.adminMailid = adminMailid;
    }

    public void setAdminRole(String adminRole) {
        this.adminRole = adminRole;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminMobileNumber() {
        return adminMobileNumber;
    }

    public String getAdminMailid() {
        return adminMailid;
    }

    public String getAdminRole() {
        return adminRole;
    }

    public String getPassword() {
        return password;
    }
}
