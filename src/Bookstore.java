import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.sql.*;
import java.time.*;
//import java.sql.DriverManager;



public class Bookstore {
    
    private ArrayList<Book> books;
    private User activeUser;
    private Scanner input = new Scanner(System.in);

    public Bookstore(int sales, int expenditures, String salesPerGenre, String salesPerAuthor, ArrayList<Book> books,
            User activeUser) {
        this.books = books;
        this.activeUser = activeUser;
    }


    public void loginsequence() { 
        String selection = "";

        this.activeUser = null;
        do{
            
            System.out.println("Welcome to Look Inna Book. Press 1 to log in, 2 to create account, 3 to browse without account, or 4 to quit.");

            selection = this.input.nextLine();

            System.out.print("\033[H\033[2J");

            if(selection.equals("1")){
                this.login();
            }
            else if(selection.equals("2")){
                this.createAccount();
            }
            else if(selection.equals("3")){
                this.gerenalBrowse();
            }
            else if(selection.equals("4")){
                System.out.println("Quitting, thank you for visiting Look Inna Book");
            }
            else{
                System.out.println("Sorry, that input was not recognized");
            }
        } while(!selection.equals("4"));
        this.input.close();
    }

    public void createAccount(){

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
                userID = this.input.nextLine();

                testQuery = "select user_id from users where user_id = '"+userID+"'";


                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(testQuery);

                while(rs.next()){
                    fail = true;
                    System.out.println("That user is not available. Press 1 to enter a different user or anything else to exit");
                    selection = this.input.nextLine();

                    System.out.print("\033[H\033[2J"); //C

                    if(!selection.equals("1")){
                        return;
                    }
                }
            }while(fail);




    
            System.out.println("Enter billing info");
            billingInfo = this.input.nextLine();
    
            System.out.println("Enter shipping info");
            shippingInfo =this.input.nextLine();
    
            System.out.println("Enter a password");
            password = this.input.nextLine();
    
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

        String loginQuery = "";
        do{
            
            System.out.println("Please enter your username");
            user = this.input.nextLine();

            System.out.println("Please enter your password");
            pass = this.input.nextLine();

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
                failExit = this.input.nextLine();
                if(!failExit.equals("1")){
                    fail=false;
                }
                
                connection.close();
            } catch (Exception e) {
                // TODO: handle exception
            }


        }while(fail);
        
    }

    /**
     * typeBrowse determines whether it shows to log in version or the general version of browse. 0 = general, 1 = log in
     */
    public void browse(int typeBrowse){
        boolean exit = true;

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

            selection = this.input.nextLine();

            if(selection.equals("1")){
                System.out.println("Enter name");
                search = this.input.nextLine();
                searchQuery = "select * from books where upper(name) like upper('%"+search+"%')";
            }
            else if(selection.equals("2")){
                System.out.println("Enter author");
                search = this.input.nextLine();
                searchQuery = "select * from books where upper(author) like upper('%"+search+"%')";
            }
            else if(selection.equals("3")){
                System.out.println("Enter ISBN");
                search = this.input.nextLine();
                searchQuery = "select * from books where upper(ISBN) like upper('"+search+"')";
            }
            else if(selection.equals("4")){
                System.out.println("Enter genre");
                search = this.input.nextLine();
                searchQuery = "select * from books where upper(genre) like upper('%"+search+"%')";
            }
            else if(selection.equals("5")){
                System.out.println("Enter publisher");
                search = this.input.nextLine();
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
                       rs.getInt(12),rs.getInt(13)));
                }

                exit = endBrowse(0, input);
                
            } catch (Exception e) {
                // TODO: handle exception
            }

        }while(exit);
        
    }

    private boolean endBrowse(int i, Scanner input) {

        String selection = "";

        if(i == 0){
             
            System.out.println("Press 1 to return to search, and any other key to exit");
            selection = this.input.nextLine();

            if(!selection.equals("1")){
                return false;
            }
            
        } else if(i == 1){

            System.out.println("Press 1 to return to search, 2 to checkout books, and any other key to return to user menu");
            selection = this.input.nextLine();
            if(selection.equals("2")){
                this.checkout();
            }
            else if(selection.equals("1")){
                return true;
            }
            else{
                return false;
            }

        }

        return true; //IDK IF IT SHOULD BE FALSE
    }


    public void gerenalBrowse(){            
                browse(0);    
    }

    public void userBrowse(){      
               browse(1);
    }

    public void checkout(){

        String confirm = "";
        int selection = -1;
        int amount = -1;
        Connection connection = null;

        boolean addingLoop = true;
        boolean indexLoop = true;
        boolean amountLoop = true;
    
        while(addingLoop){
            System.out.println("Please enter the index for the book you wish to add to your basket");

            indexLoop=true;
            while(indexLoop){
                
                try {
                    selection = this.input.nextInt();
                    this.input.nextLine();
                    if(selection>=0 && selection<this.books.size()){
                        indexLoop = false;
                    }
                    else{
                        System.out.println("Please select an index within bounds of the search results");
                    }
                    
                } catch (Exception e) {
                    System.out.println("Please enter a number");
                }
            }

            System.out.println("How many copies of "+this.books.get(selection).getName()+" would you like to add to your basket? there are "+ this.books.get(selection).getAmountStored()+" available.");
            
            amountLoop=true;
            while(amountLoop){

                try {
                    amount = this.input.nextInt();
                    this.input.nextLine();
                    if(amount>=0 && amount<=this.books.get(selection).getAmountStored()){
                        amountLoop = false;
                    }
                    else{
                        System.out.println("That number is not valid");
                    }
                    
                } catch (Exception e) {
                    System.out.println("Please enter a number");
                }
            }

            System.out.println("Adding "+amount+" copies of "+this.books.get(selection).getName()+" to basket. Press 1 to confirm, press else to return browse");

            confirm = this.input.nextLine();
            
            if(confirm.equals("1")){
                this.books.get(selection).setAmountStored(this.books.get(selection).getAmountStored()-amount);
                this.databaseEditor("update books set amount_stored="+this.books.get(selection).getAmountStored()+" where isbn='"+this.books.get(selection).getISBN()+"'");

                if(this.books.get(selection).getAmountStored() < this.books.get(selection).getMin()){
                    this.books.get(selection).setAmountStored(this.books.get(selection).getAmountStored()+this.books.get(selection).getPrevMonthSold());
                    this.databaseEditor("update books set amount_stored="+this.books.get(selection).getAmountStored()+" where isbn='"+this.books.get(selection).getISBN()+"'");
                }


                try {
                    connection = DriverManager.getConnection("jdbc:postgresql://localhost/Bookstore", "postgres", "tervete123");
                    Statement st = connection.createStatement();
                    ResultSet rs = st.executeQuery("select * from basket_contents where user_id='"+this.activeUser.getUserID()+"'");
                    CheckoutBasket userBasket = new CheckoutBasket(this.activeUser.getUserID(), new HashMap<>());

                    while(rs.next()){
                        userBasket.getBooks().put(rs.getString(2), rs.getInt(3));
                    }


                    boolean found=false;
                    for(String i : userBasket.getBooks().keySet()){
                        if(i.equals(this.books.get(selection).getISBN())){
                            userBasket.getBooks().put(this.books.get(selection).getISBN(), userBasket.getBooks().get(this.books.get(selection).getISBN())+amount);
                            found=true;
                            this.databaseEditor("update basket_contents set amount="+userBasket.getBooks().get(this.books.get(selection).getISBN())+
                            " where user_id='"+this.activeUser.getUserID()+"' and isbn='"+i+"'");
                            break;
                        }
                    }

                    if(!found){
                        userBasket.getBooks().put(this.books.get(selection).getISBN(), amount);
                        this.databaseEditor("insert into basket_contents values ('"+this.activeUser.getUserID()+"', '"+userBasket.getBooks().get(this.books.get(selection).getISBN())+
                        "', "+amount+");");
                    }

                    
                    
                } catch (Exception e) {
                    // TODO: handle exception
                }

                System.out.println("Would you like to add another book from your search to your basket? press 1 for yes, other to return to browse");
                confirm = this.input.nextLine();
                if(!confirm.equals("1")){
                    System.out.println("Thank you, please naviagte to basket view to place order");
                    return;
                }
            }
            else{
                addingLoop=false;
            }
        }
        
    }

    public void userView(){

        String selection = "";
        boolean quit = true;
        do{
            System.out.println("Welcome "+this.activeUser.getUserID());
            System.out.println("Press 1 to browse, 2 to see orders, 3 to view checkout basket, or any other key to quit");
            selection = this.input.nextLine();
            if(selection.equals("1")){
                this.userBrowse();
            }
            else if(selection.equals("2")){
                this.orderView();
            }
            else if(selection.equals("3")){
                this.viewBasket();
            }
            else{
                quit=false;
            }
        }while(quit);

    }

    public void ownerView(){

        String selection = "";
        boolean quit=true;
        do{
            System.out.println("Welcome "+this.activeUser.getUserID());
            System.out.println("Press 1 to browse, 2 to see orders, 3 to view checkout basket, 4 to add/remove a book, 5 to generate reports, or any other key to quit");
            selection = this.input.nextLine();
            if(selection.equals("1")){
                this.userBrowse();
            }
            else if(selection.equals("2")){
                this.orderView();
            }
            else if(selection.equals("3")){
                this.viewBasket();
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
        boolean contents=false;
        Connection connection = null;
        String orderQuery = "select * from \"order\" where user_id='"+this.activeUser.getUserID()+"'";
        try {
            
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/Bookstore", "postgres", "tervete123");
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(orderQuery);

            while(rs.next()){
                contents = true;
                System.out.println("Order number: "+rs.getString(1)+" Billing info: "+rs.getString(2)+
                " shipping info: "+rs.getString(3)+" arrival date: "+rs.getString(4)+" order date: "+
                rs.getString(5)+" tracking status: "+rs.getString(6));
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        if(!contents){
            System.out.println("You do not have any orders");
        }
    }

    public void viewBasket(){

        Connection connection = null;
        boolean contents = false;
        String basketQuery = "select isbn, amount from basket_contents where user_id ='"+this.activeUser.getUserID()+"'";
        String bookQuery = "select name, price, number_of_books_sold from books where isbn='";
        int totalPrice = 0;
        String shippingInfo = "";
        String billingInfo = "";
        String orderQuery = "insert into \"order\" values (";
        String orderContentsQuery = "insert into order_contents values ('";
        String bookUpdateQuery = "update books set number_of_books_sold = ";
        String basketUpdateQuery = "delete from basket_contents where user_id = '"+this.activeUser.getUserID()+"' and isbn = '";
        String basketClearQuery = "delete from basket_contents where user_id = '"+this.activeUser.getUserID()+"'";
        LocalDate currentDate = LocalDate.now();
        HashMap<String, Integer> basket = new HashMap<String, Integer>();
        ArrayList<Integer> booksSold = new ArrayList<>();
        String Selection = "";

        try {
            
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/Bookstore", "postgres", "tervete123");
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(basketQuery);

            while(rs.next()){
                contents = true;
                Statement st1 = connection.createStatement();
                ResultSet rs1 = st1.executeQuery(bookQuery+rs.getString(1)+"'");
                rs1.next();

                System.out.println("ISBN: "+rs.getString(1)+" Name: "+rs1.getString(1)+", amount: "+rs.getString(2));

                basket.put(rs.getString(1), rs.getInt(2));
                booksSold.add(rs1.getInt(3));

                totalPrice = totalPrice+rs1.getInt(2)*rs.getInt(2);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        


        if(contents){
            System.out.println("total is: $"+totalPrice);
            System.out.println("Press 1 to place an order with the contents of the checkout basket, 2 to remove book from the basket, or press else to return to user menu");

            Selection = this.input.nextLine();
            if(Selection.equals("1")){
                System.out.println("enter shipping info");
                shippingInfo=this.input.nextLine();
                System.out.println("enter billing info");
                billingInfo=this.input.nextLine();
    
                try {
                    connection = DriverManager.getConnection("jdbc:postgresql://localhost/Bookstore", "postgres", "tervete123");
                    Statement st = connection.createStatement();
                    ResultSet rs = st.executeQuery("select nextval('order_id')");
                    rs.next();
    
                    this.databaseEditor(orderQuery+rs.getInt(1)+", '"+billingInfo+"', '"+shippingInfo+"', '"+LocalDate.parse(currentDate.toString()).plusDays(7)+
                    "', '"+currentDate+"', 'In warehouse', '"+this.activeUser.getUserID()+"')");
    
                    int j=0;
                    for(String i : basket.keySet()){
                        this.databaseEditor(orderContentsQuery+this.activeUser.getUserID()+"', '"+i+"', "+basket.get(i)+")");
    
                        int total = basket.get(i)+booksSold.get(j);
                        this.databaseEditor(bookUpdateQuery+total);
                        j = j+1;
                    }
    
                    this.databaseEditor(basketClearQuery);
                    
                    
                } catch (Exception e) {
                    // TODO: handle exception
                }
                System.out.println("Successfully placed order. View your orders in the view orders page.");
            }
            else if(Selection.equals("2")){
                System.out.println("Please enter the ISBN for the book you wish to remove from your basket");

                Selection = this.input.nextLine();
                boolean temp=true;
                while(temp){
                    if(!basket.get(Selection).equals(null)){
                        this.databaseEditor(basketUpdateQuery+Selection+"'");

                        try {
                            connection = DriverManager.getConnection("jdbc:postgresql://localhost/Bookstore", "postgres", "tervete123");
                            Statement st = connection.createStatement();
                            ResultSet rs = st.executeQuery("select amount_stored from books where isbn='"+Selection+"'");
                            rs.next();
                            
                            
                            this.databaseEditor("update books set amount_stored="+basket.get(Selection)+rs.getInt(1) + " where isbn='"+Selection+"'");
                        } catch (Exception e) {
                            // TODO: handle exception
                        }


                        System.out.print("Successfully removed");
                        temp=false;
                    }
                    else{
                        System.out.println("Not a valid ISBN");
                    }
                }

            }
        }
        else{
            System.out.println("You do not have any basket contents, add books to your basket through browse");
        }

    }

    public void addRemoveBook(){
        String selection = "";
        int intSelection = -1;
        Book newBook = new Book(null,null,null,null,null,0,0,0,0,0,0,0,0);
        Connection connection = null;
        System.out.println("press 1 to add, 2 to remove, other to exit");
        selection = this.input.nextLine();

        if(selection.equals("1")){
            System.out.println("Enter ISBN to add");
            selection = this.input.nextLine();

            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost/Bookstore", "postgres", "tervete123");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("select isbn from books");

                while(rs.next()){
                    if(selection.equals(rs.getString(1))){
                        System.out.println("That book already exists");
                        return;
                    }
                }

            } catch (Exception e) {
                // TODO: handle exception
            }

            newBook.setISBN(selection);

            System.out.println("Enter author");
            selection=this.input.nextLine();
            newBook.setAuthor(selection);
            
            System.out.println("Enter name");
            selection=this.input.nextLine();
            newBook.setName(selection);

            System.out.println("Enter genre");
            selection=this.input.nextLine();
            newBook.setGenre(selection);

            System.out.println("Enter publisher");
            selection=this.input.nextLine();
            newBook.setPublisher(selection);

            System.out.println("Enter number of pages");
            intSelection=this.input.nextInt();
            this.input.nextLine();
            newBook.setPages(intSelection);

            System.out.println("Enter price");
            intSelection=this.input.nextInt();
            this.input.nextLine();
            newBook.setPrice(intSelection);

            System.out.println("Enter sales percentage");
            intSelection=this.input.nextInt();
            this.input.nextLine();
            newBook.setSalesPercent(intSelection);

            System.out.println("Enter rating");
            intSelection=this.input.nextInt();
            this.input.nextLine();
            newBook.setRating(intSelection);

            System.out.println("Enter minimum stock");
            intSelection=this.input.nextInt();
            this.input.nextLine();
            newBook.setMin(intSelection);

            System.out.println("Enter number of books sold");
            intSelection=this.input.nextInt();
            this.input.nextLine();
            newBook.setBooksSold(intSelection);

            System.out.println("Enter amount stored");
            intSelection=this.input.nextInt();
            this.input.nextLine();
            newBook.setAmountStored(intSelection);

            System.out.println("Enter number sold previous month");
            intSelection=this.input.nextInt();
            this.input.nextLine();
            newBook.setPrevMonthSold(intSelection);

            String query = "insert into books values('"+newBook.getISBN()+"','"+newBook.getAuthor()+
            "', '"+newBook.getName()+"', '"+newBook.getGenre()+"', '"+
            newBook.getPublisher()+"', "+newBook.getPages()+", "+newBook.getPrice()+
            ", "+newBook.getSalesPercent()+", "+newBook.getRating()+", "+newBook.getMin()+
            ", "+newBook.getBooksSold()+", "+newBook.getAmountStored()+", "+newBook.getPrevMonthSold()+")";

            this.databaseEditor(query);
            System.out.println("Successfully added book");
            
        }
        else if(selection.equals("2")){
            System.out.println("Enter ISBN to remove");
            selection = this.input.nextLine();

            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost/Bookstore", "postgres", "tervete123");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("select isbn from books");

                while(rs.next()){
                    if(selection.equals(rs.getString(1))){
                        this.databaseEditor("delete from books where isbn='"+selection+"'");
                        System.out.println("Successfully deleted book");
                        return;
                    }
                }
                System.out.println("Book not found");

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public void reportGenerator(){
        int temp1=0;
        int temp2=0;
        String selection = "";
        Connection connection = null;
        System.out.println("Press 1 for overall sales vs expenditures, 2 for sales vs genres, 3 for sales vs authors, press other to return");

        selection = this.input.nextLine();
        if(selection.equals("1")){
            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost/Bookstore", "postgres", "tervete123");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("select * from v_sales_vs_expenditures");

                double temp = 0;
                while(rs.next()){
                    temp1 = temp1 + rs.getInt(1)*rs.getInt(3);
                    temp2 = temp2 + rs.getInt(1)*rs.getInt(2)*rs.getInt(3);
                    temp = temp + temp2*0.01;
                }

                System.out.println("Sales: "+temp1+", expenditures: "+temp);

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        else if(selection.equals("2")){
            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost/Bookstore", "postgres", "tervete123");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("select * from v_sale_per_genre");

                while(rs.next()){
                    temp1 = rs.getInt(1)*rs.getInt(2);
                    System.out.println("sales: "+temp1+", genre: "+rs.getString(3));
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        else if(selection.equals("3")){
            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost/Bookstore", "postgres", "tervete123");
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("select * from v_sales_per_author");

                while(rs.next()){
                    temp1 = rs.getInt(1)*rs.getInt(2);
                    System.out.println("sales: "+temp1+", author: "+rs.getString(3));
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
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
    


   public static void main(String[] args) throws Exception {
        Bookstore myBookstore = new Bookstore(0, 0, null, null, new ArrayList<>(), null);

        myBookstore.loginsequence();
    }


        

}
