package Model;

public class CourierService {

    private String courierServiceId;
    private String courierServiceName;
    private String courierServiceNumber;
    private String courierServiceMailId;
    private String password;

    public CourierService(String courierServiceId, String courierServiceName, String courierServiceNumber, String courierServiceMailId, String password) {
        
        this.courierServiceId = courierServiceId;
        this.courierServiceName = courierServiceName;
        this.courierServiceNumber = courierServiceNumber;
        this.courierServiceMailId = courierServiceMailId;
        this.password = password;
    }

    public String getCourierServiceId() {
        return courierServiceId;
    }
    
    public String getCourierServiceName() {
        return courierServiceName;
    }

    public String getCourierServiceMailId() {
        return courierServiceMailId;
    }

    public String getPassword() {
        return password;
    }

    public String getCourierServiceNumber() {
        return courierServiceNumber;
    }
}
