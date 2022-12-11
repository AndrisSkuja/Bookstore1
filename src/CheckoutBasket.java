import java.util.ArrayList;
import java.util.HashMap;

public class CheckoutBasket {

    private String userID;
    private HashMap<String, Integer> books;

    
    
    public CheckoutBasket(String userID, HashMap<String, Integer> books) {
        this.userID = userID;
        this.books = books;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public HashMap<String, Integer> getBooks() {
        return books;
    }
    public void setBooks(HashMap<String, Integer> books) {
        this.books = books;
    }
    
    
    
    
}
