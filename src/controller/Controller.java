package controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
// import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Books;
import model.Loans;
import model.Locations;
import model.Model;
import model.SqliteClient;
import model.Validator;
import view.View;

public class Controller {
	
	private Model model;
	private View view;
	private SqliteClient db;
	private Books books;
	private Locations locations;
	private Loans loans;
	private Validator val;
	private ArrayList<String> textStrings = new ArrayList<String>();
	private String id = "";
	private String bookTitle = "";
	
	public Controller() {
		print("[#] Starting Controller...");
		loadTextStrings();
		db = new SqliteClient();
		val=new Validator();
		model = new Model(textStrings, db);	
	  
		books = new Books(db, val, textStrings);
		locations = new Locations(db, val);
		loans = new Loans(db, val, textStrings);
		
		view = new View(model, textStrings);
	}
	
	public void initController() {
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
		view.getRefreshModifyBookFieldsB().addActionListener(e -> refreshModifyBookFieldsB());
		view.getDeleteBookB().addActionListener(e -> deleteBookB());
		view.getModifyBookTabTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				modifyBookTabTable();
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
				modifyLocationTabTable();
			}
		});
		view.getDeleteLocationTabTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				deleteLocationTabTable();
			}
		});
		view.getRefreshAllLocationsB().addActionListener(e -> refreshAllLocationsB());
		view.getClearAddLocationFieldsB().addActionListener(e -> clearAddLocationFieldsB());
		view.getDeleteLocationB().addActionListener(e -> clearAddLocationFieldsB());
		view.getRefreshModifyLocationFieldsB().addActionListener(e -> refreshModifyLocationFieldsB());
		
		
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
		int reply=JOptionPane.showConfirmDialog(view.getFrame(),textStrings.get(83),textStrings.get(84),JOptionPane.YES_NO_OPTION);
		if(reply==JOptionPane.YES_OPTION) {
			model.saveDbToCsv();
			JOptionPane.showMessageDialog(view.getFrame(), textStrings.get(85), textStrings.get(86), JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void exitMI() {
		int reply=JOptionPane.showConfirmDialog(view.getFrame(),textStrings.get(81),textStrings.get(82),JOptionPane.YES_NO_OPTION);
		if(reply==JOptionPane.YES_OPTION) {
			model.closeProgram();
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
			model.fillJoinedModel(view.getAllBooksModel(), textStrings.get(29), "books", "locations", "books.location", "locations.locations_index");
			model.setColumnWidths(view.getAllBooksTabTable(), view.getBookColumnWidths());
		}catch (Exception e) {
			JOptionPane.showMessageDialog(view.getFrame(), textStrings.get(37)+" fillJoinedModel :\n"+e, textStrings.get(38), JOptionPane.ERROR_MESSAGE);
		    System.out.println(e.getMessage());
		}
	}
	
	public void searchBooksB() {
		String text = view.getSearchTerm().getText().trim();
		String field = String.valueOf(view.getSearchBookCombo().getSelectedItem());
		books.searchBooks(field,text,view.getBookAliases(),view.getSearchBooksModel());
	}
	
	public void addBookB() {
		if(books.insertBook(view.getAddBookTextFields(),view.getComboLocations())) {
			JOptionPane.showMessageDialog(view.getFrame(), textStrings.get(53), textStrings.get(54), JOptionPane.INFORMATION_MESSAGE);
			view.refreshBookTables();
			clearAddBookFieldsB();
		}else {
			JOptionPane.showMessageDialog(view.getFrame(), textStrings.get(55), textStrings.get(56), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void clearAddBookFieldsB() {
		for(int i=0;i<view.getAddBookTextFields().length;i++) {
			view.getAddBookTextFields()[i].setText("");;
		}
		view.setLocationsList(model.createLocationsList());
		view.getComboLocations().setModel(new DefaultComboBoxModel<String>(view.getLocationsList()));
		view.getComboLocations().setSelectedItem(null);
	}
	
	public void modifyBookB() {
		if(books.modifyBook(view.getModifyBookInputObjects(),view.getBookColumns(),id)) {
			JOptionPane.showMessageDialog(view.getFrame(), textStrings.get(57), textStrings.get(58), JOptionPane.INFORMATION_MESSAGE);
			view.refreshBookTables();
			refreshModifyBookFieldsB();
		}else {
			JOptionPane.showMessageDialog(view.getFrame(), textStrings.get(59), textStrings.get(60), JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void refreshModifyBookFieldsB() {
		JTextField textField;
		for(int i=0;i<view.getModifyBookInputObjects().length;i++) {
			if (view.getModifyBookInputObjects()[i] instanceof JTextField) {
				textField=(JTextField) view.getModifyBookInputObjects()[i];
				textField.setText("");
			}
			else if(view.getModifyBookInputObjects()[i] instanceof JComboBox) {
				view.setLocationsList(model.createLocationsList());
				view.getModifyBookCB().setModel(new DefaultComboBoxModel<String>(view.getLocationsList()));
				view.getModifyBookCB().setSelectedItem(null);
			}
		}
		try {
			books.fillModel(view.getModifyBooksModel(),view.getBookAliases(),"books");
			model.setColumnWidths( view.getModifyBookTabTable(), view.getBookColumnWidths());
		}catch (Exception e){
			JOptionPane.showMessageDialog(view.getFrame(), textStrings.get(39)+" fillModel :\n"+e, textStrings.get(40), JOptionPane.ERROR_MESSAGE);
	        System.out.println(e.getMessage());
		}
	}
	
	public void modifyBookTabTable() {
		JTextField textField;
		for(int i=0;i<view.getModifyBookInputObjects().length;i++) {
			if(view.getModifyBookInputObjects()[i] instanceof JTextField ) {
				textField=(JTextField) view.getModifyBookInputObjects()[i];
				textField.setText(String.valueOf(view.getModifyBookTabTable().getValueAt(view.getModifyBookTabTable().getSelectedRow(),i+1)));
				id=String.valueOf(view.getModifyBookTabTable().getValueAt(view.getModifyBookTabTable().getSelectedRow(),0));
			}
			else if(view.getModifyBookInputObjects()[i] instanceof JComboBox) {
				// view.getModifyBookCB() = new JComboBox<String>();
				// view.getModifyBookCB() = JComboBox<String> view.getModifyBookInputObjects()[i];
				view.getModifyBookCB().setSelectedIndex(Integer.parseInt(String.valueOf(view.getModifyBookTabTable().getValueAt(view.getModifyBookTabTable().getSelectedRow(),i+1)))-1);
			}
		}
	}
	
	public void deleteBookB() {
		int reply=JOptionPane.showConfirmDialog(view.getFrame(),textStrings.get(61)+bookTitle+"' ?",textStrings.get(62),JOptionPane.YES_NO_OPTION);
		if(reply==JOptionPane.YES_OPTION) {
			model.deleteRow("books", id);
			view.refreshBookTables();
		}
	}
	
	public void deleteBookTabTable(){
		bookTitle = String.valueOf(view.getDeleteBookTabTable().getValueAt(view.getDeleteBookTabTable().getSelectedRow(),1));
		id = String.valueOf(view.getDeleteBookTabTable().getValueAt(view.getDeleteBookTabTable().getSelectedRow(),0));
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
			print("...");
			books.fillModel(view.getAllLocationsModel(), textStrings.get(30), "locations");
			model.setColumnWidths(view.getAllLocationsTabTable(), view.getLocationColumnWidths());
		}catch (Exception e) {
			JOptionPane.showMessageDialog(view.getFrame(), textStrings.get(37)+" fillJoinedModel :\n"+e, textStrings.get(38), JOptionPane.ERROR_MESSAGE);
		    System.out.println(e.getMessage());
		}
	}
	
	public void addLocationB() {
		if(locations.insertLocation(view.getAddLocationTextFields())) {
			JOptionPane.showMessageDialog(view.getFrame(), textStrings.get(63), textStrings.get(64), JOptionPane.INFORMATION_MESSAGE);
			view.refreshLocationTables();
			clearAddLocationFieldsB();
		}else {
			JOptionPane.showMessageDialog(view.getFrame(), textStrings.get(65), textStrings.get(66), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void clearAddLocationFieldsB() {
		for(int i=0;i<view.getAddLocationTextFields().length;i++) {
			view.getAddLocationTextFields()[i].setText("");;
		}
	}

	public void modifyLocationB() {
		if(locations.modifyLocation(view.getModifyLocationTextFields(),view.getLocationFields(),id)) {
			JOptionPane.showMessageDialog(view.getFrame(), textStrings.get(67), textStrings.get(68), JOptionPane.INFORMATION_MESSAGE);
			view.refreshLocationTables();
			refreshModifyLocationFieldsB();
		}else {
			JOptionPane.showMessageDialog(view.getFrame(), textStrings.get(69), textStrings.get(70), JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void modifyLocationTabTable() {
		for(int i=0;i<view.getModifyLocationTextFields().length;i++) {
			view.getModifyLocationTextFields()[i].setText(String.valueOf(view.getModifyLocationTabTable().getValueAt(view.getModifyLocationTabTable().getSelectedRow(),i+1)));
			id=String.valueOf(view.getModifyLocationTabTable().getValueAt(view.getModifyLocationTabTable().getSelectedRow(),0));
		}
	}
	
	public void refreshModifyLocationFieldsB() {
		for(JTextField field: view.getModifyLocationTextFields()) {
			field.setText("");
		}
	}
	
	public void deleteLocationTabTable() {
		id=String.valueOf(view.getDeleteLocationTabTable().getValueAt(view.getDeleteLocationTabTable().getSelectedRow(),0));	
	}
	
	public void deleteLocationB() {
		int reply=JOptionPane.showConfirmDialog(view.getFrame(),textStrings.get(71),textStrings.get(72),JOptionPane.YES_NO_OPTION);
		if(reply==JOptionPane.YES_OPTION) {
			model.deleteRow("locations", id);
			view.refreshLocationTables();
		}
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
		int reply=JOptionPane.showConfirmDialog(view.getFrame(),textStrings.get(73)+bookTitle+"' ?",textStrings.get(74),JOptionPane.YES_NO_OPTION);
		if(reply==JOptionPane.YES_OPTION & !view.getLoanBookTextFields()[1].getText().isEmpty()) {
			if(loans.loanThisBook(view.getLoanBookTextFields())) {
				JOptionPane.showMessageDialog(view.getFrame(), textStrings.get(75), textStrings.get(76), JOptionPane.INFORMATION_MESSAGE);
				view.refreshLoanTables();
				for (JTextField field: view.getLoanBookTextFields()) {
					field.setText("");
				}
			}else {
				JOptionPane.showMessageDialog(view.getFrame(), textStrings.get(77), textStrings.get(78), JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public void makeLoanTabTable() {
		view.getLoanBookTextFields()[0].setText(String.valueOf(view.getMakeLoanTabTable().getValueAt(view.getMakeLoanTabTable().getSelectedRow(),0)));
		bookTitle = String.valueOf(view.getMakeLoanTabTable().getValueAt(view.getMakeLoanTabTable().getSelectedRow(),1));
	}
	
	public void refreshLoansTableB() {
		view.refreshLoanTables();
	}
	
	public void returnLoanTabTable() {
		// view.getLoanId().setText(String.valueOf(view.getReturnLoanTabTable().getValueAt(view.getReturnLoanTabTable().getSelectedRow(),0)));
		id=String.valueOf(view.getReturnLoanTabTable().getValueAt(view.getReturnLoanTabTable().getSelectedRow(),0));
	}
	
	public void returnBookB() {
		int reply=JOptionPane.showConfirmDialog(view.getFrame(),textStrings.get(79),textStrings.get(80),JOptionPane.YES_NO_OPTION);
		if(reply==JOptionPane.YES_OPTION) {
			loans.returnBook(id, view.getLoansModel(), loans.getFields());
			view.refreshLoanTables();
		}
	}
	
	
	// OTHER
	public void loadTextStrings() {
		print("[#] Loading text strings...");
		String line;
		String chunks[];
		String filePath="strings.txt";
		
		try(BufferedReader br=new BufferedReader(new FileReader(filePath))) {
			line = br.readLine().trim();
			while(line != null) {
				if (line.trim().startsWith("#")) {
				//	
				}else if (line.trim().length()==0){
				//	
				}
				else{
					chunks = line.split("::");
					textStrings.add(chunks[1].trim());
				}
				line= br.readLine();
			}
			print("[#] Loaded strings: "+textStrings.size());
		}catch(EOFException eofe) {
			print("\n[!] File read complete\n");
		}catch(IOException ioe) {
			print("\n[!] I/O error: "+ioe);
		}catch(Exception e) {
			print("\n[!] Error loading text strings: "+e);
			e.printStackTrace();
		}
	}
	
	static void print(String s) {
		System.out.print(s+"\n");
	}

}
