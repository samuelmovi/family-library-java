package samuelmovi.familyLibraryJava.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long index;
    private String title;
    private String author;
    private String genre;
    private String publisher;
    private String isbn;
    private String publishDate;
    private  String purchase_date;
    private  long location;
    private  boolean loaned;
    private  String registrationDate;
    private  String modificationDate;


    protected Book(){}

    public Book(String title, String author, String genre, String publisher, String isbn, String publish_date, String purchase_date){
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.isbn = isbn;
        this.publishDate = publish_date;
        this.purchase_date = purchase_date;
        // this.location = location;
        this.loaned = false;
        this.registrationDate = LocalDate.now().toString();
    }

    public Book(String title, String author, String genre, String publisher, String isbn, String publish_date, String purchase_date, long location){
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.isbn = isbn;
        this.publishDate = publish_date;
        this.purchase_date = purchase_date;
        this.location = location;
        this.loaned = false;
        this.registrationDate = LocalDate.now().toString();
    }

    @Override
    public String toString() {
        return String.format(
                "Book[id=%d, title='%s', author='%s', publishDate=%s]",
                index, title, author, publishDate);
    }

    @Column(name="index")
    public long getIndex() {
        return index;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    @Column(name="publish_date")
    public String getPublishDate() {
        return publishDate;
    }

    @Column(name="purchase_date")
    public String getPurchase_date() {
        return purchase_date;
    }

    public long getLocation() {
        return location;
    }

    public boolean isLoaned() {
        return loaned;
    }

    @Column(name="registrationDate")
    public String getRegistrationDate() {
        return registrationDate;
    }

    @Column(name="modificationDate")
    public String getModificationDate() {
        return modificationDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public void setLocation(long location) {
        this.location = location;
    }

    public void setLoaned(boolean loaned) {
        this.loaned = loaned;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }
}
