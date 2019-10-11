package samuelmovi.familyLibraryJava.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long loan_index;
    private  long book;
    private  String borrower;
    private  String loan_date;
    private  String return_date;

    protected Loan(){}

    public Loan(long book, String borrower){
        this.book = book;
        this.borrower = borrower;
        this.loan_date = LocalDate.now().toString();
        // this.return_date = return_date;
    }

    @Override
    public String toString() {
        return String.format(
                "Loan[id=%d, book='%s', borrower='%s', return_date=%s]",
                loan_index, book, borrower, return_date);
    }

    public long getLoan_index() {
        return loan_index;
    }

    public long getBook() {
        return book;
    }

    public String getBorrower() {
        return borrower;
    }

    public String getLoan_date() {
        return loan_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setBook(long book) {
        this.book = book;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public void setLoan_date(String loan_date) {
        this.loan_date = loan_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }
}
