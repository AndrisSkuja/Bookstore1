public class Book {

    private String ISBN;
    private String author;
    private String name;
    private String genre;
    private String publisher;
    private int pages;
    private int price;
    private int salesPercent;
    private int rating;
    private int min;
    private int booksSold;
   
   
   
    public Book(String iSBN, String author, String name, String genre, String publisher, int pages, int price,
            int salesPercent, int rating, int min, int booksSold) {
        ISBN = iSBN;
        this.author = author;
        this.name = name;
        this.genre = genre;
        this.publisher = publisher;
        this.pages = pages;
        this.price = price;
        this.salesPercent = salesPercent;
        this.rating = rating;
        this.min = min;
        this.booksSold = booksSold;
    }
    
    public String getISBN() {
        return ISBN;
    }
    public void setISBN(String iSBN) {
        ISBN = iSBN;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public int getPages() {
        return pages;
    }
    public void setPages(int pages) {
        this.pages = pages;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getSalesPercent() {
        return salesPercent;
    }
    public void setSalesPercent(int salesPercent) {
        this.salesPercent = salesPercent;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public int getMin() {
        return min;
    }
    public void setMin(int min) {
        this.min = min;
    }
    public int getBooksSold() {
        return booksSold;
    }
    public void setBooksSold(int booksSold) {
        this.booksSold = booksSold;
    }

    
    
}
