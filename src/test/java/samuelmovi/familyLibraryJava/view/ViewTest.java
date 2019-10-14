package samuelmovi.familyLibraryJava.view;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import samuelmovi.familyLibraryJava.controller.Controller;
import samuelmovi.familyLibraryJava.model.Book;
import samuelmovi.familyLibraryJava.model.Loan;
import samuelmovi.familyLibraryJava.model.Location;
import samuelmovi.familyLibraryJava.repo.BookRepository;
import samuelmovi.familyLibraryJava.repo.BookViewRepository;
import samuelmovi.familyLibraryJava.repo.LoanRepository;
import samuelmovi.familyLibraryJava.repo.LocationRepository;

import javax.swing.*;

@ContextConfiguration(locations = "classpath:Tests.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ViewTest {

    @Autowired
    private BookRepository books;
    @Autowired
    private BookViewRepository bookViews;
    @Autowired
    private LocationRepository locations;
    @Autowired
    private LoanRepository loans;

    private View view;
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


    // private static boolean firstRun = true;

    @Before
    public void before(){
        this.view = new View();
    }

    @After
    public void after(){

    }

    @Test
    public void testSetUpFrame(){
        // check frame and content pane are null
        Assert.assertNull(view.getFrame());
        Assert.assertNull(view.getContentPane());

        // execute method
        view.setUpFrame();

        // assert expected outcome
        Assert.assertNotNull(view.getFrame());
        Assert.assertTrue(view.getFrame().isVisible());

        Assert.assertNotNull(view.getContentPane());
    }

    //@Test
    public void testFinalSetup(){

    }

    @Test
    public void testCreatePanels(){
        // check panels are null
        Assert.assertNull(view.getBooksPanel());
        Assert.assertNull(view.getLocationsPanel());
        Assert.assertNull(view.getLoansPanel());
        Assert.assertNull(view.getHomePanel());

        // check top buttons are null
        Assert.assertNull(view.getBooksB());
        Assert.assertNull(view.getLocationsB());
        Assert.assertNull(view.getLoansB());

        // execute method
        JPanel contentPane = new JPanel();
        view.setContentPane(contentPane);
        view.createPanels();

        // assert expected outcome
        Assert.assertNotNull(view.getBooksPanel());
        Assert.assertNotNull(view.getLocationsPanel());
        Assert.assertNotNull(view.getLoansPanel());
        Assert.assertNotNull(view.getHomePanel());

        Assert.assertFalse(view.getBooksPanel().isVisible());
        Assert.assertFalse(view.getLocationsPanel().isVisible());
        Assert.assertFalse(view.getLoansPanel().isVisible());
        Assert.assertTrue(view.getHomePanel().isVisible());

        Assert.assertNotNull(view.getBooksB());
        Assert.assertNotNull(view.getLocationsB());
        Assert.assertNotNull(view.getLoansB());


    }

    @Test
    public void testCreateMenuBar(){
        // check null: menu bar, main menu, help menu
        Assert.assertNull(view.getMenuBar());
        Assert.assertNull(view.getMainMenu());
        Assert.assertNull(view.getHelpM());

        // set frame, execute method
        JFrame frame = new JFrame();
        view.setFrame(frame);
        view.createMenuBar();

        // assert expected outcome
        Assert.assertNotNull(view.getMenuBar());
        Assert.assertNotNull(view.getMainMenu());
        Assert.assertNotNull(view.getHelpM());

        Assert.assertEquals(2, view.getMenuBar().getMenuCount());
        Assert.assertEquals(2, view.getMainMenu().getItemCount());
        Assert.assertEquals(1, view.getHelpM().getItemCount());

    }

    @Test
    public void testFillHomePanel(){
        // check null: home label, book total label, pending loans label
        Assert.assertNull(view.getWelcomeLabel());
        Assert.assertNull(view.getBooksTotalLabel());
        Assert.assertNull(view.getPendingLoansLabel());

        // set home panel, all books and all loans
        JPanel panel = new JPanel();
        view.setHomePanel(panel);
        view.setAll_books(books.findAll());
        view.setAll_loans(loans.findAll());
        // execute method
        view.fillHomePanel();
        // assert expected outcome
        Assert.assertNotNull(view.getWelcomeLabel());
        Assert.assertNotNull(view.getBooksTotalLabel());
        Assert.assertNotNull(view.getPendingLoansLabel());

        Assert.assertEquals(3, view.getHomePanel().getComponentCount());
    }

    // @Test
    public void testSetColumnWidths(){

    }

    @Test
    public void testFillBookModel(){
        // populate database
        this.loadLocationData();
        this.loadBookData();
        // set bookFieldAlias
        this.controller = new Controller();
        controller.loadTextStrings();
        view.setBookFieldAlias(controller.getStringMap().get("bookFieldAlias").split("/"));
        // execute method
        view.fillBookModel(view.getModifyBooksModel(), books.findAll());
        // assert expected outcome
        Assert.assertEquals(books.count(), view.getModifyBooksModel().getRowCount());
    }

    @Test
    public void testFillBookViewModel(){
        // set up db
        this.loadLocationData();
        this.loadBookData();
        // set bookViewAlias
        this.controller = new Controller();
        controller.loadTextStrings();
        view.setBookViewAliases(controller.getStringMap().get("bookViewAliases").split("/"));
        // execute method
        view.fillBookViewModel(view.getAllBookViewsModel(), bookViews.findAll());
        // assert expected outcome
        Assert.assertEquals(bookViews.count(), view.getAllBookViewsModel().getRowCount());
    }

    // @Test TODO: solve integration issues from internal method calls
    public void testFillBooksPanel(){
        // check null: books tabbed pane
        Assert.assertNull(view.getBooksTabbedPane());
        // set books panel
        JPanel panel = new JPanel();
        view.setBooksPanel(panel);
        // set strings
        this.controller = new Controller();
        controller.loadTextStrings();
        view.setStringMap(controller.getStringMap());
        // execute method
        view.fillBooksPanel();
        // assert expected outcome
        Assert.assertNotNull(view.getBooksTabbedPane());
    }

    @Test
    public void testCreateSearchBookTab(){
        // check null: search books panel, search books tab table, search book combo, search term field, search book button
        Assert.assertNull(view.getSearchBooksPanel());
        Assert.assertNull(view.getSearchBooksTabTable());
        Assert.assertNull(view.getSearchBookCombo());
        Assert.assertNull(view.getSearchTerm());
        Assert.assertNull(view.getSearchBookB());

        // set required object state
        this.controller = new Controller();
        controller.loadTextStrings();
        view.setStringMap(controller.getStringMap());
        JTabbedPane tabbedPane = new JTabbedPane();
        view.setBooksTabbedPane(tabbedPane);
        view.setSearchFields(controller.getStringMap().get("searchBookFields").split("/"));

        // execute method
        view.createSearchBookTab();
        // assert expected outcome
        Assert.assertNotNull(view.getSearchBooksPanel());
        Assert.assertNotNull(view.getSearchBooksTabTable());
        Assert.assertNotNull(view.getSearchBookCombo());
        Assert.assertNotNull(view.getSearchTerm());
        Assert.assertNotNull(view.getSearchBookB());

    }

    // @Test controller.createLocationList throws unexpected error
    public void testCreateAddBookTab(){
        // check null:
        Assert.assertNull(view.getAddBookTab());
        Assert.assertNull(view.getComboLocations());
        Assert.assertNull(view.getAddBookB());
        Assert.assertNull(view.getClearAddBookFieldsB());

        // set db
        this.loadLocationData();

        // set object state
        this.controller = new Controller();
        controller.loadTextStrings();
        view.setStringMap(controller.getStringMap());

        view.setLocationsArray(controller.createLocationList());
        controller.setLocations(locations);
        JTabbedPane tabbedPane = new JTabbedPane();
        view.setBooksTabbedPane(tabbedPane);
        view.setBookLabelsText(controller.getStringMap().get("bookLabelsText").split(","));
        // execute method
        view.createAddBookTab();
        // assert expected outcome
        Assert.assertNotNull(view.getAddBookTab());
        Assert.assertNotNull(view.getComboLocations());
        Assert.assertNotNull(view.getAddBookB());
        Assert.assertNotNull(view.getClearAddBookFieldsB());
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
                    locations.findByAddress(locationData[0][0]).get(0).getIndex()
            );
            books.save(newBook);
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
            locations.save(newLoc);
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
