public class User {
    
    private String userID;
    private String billingInfo;
    private String shippingInfo;
    public User(String userID, String billingInfo, String shippingInfo) {
        this.userID = userID;
        this.billingInfo = billingInfo;
        this.shippingInfo = shippingInfo;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getBillingInfo() {
        return billingInfo;
    }
    public void setBillingInfo(String billingInfo) {
        this.billingInfo = billingInfo;
    }
    public String getShippingInfo() {
        return shippingInfo;
    }
    public void setShippingInfo(String shippingInfo) {
        this.shippingInfo = shippingInfo;
    }

    


}
