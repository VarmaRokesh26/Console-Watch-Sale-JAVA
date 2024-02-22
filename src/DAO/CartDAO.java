package DAO;

public class CartDAO {
    private String cartId;
    private String userId;
    private String watchId;
    private String cartDetails;

    public CartDAO() {}
    public CartDAO(String cartId, String userId, String watchId, String cartDetails) {
        this.cartId = cartId;
        this.userId = userId;
        this.watchId = watchId;
        this.cartDetails = cartDetails;
    }

    public CartDAO(String watchId, String userId) {
        this.watchId = watchId;
        this.userId = userId;
    }

    public CartDAO(String userId) {
        this.userId = userId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWatchId() {
        return watchId;
    }

    public void setWatchId(String watchId) {
        this.watchId = watchId;
    }

    public String getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(String cartDetails) {
        this.cartDetails = cartDetails;
    }

    
    
}
