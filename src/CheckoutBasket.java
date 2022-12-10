public class CheckoutBasket {

    private String userID;
    private String books;
    public CheckoutBasket(String userID, String books) {
        this.userID = userID;
        this.books = books;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getBooks() {
        return books;
    }
    public void setBooks(String books) {
        this.books = books;
    }
    
    
}
