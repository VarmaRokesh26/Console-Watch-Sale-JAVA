package Model;

public class CourierService {

    private String courierServiceName;
    private String courierServiceNumber;

    public CourierService(String courierServiceName, String courierServiceNumber) {
        this.courierServiceName = courierServiceName;
        this.courierServiceNumber = courierServiceNumber;
    }

    public String getCourierServiceName() {
        return courierServiceName;
    }

    public String getCourierServiceNumber() {
        return courierServiceNumber;
    }
}
