package samuelmovi.familyLibraryJava.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    long book_index;
    String title;
    String author;
    String genre;
    String publisher;
    String isbn;
    String publish_date;
    String purchase_date;
    long location;
    boolean loaned;
    String registration_date;
    String modification_date;


    protected Book(){}

    public Book(String title, String author, String genre, String publisher, String isbn, String publish_date, String purchase_date, long location){
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.isbn = isbn;
        this.publish_date = publish_date;
        this.purchase_date = purchase_date;
        this.location = location;
        this.loaned = false;
        this.registration_date = LocalDate.now().toString();
    }

    @Override
    public String toString() {
        return String.format(
                "Book[id=%d, title='%s', author='%s', publish_date=%s]",
                book_index, title, author, publish_date);
    }


    public long getBook_index() {
        return book_index;
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

    public String getPublish_date() {
        return publish_date;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public long getLocation() {
        return location;
    }

    public boolean isLoaned() {
        return loaned;
    }

    public String getRegistration_date() {
        return registration_date;
    }

    public String getModification_date() {
        return modification_date;
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

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
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

    public void setRegistration_date(String registration_date) {
        this.registration_date = registration_date;
    }

    public void setModification_date(String modification_date) {
        this.modification_date = modification_date;
    }
}
