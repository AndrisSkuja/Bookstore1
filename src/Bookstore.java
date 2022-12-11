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

    public Bookstore(int sales, int expenditures, String salesPerGenre, String salesPerAuthor, ArrayList<Book> books) {
        this.sales = sales;
        this.expenditures = expenditures;
        this.salesPerGenre = salesPerGenre;
        this.salesPerAuthor = salesPerAuthor;
        this.books = books;
    }

    public void loginsequence() { 
        String selection = "";
        Scanner input = new Scanner(System.in);
        do{
            
            System.out.println("Welcome to Look Inna Book. press 1 to log in, 2 to create account, 3 to browse without account, or 4 to quit.");

            selection = input.nextLine();

            if(selection.equals("1")){
                this.login();
            }
            else if(selection.equals("2")){
    
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

    public void login(){
        String user = "";
        String pass = "";
        boolean fail = true;
        String failExit = "";
        Connection connection = null;
        Scanner input = new Scanner(System.in);
        String loginQuery = "select * from \"Users\" where \"Users\".\"user_ID\"='"+user+"' and \"Users\".\"password\"='"+pass+"'";
        do{
            
            System.out.println("Please enter your username");
            user = input.nextLine();

            System.out.println("Please enter your password");
            pass = input.nextLine();

            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost/Bookstore", "postgres", "tervete123");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(loginQuery);

                while(rs.next()){
                    fail = false;
                    if(rs.getString(4).equals("t")){
                        this.ownerView(rs.getString(1));
                        connection.close();
                        return;
                    }
                    else{
                        this.userBrowse(rs.getString(1));
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
        input.close();
    }

    public void browse(){
        boolean exit = true;
        Scanner input = new Scanner(System.in);
        String search = "";
        String selection = "";
        Connection connection = null;
        String searchQuery = "";
        do{
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
                searchQuery = "select * from \"Books\" where name like '%"+search+"%'";
            }
            else if(selection.equals("2")){
                System.out.println("Enter author");
                search = input.nextLine();
                searchQuery = "select * from \"Books\" where author ='"+search+"'";
            }
            else if(selection.equals("3")){
                System.out.println("Enter ISBN");
                search = input.nextLine();
                searchQuery = "select * from \"Books\" where ISBN ='"+search+"'";
            }
            else if(selection.equals("4")){
                System.out.println("Enter genre");
                search = input.nextLine();
                searchQuery = "select * from \"Books\" where genre ='"+search+"'";
            }
            else if(selection.equals("5")){
                System.out.println("Enter publisher");
                search = input.nextLine();
                searchQuery = "select * from \"Books\" where publisher ='"+search+"'";
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
                
                System.out.println("Press 1 ");
            } catch (Exception e) {
                // TODO: handle exception
            }

        }while(exit);
        
    }

    public void userBrowse(String user){

    }

    public void ownerView(String user){

    }
    
    public void servertest(){
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/Bookstore", "postgres", "tervete123");

            String myString = "select * from \"Books\"";

             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(myString);

             while(rs.next()){
                String test = rs.getString("ISBN");

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


        Bookstore myBookstore = new Bookstore(0, 0, null, null, new ArrayList<>());

        //myBookstore.servertest();

        myBookstore.loginsequence();
    }


        

}
