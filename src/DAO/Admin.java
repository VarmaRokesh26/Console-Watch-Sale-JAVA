package DAO;

public class Admin {
    
    private String adminId;
    private String adminName;
    private String adminMobileNumber;
    private String adminMailid;
    private String adminRole;
    private String password;
    
    public Admin(String adminId, String adminName, String adminMobileNumber, String adminMailid, String adminRole, String password) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminMobileNumber = adminMobileNumber;
        this.adminMailid = adminMailid;
        this.adminRole = adminRole;
        this.password = password;
    }

    public String getAdminId() {
        return adminId;
    }    

    public String getAdminName() {
        return adminName;
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
