package DAO;

public class WatchDAO {
    private String watchId;
    private String name;
    private String brand;
    private double price;
    private String description;
    private int number_of_stocks;
    private String dealerId;

    public WatchDAO() {}

    public WatchDAO(String watchId, String name, String brand, double price, String description, int number_of_stocks, String dealerId) {
        this.watchId = watchId;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.number_of_stocks = number_of_stocks;
        this.dealerId = dealerId;
    }
    
    public WatchDAO(String watchId) {
        this.watchId = watchId;
    }

    public void setWatchId(String watchId) {
        this.watchId = watchId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumber_of_stocks(int number_of_stocks) {
        this.number_of_stocks = number_of_stocks;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }


    public String getWatchId() {
        return watchId;
    }
    
    public String getDealerId() {
        return dealerId;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfStocks() {
        return number_of_stocks;
    }
}