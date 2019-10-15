package samuelmovi.familyLibraryJava.view;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import javax.swing.table.DefaultTableModel;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;

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


    // BOOK TESTS

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
        view.setStringMap(controller.getStringMap());
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
        view.setStringMap(controller.getStringMap());
        // execute method
        view.fillBookViewModel(view.getAllBookViewsModel(), bookViews.findAll());
        // assert expected outcome
        Assert.assertEquals(bookViews.count(), view.getAllBookViewsModel().getRowCount());
    }

    // @Test // TODO: solve mocking of inner method calls
    public void testFillBooksPanel(){
        // check null: books tabbed pane
        Assert.assertNull(view.getBooksTabbedPane());

        // create spy to mock inner method calls
        View spyView = Mockito.spy(new View());

        // set strings
        this.controller = new Controller();
        controller.loadTextStrings();
        spyView.setStringMap(controller.getStringMap());

        // set books panel
        JPanel panel = new JPanel();
        spyView.setBooksPanel(panel);

        Mockito.doNothing().when(spyView).createAllBooksTab();
        Mockito.doNothing().when(spyView).createSearchBookTab();
        Mockito.doNothing().when(spyView).createAddBookTab();
        Mockito.doNothing().when(spyView).createModifyBookTab();
        Mockito.doNothing().when(spyView).createDeleteBookTab();

        Mockito.doNothing().when(spyView)
                .setColumnWidths(Mockito.any(JTable.class), Mockito.any(Integer[].class));
        Mockito.doNothing().when(spyView)
                .fillBookModel(Mockito.any(DefaultTableModel.class), Mockito.anyList());
        Mockito.doNothing().when(spyView)
                .fillBookViewModel(Mockito.any(DefaultTableModel.class), Mockito.anyList());

        // execute method
        spyView.fillBooksPanel();

        // assert expected outcome
        Assert.assertNotNull(spyView.getBooksTabbedPane());
        Mockito.verify(spyView, Mockito.times(1)).createAllBooksTab();
        Mockito.verify(spyView, Mockito.times(1)).createSearchBookTab();
        Mockito.verify(spyView, Mockito.times(1)).createAddBookTab();
        Mockito.verify(spyView, Mockito.times(1)).createModifyBookTab();
        Mockito.verify(spyView, Mockito.times(1)).createDeleteBookTab();


         Mockito.verify(spyView, Mockito.times(3)).setColumnWidths(Mockito.any(JTable.class), Mockito.any(Integer[].class));
        Mockito.verify(spyView, Mockito.times(1)).fillBookModel(Mockito.any(DefaultTableModel.class), Mockito.anyList());
    }

    // @Test
    public void testRefreshBookTables(){

    }

    @Test
    public void testCreateAllBooksTab() {
        // check null
        Assert.assertNull(view.getAllBooksTab());
        Assert.assertNull(view.getAllBooksTabTable());
        Assert.assertNull(view.getRefreshAllBooksTabB());

        // set object state
        view.setBooksTabbedPane(new JTabbedPane());

        // execute method
        view.createAllBooksTab();

        // assert expected result:
        Assert.assertNotNull(view.getAllBooksTab());
        Assert.assertNotNull(view.getAllBooksTabTable());
        Assert.assertNotNull(view.getRefreshAllBooksTabB());
        Assert.assertEquals(1, view.getBooksTabbedPane().getComponentCount());
    }

    @Test
    public void testCreateSearchBookTab() {
        // check null
        Assert.assertNull(view.getSearchBooksTab());
        Assert.assertNull(view.getSearchBooksTabTable());
        Assert.assertNull(view.getSearchTerm());
        Assert.assertNull(view.getSearchBookB());
        Assert.assertNull(view.getSearchBookCombo());

        // set object state
        view.setBooksTabbedPane(new JTabbedPane());
        this.controller = new Controller();
        controller.loadTextStrings();
        view.setStringMap(controller.getStringMap());

        // execute method
        view.createSearchBookTab();

        // assert expected result:
        Assert.assertNotNull(view.getSearchBooksTab());
        Assert.assertNotNull(view.getSearchBooksTabTable());
        Assert.assertNotNull(view.getSearchTerm());
        Assert.assertNotNull(view.getSearchBookB());
        Assert.assertNotNull(view.getSearchBookCombo());

        Assert.assertEquals(4, view.getSearchBooksTab().getComponentCount());
        Assert.assertEquals(1, view.getBooksTabbedPane().getComponentCount());
    }

    @Test
    public void testCreateAddBookTab() {
        // check null
        Assert.assertNull(view.getAddBookTab());
        Assert.assertNull(view.getComboLocations());
        Assert.assertNull(view.getAddBookB());
        Assert.assertNull(view.getClearAddBookFieldsB());

        // set object state
        view.setBooksTabbedPane(new JTabbedPane());
        this.controller = new Controller();
        controller.loadTextStrings();
        view.setStringMap(controller.getStringMap());
        view.setLocationsArray(new String[1]);

        // execute method
        view.createAddBookTab();

        // assert expected result:
        Assert.assertNotNull(view.getAddBookTab());
        Assert.assertNotNull(view.getComboLocations());
        Assert.assertNotNull(view.getAddBookB());
        Assert.assertNotNull(view.getClearAddBookFieldsB());

        Assert.assertEquals(
                3
                        + view.getAddBookTextFields().length
                        + view.getAddBookLabelObjects().length,
                view.getAddBookTab().getComponentCount()
        );
        Assert.assertEquals(1, view.getBooksTabbedPane().getComponentCount());
    }

    @Test
    public void testCreateModifyBookTab(){
        // check null
        Assert.assertNull(view.getModifyBookTab());
        Assert.assertNull(view.getModifyBookTabTable());
        Assert.assertNull(view.getModifyBookB());
        Assert.assertNull(view.getResetModifyBookTabB());

        // set object state
        view.setBooksTabbedPane(new JTabbedPane());
        this.controller = new Controller();
        controller.loadTextStrings();
        view.setStringMap(controller.getStringMap());
        view.setLocationsArray(new String[1]);

        // execute method
        view.createModifyBookTab();

        // assert expected outcome
        Assert.assertNotNull(view.getModifyBookTab());
        Assert.assertNotNull(view.getModifyBookTabTable());
        Assert.assertNotNull(view.getModifyBookB());
        Assert.assertNotNull(view.getResetModifyBookTabB());

        Assert.assertEquals(
                3
                        + view.getModifyBookInputObjects().length
                        + controller.getStringMap().get("bookLabelsText").split(",").length,
                view.getModifyBookTab().getComponentCount()
        );

        Assert.assertEquals(1, view.getBooksTabbedPane().getComponentCount());

    }

    @Test
    public void testCreateDeleteBookTab(){
        // check null
        Assert.assertNull(view.getDeleteBooksTab());
        Assert.assertNull(view.getDeleteBookTabTable());
        Assert.assertNull(view.getDeleteBookB());

        // set object state
        view.setBooksTabbedPane(new JTabbedPane());
        this.controller = new Controller();
        controller.loadTextStrings();
        view.setStringMap(controller.getStringMap());
        view.setLocationsArray(new String[1]);

        // execute method
        view.createDeleteBookTab();

        // assert expected outcome
        Assert.assertNotNull(view.getDeleteBooksTab());
        Assert.assertNotNull(view.getDeleteBookTabTable());
        Assert.assertNotNull(view.getDeleteBookB());

        Assert.assertEquals(1, view.getBooksTabbedPane().getComponentCount());
        Assert.assertEquals(2, view.getDeleteBooksTab().getComponentCount());

    }


    // LOCATION TESTS

    @Test
    public void testCreateAllLocationsTab(){
        // check null
        Assert.assertNull(view.getAllLocationsTab());
        Assert.assertNull(view.getAllLoansTabTable());
        Assert.assertNull(view.getRefreshAllLocationsB());

        // set object state
        view.setLocationsTabbedPane(new JTabbedPane());
        this.controller = new Controller();
        controller.loadTextStrings();
        view.setStringMap(controller.getStringMap());

        // execute method
        view.createAllLocationsTab();

        // assert expected outcome
        Assert.assertNotNull(view.getAllLocationsTab());
        Assert.assertNotNull(view.getAllLocationsTabTable());
        Assert.assertNotNull(view.getRefreshAllLocationsB());

        Assert.assertEquals(1, view.getLocationsTabbedPane().getComponentCount());
        Assert.assertEquals(2, view.getAllLocationsTab().getComponentCount());
    }

    @Test
    public void testFillLocationModel(){
        // populate database
        this.loadLocationData();
        this.loadBookData();
        // set object state
        this.controller = new Controller();
        controller.loadTextStrings();
        view.setStringMap(controller.getStringMap());
        // execute method
        view.fillLocationModel(view.getAllLocationsModel(), locations.findAll());
        // assert expected outcome
        Assert.assertEquals(locations.count(), view.getAllLocationsModel().getRowCount());
    }

    // @Test
    public void testFillLocationsPanel(){

    }

    // @Test
    public void testRefreshLocationTables(){

    }

    @Test
    public void testCreateAddLocationTab(){
        // check null
        Assert.assertNull(view.getAddLocationB());
        Assert.assertNull(view.getAddLocationB());
        Assert.assertNull(view.getClearAddLocationFieldsB());

        // set object state
        view.setLocationsTabbedPane(new JTabbedPane());
        this.controller = new Controller();
        controller.loadTextStrings();
        view.setStringMap(controller.getStringMap());

        // execute method
        view.createAddLocationTab();

        // assert expected result:
        Assert.assertNotNull(view.getAddLocationB());
        Assert.assertNotNull(view.getAddLocationB());
        Assert.assertNotNull(view.getClearAddLocationFieldsB());

        Assert.assertEquals(
                2
                        + view.getAddLocationTextFields().length
                        + view.getAddLocationLabelObjects().length,
                view.getAddLocationTab().getComponentCount()
        );
        Assert.assertEquals(1, view.getLocationsTabbedPane().getComponentCount());
    }

    @Test
    public void testCreatModifyLocationTab(){
        // check null
        Assert.assertNull(view.getModifyLocationTab());
        Assert.assertNull(view.getModifyLocationTabTable());
        Assert.assertNull(view.getModifyLocationB());
        Assert.assertNull(view.getRefreshModifyLocationFieldsB());

        // set object state
        view.setLocationsTabbedPane(new JTabbedPane());
        this.controller = new Controller();
        controller.loadTextStrings();
        view.setStringMap(controller.getStringMap());

        // execute method
        view.createModifyLocationTab();

        // assert expected outcome
        Assert.assertNotNull(view.getModifyLocationTab());
        Assert.assertNotNull(view.getModifyLocationTabTable());
        Assert.assertNotNull(view.getModifyLocationB());
        Assert.assertNotNull(view.getRefreshModifyLocationFieldsB());

        Assert.assertEquals(
                3
                        + view.getModifyLocationTextFields().length
                        + controller.getStringMap().get("locationLabelsText").split(",").length,
                view.getModifyLocationTab().getComponentCount()
        );

        Assert.assertEquals(1, view.getLocationsTabbedPane().getComponentCount());

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
