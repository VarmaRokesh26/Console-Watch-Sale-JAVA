package Model;

public class CourierService {

    private String courierServiceName;
    private String courierServiceNumber;
    private String courierServiceMailId;
    private String password;

    public CourierService(String courierServiceName, String courierServiceNumber, String courierServiceMailId, String password) {
        this.courierServiceName = courierServiceName;
        this.courierServiceNumber = courierServiceNumber;
        this.courierServiceMailId = courierServiceMailId;
        this.password = password;
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
