package Model;

public class Watch {
    private String watchId;
    private String name;
    private String brand;
    private double price;
    private String description;
    private int number_of_stocks;
    private String dealerId;

    public Watch(String watchId, String name, String brand, double price, String description, int number_of_stocks, String dealerId) {
        this.watchId = watchId;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.number_of_stocks = number_of_stocks;
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