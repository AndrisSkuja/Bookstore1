public class Order {
    private String billingInfo;
    private String shippingInfo;
    private String orderNumber;
    private String estimatedArrivalDate;
    private String orderDate;
    private String trackingStatus;
   
    public Order(String billingInfo, String shippingInfo, String orderNumber, String estimatedArrivalDate,
            String orderDate, String trackingStatus) {
        this.billingInfo = billingInfo;
        this.shippingInfo = shippingInfo;
        this.orderNumber = orderNumber;
        this.estimatedArrivalDate = estimatedArrivalDate;
        this.orderDate = orderDate;
        this.trackingStatus = trackingStatus;
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
    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
    public String getEstimatedArrivalDate() {
        return estimatedArrivalDate;
    }
    public void setEstimatedArrivalDate(String estimatedArrivalDate) {
        this.estimatedArrivalDate = estimatedArrivalDate;
    }
    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public String getTrackingStatus() {
        return trackingStatus;
    }
    public void setTrackingStatus(String trackingStatus) {
        this.trackingStatus = trackingStatus;
    }

    
}
