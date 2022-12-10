import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;



public class Bookstore {
    
    private int sales;
    private int expenditures;
    private int salesPerGenre;
    private int salesPerAuthor;
    private ArrayList<Book> books;

    public Bookstore(int sales, int expenditures, int salesPerGenre, int salesPerAuthor, ArrayList<Book> books) {
        this.sales = sales;
        this.expenditures = expenditures;
        this.salesPerGenre = salesPerGenre;
        this.salesPerAuthor = salesPerAuthor;
        this.books = books;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getExpenditures() {
        return expenditures;
    }

    public void setExpenditures(int expenditures) {
        this.expenditures = expenditures;
    }

    public int getSalesPerGenre() {
        return salesPerGenre;
    }

    public void setSalesPerGenre(int salesPerGenre) {
        this.salesPerGenre = salesPerGenre;
    }

    public int getSalesPerAuthor() {
        return salesPerAuthor;
    }

    public void setSalesPerAuthor(int salesPerAuthor) {
        this.salesPerAuthor = salesPerAuthor;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    

    

    
    





    public static void main(String[] args) throws Exception {
        Connection connection = null;
        System.out.println("Hello, World!");
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/Bookstore", "postgres", "tervete123");
    
            System.out.println("it connected");
    
            connection.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


        

}
