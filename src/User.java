public class User {
    
    private String userID;
    private String billingInfo;
    private String shippingInfo;
    private boolean userType;
    private String password;
    
    public User(String userID, String billingInfo, String shippingInfo, boolean userType, String password) {
        this.userID = userID;
        this.billingInfo = billingInfo;
        this.shippingInfo = shippingInfo;
        this.userType = userType;
        this.password = password;
    }
    public User(String string, String string2, String string3, String string4, String string5) {
        this.userID = string;
        this.billingInfo = string2;
        this.shippingInfo = string3;
        if(string4.equals("t")){
            this.userType = true;
        }
        else{
            this.userType = false;
        }
        
        this.password = string5;
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
