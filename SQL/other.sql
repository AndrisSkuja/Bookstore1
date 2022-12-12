All SQL 
Note that line numbers may be not 100% accurate


/*
Used to see if the id the user is trying to create is already in use or not.
“userID” is the ID the user tried to enter
Line 72: "select user_id from users where user_id = '"+userID+"'" 


Used to see if the user and password the user inputed correspond to a tuple inside the database. If it is, the user will have successfully log in to their account
Line 133: "select * from users where user_id='"+user+"' and password='"+pass+"'"


Used to retrieve all of the books with the name inputed by the user. Later shown to the user for selection purposes. 
Line 194: "select * from books where upper(name) like upper('%"+search+"%')"

Used to retrieve all of the books with the author inputed by the user. Later shown to the user for selection purposes. 
Line 199: "select * from books where upper(author) like upper('%"+search+"%')"

Used to retrieve all of the books with the ISBN inputed by the user. Later shown to the user for selection purposes. 
LIne 204: "select * from books where upper(ISBN) like upper('"+search+"')"

Used to retrieve all of the books with the genre inputed by the user. Later shown to the user for selection purposes. 
Line 209: "select * from books where upper(genre) like upper('%"+search+"%')"

Used to retrieve all of the books with the publisher inputed by the user. Later shown to the user for selection purposes. 
Line 214: "select * from books where upper(publisher) like upper('%"+search+"%')"

Updates the amount of books stored in the bookstore by decreasing it by the amount an user ordered. 
Line 397: "update books set amount_stored="+this.books.get(selection).getAmountStored()+" where isbn='"+this.books.get(selection).getISBN()+"'"

Used to update the amount of books stored in the bookcase by increasing it by the amount of books sold last month
Line 401: "update books set amount_stored="+this.books.get(selection).getAmountStored()+" where isbn='"+this.books.get(selection).getISBN()+"'"


Checking the current basket contents of the user. Used to determine if the user is trying to add a book that is already in the basket
LIne 408: "select * from basket_contents where user_id='"+this.activeUser.getUserID()+"'"

If the user is adding more of the same book to their basket, update the amount in basket contents
"update basket_contents set amount="+userBasket.getBooks().get(this.books.get(selection).getISBN())+
                            " where user_id='"+this.activeUser.getUserID()+"' and isbn='"+i+"'"

If the user is adding a new book to their checkout basket, insert it to their checkout basket
"insert into basket_contents values ('"+this.activeUser.getUserID()+"', '"+userBasket.getBooks().get(this.books.get(selection).getISBN())+
"', "+amount+");"


Used to retrieve all the orders a specific user has. Usedtoshow them to the user.
"select * from \"order\" where user_id='"+this.activeUser.getUserID()+"'"

used to get display basket contents and get values that will be used
"select isbn, amount from basket_contents where user_id ='"+this.activeUser.getUserID()+"'"

used to display book name and get values that will be used
"select name, price, number_of_books_sold from books where isbn='"

used to insert new order created by function
"insert into \"order\" values ("

used to insert new order contents created by function
"insert into order_contents values ('"

used to updated number of books sold once order has been made
"update books set number_of_books_sold = "

used to delete books that a user removed from thier basket
"delete from basket_contents where user_id = '"+this.activeUser.getUserID()+"' and isbn = '"

used to clear a user's basket once an order has been made
"delete from basket_contents where user_id = '"+this.activeUser.getUserID()+"'"

used to get the order id for the new order from the sequence
"select nextval('order_id')"

inserts the new order
Line 552: orderQuery+rs.getInt(1)+", '"+billingInfo+"', '"+shippingInfo+"', '"+LocalDate.parse(currentDate.toString()).plusDays(7)+
                    "', '"+currentDate+"', 'In warehouse', '"+this.activeUser.getUserID()+"')"

inserts the new order contents
orderContentsQuery+this.activeUser.getUserID()+"', '"+i+"', "+basket.get(i)+")"

used to get the number of books being stored so that it can be increased by the number of books being removed from the basket
"select amount_stored from books where isbn='"+Selection+"'"

updates the amount of books stored by adding the amount removed from the basket
"update books set amount_stored="+basket.get(Selection)+rs.getInt(1) + " where isbn='"+Selection+"'"

retrieves ISBNs to check  that new book doesn't already exsit
"select isbn from books"

inserts new book
"insert into books values('"+newBook.getISBN()+"','"+newBook.getAuthor()+
            "', '"+newBook.getName()+"', '"+newBook.getGenre()+"', '"+
            newBook.getPublisher()+"', "+newBook.getPages()+", "+newBook.getPrice()+
            ", "+newBook.getSalesPercent()+", "+newBook.getRating()+", "+newBook.getMin()+
            ", "+newBook.getBooksSold()+", "+newBook.getAmountStored()+", "+newBook.getPrevMonthSold()+")"

retrieves ISBNs to check that the book to be deleted exists
"select isbn from books"

deletes the selected book
"delete from books where isbn='"+selection+"'"

calls the given view
"select * from v_sales_vs_expenditures"

calls the given view
"select * from v_sale_per_genre"

calls the given view
"select * from v_sales_per_author"

*/
