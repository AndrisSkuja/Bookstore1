import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;
//import java.sql.DriverManager;



public class Bookstore {
    
    private int sales;
    private int expenditures;
    private String salesPerGenre;
    private String salesPerAuthor;
    private ArrayList<Book> books;
    private User activeUser;

    public Bookstore(int sales, int expenditures, String salesPerGenre, String salesPerAuthor, ArrayList<Book> books,
            User activeUser) {
        this.sales = sales;
        this.expenditures = expenditures;
        this.salesPerGenre = salesPerGenre;
        this.salesPerAuthor = salesPerAuthor;
        this.books = books;
        this.activeUser = activeUser;
    }


    public void loginsequence() { 
        String selection = "";
        Scanner input = new Scanner(System.in);
        this.activeUser = null;
        do{
            
            System.out.println("Welcome to Look Inna Book. press 1 to log in, 2 to create account, 3 to browse without account, or 4 to quit.");

            selection = input.nextLine();

            if(selection.equals("1")){
                this.login();
            }
            else if(selection.equals("2")){
                this.createAccount();
            }
            else if(selection.equals("3")){
                this.browse();
            }
            else if(selection.equals("4")){
                System.out.println("Quitting, thank you for visiting Look Inna Book");
            }
            else{
                System.out.println("Sorry, that input was not recognized");
            }
        } while(!selection.equals("4"));
        input.close();
    }

    public void createAccount(){
        Scanner input = new Scanner(System.in);
        String userID ="";
        String billingInfo ="";
        String shippingInfo ="";
        String password ="";
        Connection connection =null;
        String query = "";
        String testQuery ="";
        boolean fail = false;
        String selection = "";

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/Bookstore", "postgres", "tervete123");
            
            do{
                fail = false;
                System.out.println("Enter a username");
                userID = input.nextLine();

                testQuery = "select user_id from users where user_id = '"+userID+"'";


                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(testQuery);

                while(rs.next()){
                    fail = true;
                    System.out.println("That user is not available. Press 1 to enter a different user or anything else to exit");
                    selection = input.nextLine();
                    if(!selection.equals("1")){
                        return;
                    }
                }
            }while(fail);




    
            System.out.println("Enter billing info");
            billingInfo = input.nextLine();
    
            System.out.println("Enter shipping info");
            shippingInfo =input.nextLine();
    
            System.out.println("Enter a password");
            password = input.nextLine();
    
            query = "insert into users values ('"+userID+"', '"+billingInfo+"', '"+shippingInfo+"', false, '"+password+"');";

            this.databaseEditor(query);

            this.activeUser = new User(userID, billingInfo, shippingInfo, false, password);

            this.userView();


        } catch (Exception e) {
            // TODO: handle exception
        }


    }

    public void login(){
        String user = "";
        String pass = "";
        boolean fail = true;
        String failExit = "";
        Connection connection = null;
        Scanner input = new Scanner(System.in);
        String loginQuery = "";
        do{
            
            System.out.println("Please enter your username");
            user = input.nextLine();

            System.out.println("Please enter your password");
            pass = input.nextLine();

            loginQuery = "select * from users where user_id='"+user+"' and password='"+pass+"'";

            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost/Bookstore", "postgres", "tervete123");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(loginQuery);

                while(rs.next()){
                    fail = false;
                    if(rs.getString(4).equals("t")){
                        this.activeUser = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                        this.ownerView();
                        connection.close();
                        return;
                    }
                    else{
                        this.activeUser = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                        this.userView();
                        connection.close();
                        return;
                    }
                }

                System.out.println("Incorrect username/password. Press 1 to try again or any other key to go back");
                failExit = input.nextLine();
                if(!failExit.equals("1")){
                    fail=false;
                }
                
                connection.close();
            } catch (Exception e) {
                // TODO: handle exception
            }


        }while(fail);
        
    }

    public void browse(){
        boolean exit = true;
        Scanner input = new Scanner(System.in);
        String search = "";
        String selection = "";
        Connection connection = null;
        String searchQuery = "";
        do{
            this.books= new ArrayList<>();
            System.out.println("Welcome to browse.");
            System.out.println("Enter 1 for search by name,");
            System.out.println("Enter 2 for search by author,");
            System.out.println("Enter 3 for search by ISBN,");
            System.out.println("Enter 4 for search by genre,");
            System.out.println("Enter 5 for search by publisher,");
            System.out.println("Enter other key to exit");

            selection = input.nextLine();

            if(selection.equals("1")){
                System.out.println("Enter name");
                search = input.nextLine();
                searchQuery = "select * from books where upper(name) like upper('%"+search+"%')";
            }
            else if(selection.equals("2")){
                System.out.println("Enter author");
                search = input.nextLine();
                searchQuery = "select * from books where upper(author) like upper('%"+search+"%')";
            }
            else if(selection.equals("3")){
                System.out.println("Enter ISBN");
                search = input.nextLine();
                searchQuery = "select * from books where upper(ISBN) like upper('"+search+"')";
            }
            else if(selection.equals("4")){
                System.out.println("Enter genre");
                search = input.nextLine();
                searchQuery = "select * from books where upper(genre) like upper('%"+search+"%')";
            }
            else if(selection.equals("5")){
                System.out.println("Enter publisher");
                search = input.nextLine();
                searchQuery = "select * from books where upper(publisher) like upper('%"+search+"%')";
            }
            else{
                break;
            }

            

            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost/Bookstore", "postgres", "tervete123");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(searchQuery);

                while(rs.next()){
                    System.out.println("ISBN: "+rs.getString(1)+" author: "+
                    rs.getString(2)+" name: "+rs.getString(3)+
                    " genre: "+rs.getString(4)+" publisher: "+rs.getString(5)+
                    " number of pages: "+rs.getString(6)+" price: $"+rs.getString(7)+" rating: "+rs.getString(9));

                    this.books.add(new Book(rs.getString(1), rs.getString(2), rs.getString(3),
                     rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7),
                      rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11),
                       rs.getInt(12)));
                }
                
                System.out.println("Press 1 to return to search, and any other key to exit");
                selection = input.nextLine();
                if(!selection.equals("1")){
                    exit=false;
                }
            } catch (Exception e) {
                // TODO: handle exception
            }

        }while(exit);
        
    }

    public void userBrowse(){
        boolean exit = true;
        Scanner input = new Scanner(System.in);
        String search = "";
        String selection = "";
        Connection connection = null;
        String searchQuery = "";
        do{
            this.books= new ArrayList<>();
            System.out.println("Welcome to browse.");
            System.out.println("Enter 1 for search by name,");
            System.out.println("Enter 2 for search by author,");
            System.out.println("Enter 3 for search by ISBN,");
            System.out.println("Enter 4 for search by genre,");
            System.out.println("Enter 5 for search by publisher,");
            System.out.println("Enter other key to return to user menu");

            selection = input.nextLine();

            if(selection.equals("1")){
                System.out.println("Enter name");
                search = input.nextLine();
                searchQuery = "select * from books where upper(name) like upper('%"+search+"%')";
            }
            else if(selection.equals("2")){
                System.out.println("Enter author");
                search = input.nextLine();
                searchQuery = "select * from books where upper(author) like upper('%"+search+"%')";
            }
            else if(selection.equals("3")){
                System.out.println("Enter ISBN");
                search = input.nextLine();
                searchQuery = "select * from books where upper(ISBN) like upper('"+search+"')";
            }
            else if(selection.equals("4")){
                System.out.println("Enter genre");
                search = input.nextLine();
                searchQuery = "select * from books where upper(genre) like upper('%"+search+"%')";
            }
            else if(selection.equals("5")){
                System.out.println("Enter publisher");
                search = input.nextLine();
                searchQuery = "select * from books where upper(publisher) like upper('%"+search+"%')";
            }
            else{
                break;
            }

            

            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost/Bookstore", "postgres", "tervete123");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(searchQuery);

                while(rs.next()){
                    System.out.println("ISBN: "+rs.getString(1)+" author: "+
                    rs.getString(2)+" name: "+rs.getString(3)+
                    " genre: "+rs.getString(4)+" publisher: "+rs.getString(5)+
                    " number of pages: "+rs.getString(6)+" price: $"+rs.getString(7)+" rating: "+rs.getString(9));

                    this.books.add(new Book(rs.getString(1), rs.getString(2), rs.getString(3),
                     rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7),
                      rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11),
                       rs.getInt(12)));
                }
                
                System.out.println("Press 1 to return to search, 2 to checkout books, and any other key to return to user menu");
                selection = input.nextLine();
                if(selection.equals("2")){
                    this.checkout();
                }
                else if(selection.equals("1")){
                    exit=true;
                }
                else{
                    exit=false;
                }
            } catch (Exception e) {
                // TODO: handle exception
            }

        }while(exit);
        
        
    }

    public void checkout(){
        System.out.println("test");
    }

    public void userView(){
        Scanner input = new Scanner(System.in);
        String selection = "";
        boolean quit = true;
        do{
            System.out.println("Welcome "+this.activeUser.getUserID());
            System.out.println("Press 1 to browse, 2 to see orders, 3 to view checkout basket, or any other key to quit");
            selection = input.nextLine();
            if(selection.equals("1")){
                this.userBrowse();
            }
            else if(selection.equals("2")){
                this.orderView();
            }
            else if(selection.equals("3")){
                
            }
            else{
                quit=false;
            }
        }while(quit);

    }

    public void ownerView(){
        Scanner input = new Scanner(System.in);
        String selection = "";
        boolean quit=true;
        do{
            System.out.println("Welcome "+this.activeUser.getUserID());
            System.out.println("Press 1 to browse, 2 to see orders, 3 to view checkout basket, 4 to add/remove a book, 5 to generate reports, or any other key to quit");
            selection = input.nextLine();
            if(selection.equals("1")){
                this.userBrowse();
            }
            else if(selection.equals("2")){
                this.orderView();
            }
            else if(selection.equals("3")){
                
            }
            else if(selection.equals("4")){
                this.addRemoveBook();
            }
            else if(selection.equals("5")){
                this.reportGenerator();
            }
            else{
                quit = false;
            }
        }while(quit);

    }

    public void orderView(){

    }

    public void addRemoveBook(){

    }

    public void reportGenerator(){

    }

    public void databaseEditor(String query){
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/Bookstore", "postgres", "tervete123");
            Statement st = connection.createStatement();
            st.executeQuery(query);

            connection.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }
    
    public void servertest(){
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/Bookstore", "postgres", "tervete123");

            String myString = "insert into users values ('tuser1', 'tbill1', 'tship1', false, 'password');";

             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(myString);

             while(rs.next()){
                String test = rs.getString("isbn");

                System.out.println(rs.getString(1));
             }


            //  while(rs.next()){
            //     System.out.println(rs.getString("ISBN")+ " " + rs.getString("author") + " " +  rs.getString("name") + " " + rs.getString("genre") + " " + rs.getString("publisher") + " " + rs.getString("price"));
            //  }

            connection.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    

    
    





    public static void main(String[] args) throws Exception {


        Bookstore myBookstore = new Bookstore(0, 0, null, null, new ArrayList<>(), null);

        //myBookstore.servertest();

        //myBookstore.databaseEditor("insert into users values ('tuser2', 'tbill2', 'tship2', false, 'password');");

        myBookstore.loginsequence();
    }


        

}
