package samuelmovi.familyLibraryJava.controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import samuelmovi.familyLibraryJava.model.*;
import samuelmovi.familyLibraryJava.repo.BookRepository;
import samuelmovi.familyLibraryJava.repo.BookViewRepository;
import samuelmovi.familyLibraryJava.repo.LoanRepository;
import samuelmovi.familyLibraryJava.repo.LocationRepository;
import samuelmovi.familyLibraryJava.view.View;

public class Controller {
	
	private View view;
	private Map<String, String> stringMap;
	
	// BOOKS
	@Autowired
	private BookRepository books;
	@Autowired
	private BookViewRepository bookViews;
	private String bookIndex;
	private String[] bookFields = {"book_index", "title", "author", "genre", "publisher", "isbn", "publish_date", "purchase_date", "location"};
	private String[] bookSearchFields = { "title", "author", "genre", "publisher", "isbn" };
	private String bookTitle = "";

	// LOCATIONS
	@Autowired
	private LocationRepository locations;
	private String locationIndex;
	private String locationFields[] = {"location_index", "address","room","furniture","details"};

	// LOANS
	@Autowired
	private LoanRepository loans;
	private String loanIndex;
	private String[] loanFields = {"loan_index", "book", "borrower", "loan_date", "return_date"};


	public Controller(View view) {
		this.view = view;
		this.view.setUpFrame();
	}
	
	public void initController() {

		//view.initSetup();

		loadTextStrings();
		// set samuelmovi.familyLibraryJava.view info
		view.setLocationsArray(createLocationList());
		view.setStringMap(stringMap);
		// books stuff
		view.setBookJoinAliases(stringMap.get("bookJoinAliases").split("/"));
		view.setAll_books((List<Book>)books.findAll());
		view.setAllBookViews((List<BookView>)bookViews.findAll());
		view.setBookFieldAlias(stringMap.get("bookFieldAlias").split("/"));
		view.setAvailableBooks(books.findByLoaned(false));
		view.setSearchFields(stringMap.get("searchBookFields").split("/"));

		// locations stuff
		view.setLocationAliases(stringMap.get("locationAliases"));
		view.setAll_locations((List<Location>)locations.findAll());
		view.setLocationFieldAlias(stringMap.get("locationFieldAlias").split("/"));

		// loans stuff
		view.setLoanJoinAliases(stringMap.get("loanJoinAliases"));
		view.setAll_loans((List<Loan>)loans.findAll());
		view.setLoanFieldAlias(stringMap.get("loanFieldALias").split("/"));

		// FINAL SETUP
		view.finalSetup();
		
		// SYSTEM
		view.getSaveDataMI().addActionListener(e -> saveDataMI());
		view.getExitMI().addActionListener(e -> exitMI());		
		
		// BOOKS
		view.getBooksB().addActionListener(e -> showBooksB());
		view.getRefreshAllBooksTabB().addActionListener(e -> refreshAllBooksTab());
		view.getSearchBookB().addActionListener(e -> searchBooksB());
		view.getAddBookB().addActionListener(e -> addBookB());
		view.getClearAddBookFieldsB().addActionListener(e -> clearAddBookFieldsB());
		view.getModifyBookB().addActionListener(e -> modifyBookB());
		view.getResetModifyBookTabB().addActionListener(e -> resetModifyBookTab());
		view.getDeleteBookB().addActionListener(e -> deleteBookB());
		view.getModifyBookTabTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO: find workaround to error selecting correct choice
				fillModifyBookFields();
			}
		});
		view.getDeleteBookTabTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				deleteBookTabTable();
			}
		});
		
		// LOCATIONS
		view.getLocationsB().addActionListener(e -> showLocationsB());
		view.getAddLocationB().addActionListener(e -> addLocationB());
		view.getClearAddLocationFieldsB().addActionListener(e -> clearAddLocationFieldsB());
		view.getModifyLocationB().addActionListener(e -> modifyLocationB());
		view.getDeleteLocationB().addActionListener(e -> deleteLocationB());
		view.getModifyLocationTabTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				fillModifyLocationFields();
			}
		});
		view.getDeleteLocationTabTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setLocationToDelete();
			}
		});
		view.getRefreshAllLocationsB().addActionListener(e -> refreshAllLocationsB());
		view.getClearAddLocationFieldsB().addActionListener(e -> clearAddLocationFieldsB());
		view.getDeleteLocationB().addActionListener(e -> clearAddLocationFieldsB());
		view.getRefreshModifyLocationFieldsB().addActionListener(e -> clearModifyLocationFields());
		
		// LOANS
		view.getLoansB().addActionListener(e -> showLoansB());
		view.getRefreshLoanTablesB().addActionListener(e -> refreshLoansTableB());
		view.getLoanBookB().addActionListener(e -> loanBookB());
		view.getReturnBookB().addActionListener(e -> returnBookB());
		view.getMakeLoanTabTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				makeLoanTabTable();
			}
		});
		view.getReturnLoanTabTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				returnLoanTabTable();
			}
		});
		
	}
	
	// SYSTEM
	public void saveDataMI() {
		int reply=JOptionPane.showConfirmDialog(view.getFrame(), stringMap.get("saveData"), stringMap.get("saveDataHeader") ,JOptionPane.YES_NO_OPTION);
		if(reply==JOptionPane.YES_OPTION) {
			saveDbToCsv();
			JOptionPane.showMessageDialog(view.getFrame(), stringMap.get("successfulSave"), stringMap.get("successfulSaveHeader"), JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void exitMI() {
		int reply = JOptionPane.showConfirmDialog(view.getFrame(),stringMap.get("confirmExit"),stringMap.get("confirmExitHeader"),JOptionPane.YES_NO_OPTION);
		if(reply==JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
	// BOOKS
	public void showBooksB() {		
		if(!view.getBooksPanel().isVisible()) {
			view.getLoansPanel().setVisible(false);
			view.getHomePanel().setVisible(false);
			view.getLocationsPanel().setVisible(false);
			view.getBooksPanel().setVisible(true);
			
			view.getBooksB().setBackground(view.booksColor);
			view.getLocationsB().setBackground(Color.WHITE);
			view.getLoansB().setBackground(Color.WHITE);
		}
	}
	
	public void refreshAllBooksTab() {
		try {
			view.fillBookViewModel(view.getAllBooksModel(), (List<BookView>)bookViews.findAll());
			view.setColumnWidths(view.getAllBooksTabTable(), view.getBookColumnWidths());
		}catch (Exception e) {
			JOptionPane.showMessageDialog(view.getFrame(), stringMap.get("refreshBookTablesX")+" fillJoinedModel :\n"+e, stringMap.get("refreshBookTablesXHeader"), JOptionPane.ERROR_MESSAGE);
		    System.out.println(e.getMessage());
		}
	}
	
	public void searchBooksB() {
		// get database field from combo
		int selection = view.getSearchBookCombo().getSelectedIndex();
		String field = bookSearchFields[selection];
		// get value from text field
		String value = view.getSearchTerm().getText().trim();

		List<Book> filteredBooks = new ArrayList<>();
		if(field.equals(bookSearchFields[0])){
			filteredBooks = books.findByTitle(value);
		}
		else if(field.equals(bookSearchFields[1])){
			filteredBooks = books.findByAuthor(value);
		}
		else if(field.equals(bookSearchFields[2])){
			filteredBooks =  books.findByGenre(value);
		}
		else if(field.equals(bookSearchFields[3])){
			filteredBooks =  books.findByPublisher(value);
		}
		else if(field.equals(bookSearchFields[4])){
			filteredBooks =  books.findByIsbn(value);
		}

		view.fillBookModel(view.getSearchBooksModel(), filteredBooks);
	}
	
	public void addBookB() {
		JTextField textfields[] = view.getAddBookTextFields();
		// create Book object from into in fields and pass that to dao
		String locationId = String.valueOf(view.getComboLocations().getSelectedItem()).split("/")[0];

		Book newBook = new Book(
				textfields[0].getText(),
				textfields[1].getText(),
				textfields[2].getText(),
				textfields[3].getText(),
				textfields[4].getText(),
				textfields[5].getText(),
				textfields[6].getText(),
				Integer.valueOf(locationId)
		);

		try{
			books.save(newBook);
			JOptionPane.showMessageDialog(view.getFrame(), stringMap.get("addBookC"), stringMap.get("addBookCHeader"), JOptionPane.INFORMATION_MESSAGE);
			view.setAll_books((List<Book>)books.findAll());
			view.refreshBookTables();
			clearAddBookFieldsB();
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(view.getFrame(), stringMap.get("addBookX"), stringMap.get("addBookXHeader"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void clearAddBookFieldsB() {
		for(int i=0;i<view.getAddBookTextFields().length;i++) {
			view.getAddBookTextFields()[i].setText("");;
		}
		view.setLocationsArray(createLocationList());
		view.getComboLocations().setModel(new DefaultComboBoxModel<String>(view.getLocationsArray()));
		view.getComboLocations().setSelectedItem(null);
	}
	
	public void modifyBookB() {
		// get the input objects
		Object[] inputFields = view.getModifyBookInputObjects();
		// extract content and add to vector
		Vector<String> vector = new Vector<String>();
		for (int i=0; i<(inputFields.length-1); i++){
			JTextField textField = (JTextField) inputFields[i];
			vector.add(textField.getText());
		}
		// set location id from combo
		vector.add(
				String.valueOf(view.getModifyBookCB().getSelectedItem()).split("/")[0]
		);
		// create Book object from info in fields
		try{
			Book newBook = new Book(
					vector.get(0),
					vector.get(1),
					vector.get(2),
					vector.get(3),
					vector.get(4),
					vector.get(5),
					vector.get(6),
					Long.valueOf(vector.get(7))
			);
			books.save(newBook);
			JOptionPane.showMessageDialog(view.getFrame(), stringMap.get("addBookC"), stringMap.get("addBookCHeader"), JOptionPane.INFORMATION_MESSAGE);
			view.setAll_books(books.findAll());
			view.refreshBookTables();
			clearAddBookFieldsB();
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(view.getFrame(), stringMap.get("addBookX"), stringMap.get("addBookXHeader"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void resetModifyBookTab() {
		JTextField textField;
		for(int i=0;i<view.getModifyBookInputObjects().length;i++) {
			if (view.getModifyBookInputObjects()[i] instanceof JTextField) {
				textField=(JTextField) view.getModifyBookInputObjects()[i];
				textField.setText("");
			}
			else if(view.getModifyBookInputObjects()[i] instanceof JComboBox) {
				view.setLocationsArray(createLocationList());
				view.getModifyBookCB().setModel(new DefaultComboBoxModel<String>(createLocationList()));
				view.getModifyBookCB().setSelectedItem(null);
			}
		}
		view.setAll_books((List<Book>)books.findAll());
		view.refreshBookTables();
	}
	
	public void fillModifyBookFields() {
		JTextField textField;
		for(int i=0;i<view.getModifyBookInputObjects().length;i++) {
			if(view.getModifyBookInputObjects()[i] instanceof JTextField ) {
				textField=(JTextField) view.getModifyBookInputObjects()[i];
				textField.setText(
						String.valueOf( 
								view.getModifyBookTabTable()
								.getValueAt(view.getModifyBookTabTable().getSelectedRow(),i+1)));
				bookIndex = String.valueOf(
								view.getModifyBookTabTable()
								.getValueAt(view.getModifyBookTabTable().getSelectedRow(),0));
			}
			else if(view.getModifyBookInputObjects()[i] instanceof JComboBox) {
				JComboBox locationCombo = (JComboBox) view.getModifyBookInputObjects()[i];
				// get id of selected book
				String location_index = String.valueOf(view.getModifyBookTabTable()
						.getValueAt(view.getModifyBookTabTable()
								.getSelectedRow(),i+1));
				// match id to first chunk of location combo items
				for (int j=0; j<locationCombo.getItemCount(); j++){
					String item = String.valueOf(locationCombo.getItemAt(j));
					if(location_index.equals(item.split("/")[0])){
						locationCombo.setSelectedIndex(j);
						break;
					}
				}
			}
		}
	}
	
	public void deleteBookB() {
		int reply=JOptionPane.showConfirmDialog(view.getFrame(),stringMap.get("deleteBookC")+bookTitle+"' ?",stringMap.get("deleteBookCHeader"),JOptionPane.YES_NO_OPTION);
		if(reply==JOptionPane.YES_OPTION) {
			books.deleteById(Long.valueOf(bookIndex));
			view.setAll_books((List<Book>)books.findAll());
			view.refreshBookTables();
		}
	}
	
	public void deleteBookTabTable(){
		bookTitle = String.valueOf(view.getDeleteBookTabTable().getValueAt(view.getDeleteBookTabTable().getSelectedRow(),1));
		bookIndex = String.valueOf(view.getDeleteBookTabTable().getValueAt(view.getDeleteBookTabTable().getSelectedRow(),0));
	}

	// LOCATIONS
	public void showLocationsB() {
		if(!view.getLocationsPanel().isVisible()) {
			view.getBooksPanel().setVisible(false);
			view.getLoansPanel().setVisible(false);
			view.getHomePanel().setVisible(false);
			view.getLocationsPanel().setVisible(true);
			
			view.getLocationsB().setBackground(view.locationsColor);
			view.getBooksB().setBackground(Color.WHITE);
			view.getLoansB().setBackground(Color.WHITE);
		}
	}
	
	public void refreshAllLocationsB() {
		try {
			print("[#] Refreshing locations...");
			view.fillLocationModel(view.getAllLocationsModel(), (List<Location>) locations.findAll());
			view.setColumnWidths(view.getAllLocationsTabTable(), view.getLocationColumnWidths());
		}catch (Exception e) {
			JOptionPane.showMessageDialog(view.getFrame(), stringMap.get("refreshBookTablesX")+" fillJoinedModel :\n"+e, stringMap.get("refreshBookTablesXHeader"), JOptionPane.ERROR_MESSAGE);
		    System.out.println(e.getMessage());
		}
	}
	
	public void addLocationB() {
		JTextField textfields[] = view.getAddLocationTextFields();
		// create Location object from into in fields and pass that to dao
		Location newLoc = new Location(
				textfields[0].getText(),
				textfields[1].getText(),
				textfields[2].getText(),
				textfields[3].getText()
		);
		try{
			locations.save(newLoc);
			JOptionPane.showMessageDialog(view.getFrame(), stringMap.get("addLocationC"), stringMap.get("addLocationCHeader"), JOptionPane.INFORMATION_MESSAGE);
			view.setAll_locations((List<Location>)locations.findAll());
			view.refreshLocationTables();
			clearAddLocationFieldsB();
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(view.getFrame(), stringMap.get("addLocationX"), stringMap.get("addLocationXHeader"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void clearAddLocationFieldsB() {
		for(int i=0;i<view.getAddLocationTextFields().length;i++) {
			view.getAddLocationTextFields()[i].setText("");;
		}
	}

	public void modifyLocationB() {
		JTextField textfields[] = view.getModifyLocationTextFields();
		// create Location object from into in fields and pass that to dao
		Location newLoc = new Location(
				textfields[0].getText(),
				textfields[1].getText(),
				textfields[2].getText(),
				textfields[3].getText()
		);

		try{
			locations.save(newLoc);
			JOptionPane.showMessageDialog(view.getFrame(), stringMap.get("modifyLocationC"), stringMap.get("modifyLocationCHeader"), JOptionPane.INFORMATION_MESSAGE);
			view.setAll_locations((List<Location>)locations.findAll());
			view.refreshLocationTables();
			clearAddLocationFieldsB();
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(view.getFrame(), stringMap.get("modifyLocationX"), stringMap.get("modifyLocationXHeader"), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void fillModifyLocationFields() {
		JTextField textField;
		for(int i=0;i<view.getModifyLocationTextFields().length;i++) {
			textField = view.getModifyLocationTextFields()[i];
			textField.setText(String.valueOf(
							view.getModifyLocationTabTable()
							.getValueAt(view.getModifyLocationTabTable()
									.getSelectedRow(),i+1)));
			locationIndex = String.valueOf(
					view.getModifyLocationTabTable()
					.getValueAt(
							view.getModifyLocationTabTable().getSelectedRow(),0));
		}
	}
	
	public void clearModifyLocationFields() {
		for(JTextField field: view.getModifyLocationTextFields()) {
			field.setText("");
		}
	}
	
	public void setLocationToDelete() {
		locationIndex = String.valueOf(
				view.getDeleteLocationTabTable()
				.getValueAt(view.getDeleteLocationTabTable()
						.getSelectedRow(),0));	
	}
	
	public void deleteLocationB() {
		int reply = JOptionPane.showConfirmDialog(
				view.getFrame(),
				stringMap.get("deleteLocationC"),
				stringMap.get("deleteLocationCHeader"),
				JOptionPane.YES_NO_OPTION
				);
		if(reply==JOptionPane.YES_OPTION) {
			this.locations.deleteById(Long.valueOf(locationIndex));
			view.setAll_locations((List<Location>) locations.findAll());
			view.refreshLocationTables();
		}
	}

	public String[] createLocationList(){
		String[] locationArray = null;

		try {
			List<Location> locationsList = (List<Location>) locations.findAll();
			locationArray = new String[locationsList.size()];
			for (int i=0; i<locationsList.size(); i++) {
				Location loc = locationsList.get(i);
				locationArray[i] = loc.getIndex() + "/ ";
				locationArray[i] += loc.getAddress() + " / ";
				locationArray[i] += loc.getRoom() + " / ";
				locationArray[i] += loc.getFurniture() + " / ";
				locationArray[i] += loc.getDetails();
			}
		}catch(Exception e) {
			System.out.println("\n[!] Error at createLocationList : "+e+"\n");
		}
		return locationArray;
	}

	// LOANS
	public void showLoansB() {
		if(!view.getLoansPanel().isVisible()) {
			view.getBooksPanel().setVisible(false);
			view.getLocationsPanel().setVisible(false);
			view.getHomePanel().setVisible(false);
			view.getLoansPanel().setVisible(true);
			
			view.getLoansB().setBackground(view.loansColor);
			view.getLocationsB().setBackground(Color.WHITE);
			view.getBooksB().setBackground(Color.WHITE);
		}
	}
	
	public void loanBookB() {
		int reply=JOptionPane.showConfirmDialog(view.getFrame(),stringMap.get("loanBookQ")+bookTitle+"' ?",stringMap.get("loanBookQHeader"),JOptionPane.YES_NO_OPTION);
		if(reply==JOptionPane.YES_OPTION & !view.getLoanBookTextFields()[1].getText().isEmpty()) {
			if(loanThisBook(view.getLoanBookTextFields())) {
				JOptionPane.showMessageDialog(view.getFrame(), stringMap.get("loanBookC"), stringMap.get("loanBookCHeader"), JOptionPane.INFORMATION_MESSAGE);
				view.setAll_books((List<Book>)books.findAll());
				view.refreshBookTables();
				view.setAll_loans((List<Loan>)loans.findAll());
				view.refreshLoanTables();
				for (JTextField field: view.getLoanBookTextFields()) {
					field.setText("");
				}
			}else {
				JOptionPane.showMessageDialog(view.getFrame(), stringMap.get("loanBookX"), stringMap.get("loanBookXHeader"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public boolean loanThisBook(JTextField inputFields[]) {
		String book_index = inputFields[0].getText().trim();
		String borrower = inputFields[1].getText().trim();

		if( book_index.isEmpty() && borrower.isEmpty()) {
			return false;
		}
		else {
			Loan newLoan = new Loan(
					Long.valueOf(book_index),
					borrower
			);

			try{
				loans.save(newLoan);
				// set loaned=true for loaned book
				Optional<Book> opBook = books.findById(Long.valueOf(book_index));
				if (opBook.isPresent()){
					Book book = opBook.get();
					book.setLoaned(true);
					books.save(book);
					print("[#] update book loaned = true");
					return true;
				}
				else{
					return false;
				}
			}
			catch (Exception e){
				return false;
			}
		}
	}
	
	public void makeLoanTabTable() {
		view.getLoanBookTextFields()[0].setText(String.valueOf(view.getMakeLoanTabTable().getValueAt(view.getMakeLoanTabTable().getSelectedRow(),0)));
		bookTitle = String.valueOf(view.getMakeLoanTabTable().getValueAt(view.getMakeLoanTabTable().getSelectedRow(),1));
	}
	
	public void refreshLoansTableB() {
		view.setAll_loans((List<Loan>)loans.findAll());
		view.refreshLoanTables();
	}
	
	public void returnLoanTabTable() {
		// samuelmovi.familyLibraryJava.view.getLoanId().setText(String.valueOf(samuelmovi.familyLibraryJava.view.getReturnLoanTabTable().getValueAt(samuelmovi.familyLibraryJava.view.getReturnLoanTabTable().getSelectedRow(),0)));
		loanIndex=String.valueOf(view.getReturnLoanTabTable().getValueAt(view.getReturnLoanTabTable().getSelectedRow(),0));
	}
	
	public void returnBookB() {
		int reply = JOptionPane.showConfirmDialog(
				view.getFrame(),
				stringMap.get("returnBookC"),
				stringMap.get("returnBookCHeader"),JOptionPane.YES_NO_OPTION);
		if(reply==JOptionPane.YES_OPTION) {
			Optional<Loan> opLoan = loans.findById(Long.valueOf(loanIndex));
			if (opLoan.isPresent()) {
				Loan loan = opLoan.get();
				loan.setReturn_date(String.valueOf(LocalDate.now()));
				loans.save(loan);
				view.setAll_loans((List<Loan>)loans.findAll());
				view.refreshLoanTables();
			}
		}
	}
	
	// OTHER
	public void loadTextStrings() {
		print("[#] Loading text strings...");
		String line;
		String chunks[];
		String filePath="strings.txt";
		stringMap = new HashMap<String, String>();
		try(BufferedReader br=new BufferedReader(new FileReader(filePath))) {
			line = br.readLine().trim();
			while(line != null) {
				if (!line.trim().startsWith("#") && line.trim().length()!=0){
					chunks = line.split("::");
					stringMap.put(chunks[0].trim(), chunks[1].trim());
				}
				line= br.readLine();
			}
			print("[#] Loaded strings: "+stringMap.size());
		}catch(EOFException eofe) {
			print("\n[!] File read complete\n");
		}catch(IOException ioe) {
			print("\n[!] I/O error: "+ioe);
		}catch(Exception e) {
			print("\n[!] Error loading text strings: "+e);
			e.printStackTrace();
		}
	}
	
	public void saveDbToCsv() {
		ArrayList<String> text = new ArrayList<String>();
		String holder="";
		
		try {
			// save books data
			List<Book> all_books = (List<Book>) books.findAll();
			for (String s: bookFields) {
				holder += s;
				holder += ", ";
			}
			text.add(holder);
			holder = "";
			for (Book b: all_books) {
				holder += b.getTitle();
				holder += b.getAuthor();
				holder += b.getGenre();
				holder += b.getPublisher();
				holder += b.getIsbn();
				holder += b.getPublishDate();
				holder += b.getPurchase_date();
				text.add(holder);
				holder = "";
			}
			writeToCsv(text, "books");
			text.clear();
			
			// save locations data
			List<Location> all_locations = (List<Location>) locations.findAll();
			for (String s: locationFields) {
				holder += s;
				holder += ", ";
			}
			text.add(holder);
			holder = "";
			for (Location loc: all_locations) {
				holder += loc.getAddress();
				holder += loc.getRoom();
				holder += loc.getFurniture();
				holder += loc.getDetails();
				text.add(holder);
				holder = "";
			}
			writeToCsv(text, "locations");
			text.clear();
			// save loans data
			List<Loan> all_loans = (List<Loan>) loans.findAll();
			for (String s: loanFields) {
				holder += s;
				holder += ", ";
			}
			text.add(holder);
			holder = "";
			for (Loan loan: all_loans) {
				holder += loan.getBook();
				holder += loan.getBorrower();
				holder += loan.getLoan_date();
				holder += loan.getReturn_date();
				text.add(holder);
				holder = "";
			}
			writeToCsv(text, "loans");
			text.clear();
		}
		catch(Exception e) {
			print("\n[!] Exception in save2csv: "+ e);
		}
	}
	
	public void writeToCsv(ArrayList<String> text, String tableName) {
		
		String fileName = LocalDateTime.now().toString();
		fileName = fileName.substring(0, fileName.length()-4);
		fileName  += "_-_" + tableName +".csv";
		try(BufferedWriter br=new BufferedWriter(new FileWriter(fileName))) {
			for (int i = 0; i < text.size(); i++) {
				br.write(text.get(i));
				br.newLine();
			}
		}catch(Exception e) {
			print("|n[!] Exception in writeToCsv : "+ e);
		}
	}
	
	static void print(String s) {
		System.out.print(s+"\n");
	}

	// GETTERS AND SETTERS

	public BookRepository getBooks() {
		return books;
	}

	public void setBooks(BookRepository books) {
		this.books = books;
	}

	public BookViewRepository getBookViews() {
		return bookViews;
	}

	public void setBookViews(BookViewRepository bookViews) {
		this.bookViews = bookViews;
	}

	public LocationRepository getLocations() {
		return locations;
	}

	public void setLocations(LocationRepository locations) {
		this.locations = locations;
	}

	public LoanRepository getLoans() {
		return loans;
	}

	public void setLoans(LoanRepository loans) {
		this.loans = loans;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Map<String, String> getStringMap() {
		return stringMap;
	}

	public String getBookIndex() {
		return bookIndex;
	}

	public void setBookIndex(String bookIndex) {
		this.bookIndex = bookIndex;
	}
}
