package DAO;

public class OrderDAO {
    private String orderId;
    private String userId;
    private String dealerId;
    private String watchId;
    private int quantity;
    private double totalAmount;
    private String paymentMode;
    private String status;

    public OrderDAO() {}

    public OrderDAO(String orderId, String userId, String dealerId, String watchId, int quantity, double totalAmount, String paymentMode, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.dealerId = dealerId;
        this.watchId = watchId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.paymentMode = paymentMode;
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getWatchId() {
        return watchId;
    }

    public void setWatchId(String watchId) {
        this.watchId = watchId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
