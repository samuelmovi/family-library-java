package samuelmovi.familyLibraryJava.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BookView {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;
    String title;
    String author;
    String genre;
    String publisher;
    String isbn;
    String publish_date;
    String purchase_date;
    String location;
    boolean loaned;
    String registration_date;
    String modification_date;

    protected BookView(){}

    public BookView(String title, String author, String genre, String publisher, String isbn, String publish_date, String purchase_date, String location, boolean loaned, String registration_date, String modification_date){

        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publisher = publisher;
        this.isbn = isbn;
        this.publish_date = publish_date;
        this.purchase_date = purchase_date;
        this.location = location;
        this.loaned = loaned;
        this.registration_date = registration_date;
        this.modification_date = modification_date;


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

    public String getLocation() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
