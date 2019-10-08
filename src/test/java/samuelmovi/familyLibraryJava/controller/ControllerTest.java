package samuelmovi.familyLibraryJava.controller;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

@ContextConfiguration(locations = "classpath:Tests.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ControllerTest {

    @Autowired
    private BookRepository books;
    @Autowired
    private LocationRepository locations;
    @Autowired
    private LoanRepository loans;

    @Mock
    private View mockView;
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
        // POPULATE DATABASE
        loadLocationData();
        loadBookData();

        if (firstRun){
            controller.initController();
            view = controller.getView();
            firstRun = false;
        }
    }

    @After
    public void after(){

        controller.getBooks().deleteAll();
        controller.getLocations().deleteAll();
        controller.getLoans().deleteAll();

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
        // TODO: check if modifyBook is changing the book's index
        // populate view.getModifyBookInputObjects()

        // execute method

        // assert expected outcome

    }

    // @Test
    public void testResetModifyBookTab() {

    }

    // @Test
    public void testFillModifyBookFields() {

    }

    // @Test
    public void testDeleteBookB() {

    }

    // @Test
    public void testDeleteBookTabTable(){

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

    // @Test
    public void testClearAddLocationFieldsB(){

    }

    // @Test
    public void testModifyLocationB(){

    }

    // @Test
    public void testFillModifyLocationFields(){

    }

    // @Test
    public void testClearModifyLocationFields(){

    }

    // @Test
    public void testSetLocationToDelete(){

    }

    // @Test
    public void testDeleteLocationB(){

    }

    // @Test
    public void testCreateLocationList(){

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

    // @Test
    public void testLoanBookB(){

    }

    // @Test
    public void testLoanThisBook(){

    }

    // @Test
    public void testMakeLoanTabTable(){

    }

    // @Test
    public void testRefreshLoansTableB(){

    }

    // @Test
    public void testReturnLoanTabTable(){

    }

    // @Test
    public void testReturnBookB(){

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
                    controller.getLocations().findByAddress(locationData[0][0]).getLocation_index()
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
        controller.getLoans().save(newLoan);
    }

}
