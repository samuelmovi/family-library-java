package samuelmovi.familyLibraryJava.controller;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import samuelmovi.familyLibraryJava.model.Book;
import samuelmovi.familyLibraryJava.model.Loan;
import samuelmovi.familyLibraryJava.model.Location;
import samuelmovi.familyLibraryJava.repo.BookRepository;
import samuelmovi.familyLibraryJava.repo.LoanRepository;
import samuelmovi.familyLibraryJava.repo.LocationRepository;
import samuelmovi.familyLibraryJava.view.View;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

@ContextConfiguration(locations = "classpath:Tests.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ControllerTest {

    @Autowired
    private BookRepository books;
    @Autowired
    private LocationRepository locations;
    @Autowired
    private LoanRepository loans;
    private static View view;
    @Autowired
    private Controller controller;

    private static String[][] bookData = {
            {"title1", "author1", "genre1", "publisher1", "isbn1", "publish_date1", "purchase_date1"},
            {"title2", "author2", "genre2", "publisher2", "isbn2", "publish_date2", "purchase_date2"},
            {"title3", "author3", "genre3", "publisher3", "isbn3", "publish_date3", "purchase_date3"}
    };

    private static String[][] locationData = {
            {"address1", "room1", "furniture1", "details1"},
            {"address2", "room2", "furniture2", "details2"},
            {"address3", "room3", "furniture3", "details3"}
    };


    private static boolean firstRun = true;

    @Before
    public void before(){
        controller.getBooks().deleteAll();
        controller.getLocations().deleteAll();
        controller.getLoans().deleteAll();

        // POPULATE DATABASE
        loadLocationData();
        loadBookData();

        if (firstRun){
            controller.initController();
            view = controller.getView();
            books = controller.getBooks();
            locations = controller.getLocations();
            loans = controller.getLoans();

            firstRun = false;
        }
    }

    @After
    public void after(){

        /*controller.getBooks().deleteAll();
        controller.getLocations().deleteAll();
        controller.getLoans().deleteAll();*/

    }

    // BOOK TESTS
    @Test
    public void testShowBooksB(){
        // execute showBooksB()
        controller.showBooksB();
        // assert only visible panel is BooksPanel
        Assert.assertTrue(view.getBooksPanel().isVisible());
        Assert.assertFalse(view.getLoansPanel().isVisible());
        Assert.assertFalse(view.getLocationsPanel().isVisible());
        Assert.assertFalse(view.getHomePanel().isVisible());

        // assert buttons' background colors are properly set
        Assert.assertEquals(view.booksColor, view.getBooksB().getBackground());
        Assert.assertEquals(Color.WHITE, view.getLocationsB().getBackground());
        Assert.assertEquals(Color.WHITE, view.getLoansB().getBackground());
    }

    @Test
    public void refreshAllBooksTabTest(){
        int before = view.getAllBooksTabTable().getColumnCount();
        // execute method
        controller.refreshAllBooksTab();
        // assert table contents are what expected
        Assert.assertEquals(before, view.getAllBooksTabTable().getColumnCount());
    }

    @Test
    public void testSearchBookB(){
        // set search field to title in combo
        view.getSearchBookCombo().setSelectedIndex(0);
        // set field value in text field
        view.getSearchTerm().setText(bookData[0][0]);
        // execute method
        controller.searchBooksB();
        // assert expected outcome
        Assert.assertEquals(1, view.getSearchBooksModel().getRowCount());
    }

    @Test
    public void testAddBookB(){
        long before = books.count();
        // set data sources for new book: view's addBookTextFields and comboLocations
        for (int i=0; i< bookData[0].length; i++){
            view.getAddBookTextFields()[i].setText(bookData[0][i]);
        }
        view.getComboLocations().setSelectedIndex(0);
        // execute method
        controller.addBookB();
        // assert expected outcome
        Assert.assertEquals(before+1, books.count());
    }

    @Test
    public void testClearAddBookFieldsB() {
        // populate add book fields
        for (int i=0; i< bookData[0].length; i++){
            view.getAddBookTextFields()[i].setText(bookData[0][i]);
        }
        view.getComboLocations().setSelectedIndex(0);
        // execute method
        controller.clearAddBookFieldsB();
        // assert expected outcome
        for(JTextField field: view.getAddBookTextFields()){
            Assert.assertEquals(field.getText(), "");
        }
        Assert.assertNull(view.getComboLocations().getSelectedItem());
    }

    @Test
    public void testModifyBookB() {
        Book testBook = books.findByTitle(bookData[0][0]).get(0);
        // set selected book index
        controller.setBookIndex(String.valueOf(testBook.getIndex()));
        // set values for ModifyBookInputObjects
        String testString = "vsdFvgFDS";
        for(int i=0;i<view.getModifyBookInputObjects().length;i++) {
            if(view.getModifyBookInputObjects()[i] instanceof JTextField ) {
                JTextField textField=(JTextField) view.getModifyBookInputObjects()[i];
                textField.setText(testString);
            }
            else if(view.getModifyBookInputObjects()[i] instanceof JComboBox) {
                JComboBox locationCombo = (JComboBox) view.getModifyBookInputObjects()[i];
                // set location to first choice in combo
                locationCombo.setSelectedIndex(0);
            }
        }
        // execute method
        controller.modifyBookB(view.getModifyBookInputObjects());
        // assert expected outcome
        Optional<Book> optional = books.findById(testBook.getIndex());
        if (optional.isPresent()){
            Book book = optional.get();
            Assert.assertEquals(testString, book.getTitle());
            Assert.assertEquals(LocalDate.now().toString(), book.getModificationDate());
        }
    }

    @Test
    public void testResetModifyBookTab() {
        // set content of modify fields
        JTextField textField;
        String testString = "wqerWQErQEW";
        for(int i=0;i<view.getModifyBookInputObjects().length;i++) {
            if (view.getModifyBookInputObjects()[i] instanceof JTextField) {
                textField=(JTextField) view.getModifyBookInputObjects()[i];
                textField.setText(testString);
            }
            else if(view.getModifyBookInputObjects()[i] instanceof JComboBox) {
                view.getModifyBookCB().setSelectedItem(null);
            }
        }
        // execute method
        controller.resetModifyBookTab();
        // assert expected outcome
        for(int i=0;i<view.getModifyBookInputObjects().length;i++) {
            if (view.getModifyBookInputObjects()[i] instanceof JTextField) {
                textField=(JTextField) view.getModifyBookInputObjects()[i];
                Assert.assertEquals("", textField.getText());
            }
            else if(view.getModifyBookInputObjects()[i] instanceof JComboBox) {
                Assert.assertNull(view.getModifyBookCB().getSelectedItem());
            }
        }
    }

    @Test
    public void testFillModifyBookFields() {
        // set content of table model
        Book b = books.findByTitle(bookData[0][0]).get(0);
        Vector<String> vector = new Vector<String>();
        vector.add(String.valueOf(b.getIndex()));
        vector.add(b.getTitle());
        vector.add(b.getAuthor());
        vector.add(b.getGenre());
        vector.add(b.getPublisher());
        vector.add(b.getIsbn());
        vector.add(b.getPublishDate());
        vector.add(b.getPurchase_date());

        view.getModifyBooksModel().setRowCount(0);
        view.getModifyBooksModel().setColumnCount(controller.getStringMap().get("bookFieldAlias").split("/").length);
        view.getModifyBooksModel().addRow(vector);
        // set location combo content
        String[] locationString = new String[1];
        locationString[0] = b.getIndex() + "/ qwerqwe ";
        view.getModifyBookCB().setModel(new DefaultComboBoxModel<String>(locationString));
        // set selected table row
        view.getModifyBookTabTable().setRowSelectionInterval(0,0);
        // execute method
        controller.fillModifyBookFields();
        // assert expected outcome
        for(int i=0;i<view.getModifyBookInputObjects().length;i++) {
            if(view.getModifyBookInputObjects()[i] instanceof JTextField ) {
                JTextField textField=(JTextField) view.getModifyBookInputObjects()[i];
                String value = textField.getText();
                System.out.println("["+i+"] "+value+" - "+bookData[0][i]);
                Assert.assertEquals(bookData[0][i], value);
            }
            else if(view.getModifyBookInputObjects()[i] instanceof JComboBox) {
                JComboBox locationCombo = (JComboBox) view.getModifyBookInputObjects()[i];
                String selected = (String) locationCombo.getSelectedItem();
                System.out.println("[#] Combo selection: "+ selected);
                Assert.assertEquals(String.valueOf(b.getIndex()), selected.split("/")[0]);
            }
        }
    }

    // @Test TODO: figure out how to click the dialog's ok button
    public void testDeleteBookB() {
        // set selected book index

        // execute method

        // click ok

        // assert expected outcome
    }

    @Test
    public void testBookToDelete(){
        // reset bookIndex and bookTitle
        controller.setBookIndex("");
        controller.setBookTitle("");
        // set content of delete book table model
        Book b = books.findByTitle(bookData[0][0]).get(0);
        Vector<String> vector = new Vector<String>();
        vector.add(String.valueOf(b.getIndex()));
        vector.add(b.getTitle());
        vector.add(b.getAuthor());
        vector.add(b.getGenre());
        vector.add(b.getPublisher());
        vector.add(b.getIsbn());
        vector.add(b.getPublishDate());
        vector.add(b.getPurchase_date());

        view.getAllBooksModel().setRowCount(0);
        view.getAllBooksModel().setColumnCount(controller.getStringMap().get("bookFieldAlias").split("/").length);
        view.getAllBooksModel().addRow(vector);
        // set selected table row
        view.getDeleteBookTabTable().setRowSelectionInterval(0,0);
        // execute method
        controller.bookToDelete();
        // assert expected outcome
        Assert.assertEquals(String.valueOf(b.getIndex()), controller.getBookIndex());
        Assert.assertEquals(b.getTitle(), controller.getBookTitle());
    }

    // LOCATION TESTS
    @Test
    public void testShowLocationsB(){
        // execute showLocationsB()
        controller.showLocationsB();
        // assert only visible panel is BooksPanel
        Assert.assertTrue(view.getLocationsPanel().isVisible());
        Assert.assertFalse(view.getLoansPanel().isVisible());
        Assert.assertFalse(view.getBooksPanel().isVisible());
        Assert.assertFalse(view.getHomePanel().isVisible());

        // assert buttons' background colors are properly set
        Assert.assertEquals(view.locationsColor, view.getLocationsB().getBackground());
        Assert.assertEquals(Color.WHITE, view.getBooksB().getBackground());
        Assert.assertEquals(Color.WHITE, view.getLoansB().getBackground());
    }

    @Test
    public void testRefreshAllLocationsB(){
        int before = view.getAllLocationsTabTable().getColumnCount();
        // execute method
        controller.refreshAllLocationsB();
        // assert table contents are what expected
        Assert.assertEquals(before, view.getAllLocationsTabTable().getColumnCount());
    }

    @Test
    public void testAddLocationB(){
        long before = locations.count();
        // set data in view.getAddLocationTextFields
        for (int i=0; i< locationData[0].length; i++){
            view.getAddLocationTextFields()[i].setText(locationData[0][i]);
        }
        // execute method
        controller.addLocationB();
        // assert expected outcome
        Assert.assertEquals(before+1, locations.count());
    }

    @Test
    public void testClearAddLocationFieldsB(){
        // populate add location input fields
        for (int i=0; i< locationData[0].length; i++){
            view.getAddLocationTextFields()[i].setText(locationData[0][i]);
        }
        // execute method
        controller.clearAddLocationFieldsB();
        // assert expected outcome
        for(JTextField field: view.getAddLocationTextFields()){
            Assert.assertEquals(field.getText(), "");
        }
    }

    @Test
    public void testModifyLocationB(){
        String testString = "thTRGtrTR";
        // get location and populate modify fields and location index
        Location location = locations.findByAddress(locationData[0][0]).get(0);
        view.getModifyLocationTextFields()[0].setText(location.getAddress());
        view.getModifyLocationTextFields()[1].setText(location.getRoom());
        view.getModifyLocationTextFields()[2].setText(location.getFurniture());
        view.getModifyLocationTextFields()[3].setText(testString);
        controller.setLocationIndex(String.valueOf(location.getIndex()));
        // execute method
        controller.modifyLocationB(view.getModifyLocationTextFields());
        // assert expected outcome
        Optional<Location> optional = locations.findById(location.getIndex());
        if (optional.isPresent()){
            Location testLocation = optional.get();
            Assert.assertEquals(testString, testLocation.getDetails());
            Assert.assertNotNull(testLocation.getModificationDate());
        }
    }

    @Test
    public void testFillModifyLocationFields(){
        String testString = "5t4Gtrgy45y";
        // set content of modify location table model
        Location testLocation = locations.findByAddress(locationData[0][0]).get(0);
        Vector<String> vector = new Vector<String>();
        vector.add(String.valueOf(testLocation.getIndex()));
        vector.add(testString);
        vector.add(testString);
        vector.add(testString);
        vector.add(testString);

        view.getAllLocationsModel().setRowCount(0);
        view.getAllLocationsModel().setColumnCount(controller.getStringMap().get("locationFieldAlias").split("/").length);
        view.getAllLocationsModel().addRow(vector);
        // set selected table row
        view.getModifyLocationTabTable().setRowSelectionInterval(0,0);
        // execute method
        controller.fillModifyLocationFields();
        // assert expected outcome
        for (int i=0; i<view.getModifyLocationTextFields().length; i++){
            Assert.assertEquals(testString, view.getModifyLocationTextFields()[i].getText());
        }
    }

    @Test
    public void testClearModifyLocationFields(){
        // set content of modify fields
        String testString = "fdDSFGre e evgf";
        for(JTextField field: view.getModifyLocationTextFields()){
            field.setText(testString);
        }
        // execute method
        controller.clearModifyLocationFields();
        // assert expected outcome
        for(JTextField field: view.getModifyLocationTextFields()){
            Assert.assertEquals("", field.getText());
        }
    }

    @Test
    public void testLocationToDelete(){
        // reset location index value
        controller.setLocationIndex("");
        // set content of all locations model
        Location testLocation = locations.findByAddress(locationData[0][0]).get(0);
        Vector<String> vector = new Vector<String>();
        vector.add(String.valueOf(testLocation.getIndex()));
        vector.add(testLocation.getAddress());
        vector.add(testLocation.getRoom());
        vector.add(testLocation.getFurniture());
        vector.add(testLocation.getDetails());

        view.getAllLocationsModel().setRowCount(0);
        view.getAllLocationsModel().setColumnCount(controller.getStringMap().get("locationFieldAlias").split("/").length);
        view.getAllLocationsModel().addRow(vector);
        // set selected table row
        view.getDeleteLocationTabTable().setRowSelectionInterval(0,0);
        // execute method
        controller.locationToDelete();
        // assert expected outcome
        Assert.assertNotEquals("", controller.getLocationIndex());
    }

    // @Test TODO: figure out how to click the dialog's ok button
    public void testDeleteLocationB(){

    }

    //@Test
    public void testCreateLocationList(){
        // this method seems to be throwing a weird error, but not crashing
        // db setup in @before
        // execute method
        String[] testResults = controller.createLocationList();
        // assert expected outcome
        Assert.assertEquals(locationData.length, testResults.length);
        List<Location> allLocations = locations.findAll();
        for(int i=0; i<testResults.length; i++){
            // build expected result
            String testString = "";
            Location loc = allLocations.get(i);
            testString += loc.getIndex() + " / ";
            testString += loc.getAddress() + " / ";
            testString += loc.getRoom() + " / ";
            testString += loc.getFurniture() + " / ";
            testString += loc.getDetails();

            Assert.assertEquals(testString, testResults[i]);
        }
    }

    // LOAN TESTS
    @Test
    public void testShowLoansB(){
        // execute showLoansB()
        controller.showLoansB();
        // assert only visible panel is BooksPanel
        Assert.assertTrue(view.getLoansPanel().isVisible());
        Assert.assertFalse(view.getBooksPanel().isVisible());
        Assert.assertFalse(view.getLocationsPanel().isVisible());
        Assert.assertFalse(view.getHomePanel().isVisible());

        // assert buttons' background colors are properly set
        Assert.assertEquals(view.loansColor, view.getLoansB().getBackground());
        Assert.assertEquals(Color.WHITE, view.getLocationsB().getBackground());
        Assert.assertEquals(Color.WHITE, view.getBooksB().getBackground());

    }

    // @Test TODO: figure out how to click the dialog's ok button
    public void testLoanBookB(){

    }

    @Test
    public void testLoanThisBook(){
        // populate input fields for borrower and book index
        Book book = books.findAll().get(0);
        if (view.getLoanBookTextFields().length == 2){
            view.getLoanBookTextFields()[0].setText(String.valueOf(book.getIndex()));
            view.getLoanBookTextFields()[1].setText("borrower1");
        }
        // execute method
        boolean result = controller.loanThisBook(view.getLoanBookTextFields());
        // assert expected outcome
        Optional <Book> optionalBook = books.findById(book.getIndex());
        Assert.assertTrue(optionalBook.isPresent());
        Book testBook = optionalBook.get();
        Assert.assertTrue(result);
        Assert.assertTrue(testBook.isLoaned());
        Optional<Loan> optionalLoan = loans.findByBorrower("borrower1");
        Assert.assertTrue(optionalLoan.isPresent());
        Loan testLoan = optionalLoan.get();
        Assert.assertEquals("borrower1", testLoan.getBorrower());
        Assert.assertEquals(testBook.getIndex(), testLoan.getBook());
        Assert.assertEquals(LocalDate.now().toString(), testLoan.getLoan_date());
    }

    @Test
    public void testBookToLoan(){
        // clear book title and first loan book input field
        controller.setBookTitle("");
        view.getLoanBookTextFields()[0].setText("");
        // populate table model and select row
        Book b = books.findByTitle(bookData[0][0]).get(0);
        Vector<String> vector = new Vector<String>();
        vector.add(String.valueOf(b.getIndex()));
        vector.add(b.getTitle());
        vector.add(b.getAuthor());
        vector.add(b.getGenre());
        vector.add(b.getPublisher());
        vector.add(b.getIsbn());
        vector.add(b.getPublishDate());
        vector.add(b.getPurchase_date());

        view.getAllBooksModel().setRowCount(0);
        view.getAllBooksModel().setColumnCount(controller.getStringMap().get("bookFieldAlias").split("/").length);
        view.getAllBooksModel().addRow(vector);
        // set selected table row
        view.getMakeLoanTabTable().setRowSelectionInterval(0,0);
        // execute method
        controller.bookToLoan();
        // assert expected results
        Assert.assertEquals(String.valueOf(b.getIndex()), view.getLoanBookTextFields()[0].getText());
        Assert.assertEquals(b.getTitle(), controller.getBookTitle());
    }

    @Test
    public void testRefreshLoansTableB(){
        long before = loans.count();
        // set number of instances of loans
        Loan loan1 = new Loan(books.findAll().get(0).getIndex(), "borrower1");
        Loan loan2 = new Loan(books.findAll().get(1).getIndex(), "borrower2");
        loans.save(loan1);
        loans.save(loan2);
        // execute method
        controller.refreshLoansTableB();
        // assert expected outcome
        Assert.assertEquals(before+2, loans.count());
        Assert.assertEquals(before+2, view.getAllLoansTabTable().getRowCount());
    }

    @Test
    public void testLoanToReturn(){
        // clear loan index
        controller.setLoanIndex("");
        // populate loan table model
        Loan loan1 = new Loan(books.findAll().get(0).getIndex(), "borrower1");
        loans.save(loan1);
        Vector<String> vector = new Vector<String>();
        vector.add(String.valueOf(loan1.getLoan_index()));
        vector.add(String.valueOf(loan1.getBook()));
        vector.add(loan1.getBorrower());
        vector.add(loan1.getLoan_date());
        vector.add(loan1.getReturn_date());
        view.getLoansModel().setRowCount(0);
        view.getLoansModel().setColumnCount(controller.getStringMap().get("loanFieldALias").split("/").length);
        view.getLoansModel().addRow(vector);
        // set selected row
        view.getReturnLoanTabTable().setRowSelectionInterval(0,0);
        // execute method
        controller.loanToReturn();
        // assert expected outcome
        Assert.assertEquals(String.valueOf(loan1.getLoan_index()), controller.getLoanIndex());

    }

    // @Test TODO: figure out how to click the dialog's ok button
    public void testReturnBookB(){
        Loan loan1 = new Loan(books.findAll().get(0).getIndex(), "borrower1");

        // assert expected outcome
        Optional<Loan> optionalLoan = loans.findById(loan1.getLoan_index());
        Assert.assertTrue(optionalLoan.isPresent());
        Loan returnedLoan = optionalLoan.get();
        Assert.assertEquals(LocalDate.now().toString(), returnedLoan.getReturn_date());
        Optional<Book> optionalBook = books.findById(loan1.getBook());
        Assert.assertTrue(optionalBook.isPresent());
        Book returnedBook = optionalBook.get();
        Assert.assertFalse(returnedBook.isLoaned());
    }


    // UTILITIES
    public void loadBookData(){
        for (String[] data: bookData){
            Book newBook = new Book(
                    data[0],
                    data[1],
                    data[2],
                    data[3],
                    data[4],
                    data[5],
                    data[6],
                    controller.getLocations().findByAddress(locationData[0][0]).get(0).getIndex()
            );
            controller.getBooks().save(newBook);
        }
    }

    public void loadLocationData(){
        for (String[] data: locationData){
            Location newLoc = new Location(
                    data[0],
                    data[1],
                    data[2],
                    data[3]
            );
            controller.getLocations().save(newLoc);
        }
    }

    public void setBookAsLoaned(long id, String borrower){
        Loan newLoan = new Loan(
                id,
                borrower
        );
        loans.save(newLoan);
    }

}
