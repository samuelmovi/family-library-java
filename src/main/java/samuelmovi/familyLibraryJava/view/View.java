package samuelmovi.familyLibraryJava.view;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import samuelmovi.familyLibraryJava.model.Book;
import samuelmovi.familyLibraryJava.model.BookView;
import samuelmovi.familyLibraryJava.model.Loan;
import samuelmovi.familyLibraryJava.model.Location;

public class View {
	
	private JPanel contentPane;
	
	// book stuff
	private List<Book> all_books;
	private List<BookView> allBookViews;
	private JPanel booksPanel;
	private JPanel allBooksTab = new JPanel();
	private JPanel deleteBooksTab = new JPanel();
	private JButton booksB;
	private JButton searchBookB; 
	private JButton addBookB;
	private JButton clearAddBookFieldsB;
	private JButton modifyBookB;
	private JButton resetModifyBookTabB;
	private JButton deleteBookB;
	private JButton refreshAllBooksTabB;
	private JTable allBooksTabTable;
	private JTable searchBooksTabTable;
	private JTable modifyBookTabTable;
	private JTable deleteBookTabTable;
	private	int[] bookColumnWidths = {25, 150, 125, 100, 80, 80, 95, 95, 80, 95, 95};
	private DefaultTableModel allBooksModel=new DefaultTableModel();
	private DefaultTableModel searchBooksModel=new DefaultTableModel();
	private DefaultTableModel modifyBooksModel=new DefaultTableModel();
	private JComboBox<String> searchBookCombo;
	private TableRowSorter<TableModel> allBooksSorter=new TableRowSorter<TableModel>();
	private TableRowSorter<TableModel> searchBooksSorter=new TableRowSorter<TableModel>();
	private TableRowSorter<TableModel> modifyBooksSorter=new TableRowSorter<TableModel>();
	private TableRowSorter<TableModel> deleteBooksSorter=new TableRowSorter<TableModel>();
	private JTextField addBookTextFields[];
	private JTabbedPane booksTabbedPane;
	private String bookLabelsText[];
	private Object[] modifyBookInputObjects= {new JTextField(),new JTextField(),new JTextField(),new JTextField(),new JTextField(),new JTextField(),new JTextField(),new JComboBox<String>()};
	private String[] searchFields;
	private String[] bookFieldAlias;
	private String[] bookJoinAliases;
	private JComboBox<String> comboLocations;
	private JComboBox<String> modifyBookCB;

	//  location stuff
	private List<Location> all_locations;
	private String[] locationsArray;
	private JButton refreshAllLocationsB;
	private JPanel locationsPanel;
	private JButton modifyLocationB;
	private JButton clearAddLocationFieldsB;
	private JButton deleteLocationB;
	private JPanel allLocationsTab = new JPanel();
	private JPanel deleteLocationTab = new JPanel();
	private JButton locationsB;	
	private JButton addLocationB;
	private DefaultTableModel allLocationsModel=new DefaultTableModel();
	private JTable allLocationsTabTable;
	private JTable modifyLocationTabTable;
	private JButton refreshModifyLocationFieldsB;
	private JTable deleteLocationTabTable;
	private int[] locationColumnWidths = {25, 250, 200, 200, 300};
	private TableRowSorter<TableModel> locationsSorter=new TableRowSorter<TableModel>();
	private JTextField addLocationTextFields[] = new JTextField[4];
	private JTabbedPane locationsTabbedPane;
	private String locationLabelsText[];
	private JTextField modifyLocationTextFields[];
	private String locationAliases;
	private String[] locationFieldAlias;

	// loans stuff
	private List<Loan> all_loans;
	private List<Book> availableBooks;
	private JPanel loansPanel;
	private JButton refreshLoanTablesB;
	private JButton returnBookB;
	private JPanel allLoansTab = new JPanel();
	private JButton loansB;
	private JButton loanBookB;	
	private DefaultTableModel loansModel=new DefaultTableModel();
	private DefaultTableModel availableBooksModel=new DefaultTableModel();
	private JTable allLoansTabTable;
	private JTable makeLoanTabTable;
	private JTable returnLoanTabTable;
	private int loansColumnWidths[]= {27, 290, 250, 175, 175}; 
	private TableRowSorter<TableModel> loansSorter=new TableRowSorter<TableModel>();
	private TableRowSorter<TableModel> allLoansSorter=new TableRowSorter<TableModel>();
	private JTextField loanBookTextFields[];
	private JTabbedPane loansTabbedPane;
	private String loanJoinAliases;
	private String[] loanFieldAlias;

	// SYSTEM
	private JFrame frame;
	private JPanel homePanel;
	public Color booksColor=new Color(88,208,244);
	public Color locationsColor=new Color(255, 204, 102);
	public Color loansColor=new Color(51,234,185);
	private JTextField searchTerm;
	private Font mediumBoldFont=new Font(Font.SANS_SERIF, Font.BOLD,13);
	private Map<String, String> stringMap = new HashMap<String, String>();
	private String chunks[];
	private JMenuItem saveDataMI;
	private JMenuItem exitMI;


	public void setUpFrame(){
		print("[#] setting frame up...");
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(100, 100, 1000, 700);
		frame.setResizable(false);
		frame.setVisible(true);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		frame.setContentPane(contentPane);
	}
	
	public void finalSetup() {

		createMenuBar();
		createPanels();
		fillHomePanel();
		fillBooksPanel();
		fillLocationsPanel();
		fillLoansPanel();
	}
	
	public void createPanels() {

		booksPanel = new JPanel();
		booksPanel.setBackground(booksColor);
		booksPanel.setBounds(5, 50, 975, 795);
		contentPane.add(booksPanel);
		booksPanel.setLayout(null);
		booksPanel.setVisible(false);
		
		locationsPanel = new JPanel();
		locationsPanel.setBackground(locationsColor);
		locationsPanel.setBounds(5, 50, 975, 795);
		contentPane.add(locationsPanel);
		locationsPanel.setLayout(null);
		locationsPanel.setVisible(false);
		
		loansPanel = new JPanel();
		loansPanel.setBackground(loansColor);
		loansPanel.setBounds(5, 50, 975, 795);
		contentPane.add(loansPanel);
		loansPanel.setLayout(null);
		loansPanel.setVisible(false);


		homePanel = new JPanel();
		homePanel.setBounds(5, 50, 975, 795);
		contentPane.add(homePanel);
		homePanel.setLayout(null);
		homePanel.setVisible(true);


		// setting top row buttons
		booksB= new JButton(stringMap.get("books"));
		booksB.setBackground(Color.WHITE);
		booksB.setBounds(5, 5, 320, 40);
		contentPane.add(booksB);

		locationsB= new JButton(stringMap.get("locations"));
		locationsB.setBackground(Color.WHITE);
		locationsB.setBounds(330, 5,  320, 40);
		contentPane.add(locationsB);

		loansB = new JButton(stringMap.get("loans"));
		loansB.setBackground(Color.WHITE);
		loansB.setBounds(655, 5, 320, 40);
		contentPane.add(loansB);
	}
	
	public void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menuM = new JMenu(stringMap.get("menu"));
		menuBar.add(menuM);
		
		saveDataMI = new JMenuItem(stringMap.get("saveData"));
		menuM.add(saveDataMI);
		
		exitMI = new JMenuItem(stringMap.get("exit"));
		menuM.add(exitMI);
		
		JMenu tableM = new JMenu(stringMap.get("data"));
		menuBar.add(tableM);
		
		JMenuItem booksMI = new JMenuItem(stringMap.get("books"));
		booksMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!booksPanel.isVisible()) {
					loansPanel.setVisible(false);
					homePanel.setVisible(false);
					locationsPanel.setVisible(false);
					booksPanel.setVisible(true);
					
					booksB.setBackground(booksColor);
					locationsB.setBackground(Color.WHITE);
					loansB.setBackground(Color.WHITE);
				}
			}
		});
		tableM.add(booksMI);
		
		JMenuItem locationsMI = new JMenuItem(stringMap.get("locations"));
		locationsMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!locationsPanel.isVisible()) {
					booksPanel.setVisible(false);
					loansPanel.setVisible(false);
					homePanel.setVisible(false);
					locationsPanel.setVisible(true);
					
					booksB.setBackground(Color.WHITE);
					loansB.setBackground(Color.WHITE);
					locationsB.setBackground(locationsColor);
				}
			}
		});
		tableM.add(locationsMI);
		
		JMenuItem loansMI = new JMenuItem(stringMap.get("loans"));
		loansMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!loansPanel.isVisible()) {
					booksPanel.setVisible(false);
					locationsPanel.setVisible(false);
					homePanel.setVisible(false);
					loansPanel.setVisible(true);
					
					booksB.setBackground(Color.WHITE);
					locationsB.setBackground(Color.WHITE);
					loansB.setBackground(loansColor);
				}
			}
		});
		tableM.add(loansMI);
		
		JMenu helpM=new JMenu(stringMap.get("help"));
		menuBar.add(helpM);
		JMenuItem aboutMI=new JMenuItem(stringMap.get("about"));
		aboutMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame, stringMap.get("aboutInfo"), stringMap.get("aboutInforHeader"), JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		helpM.add(aboutMI);
	}
	
	public void fillHomePanel() {

		JLabel welcomeLabel = new JLabel(stringMap.get("welcomeLabel"));
		welcomeLabel.setBounds(200, 200, 600, 100);
		welcomeLabel.setFont(new Font("Dialog", Font.BOLD, 25));
		homePanel.add(welcomeLabel);

		JLabel booksTotalLabel = new JLabel(stringMap.get("booksTotalLabel")+all_books.size());
		booksTotalLabel.setBounds(200, 350, 150, 40);
		homePanel.add(booksTotalLabel);
		JLabel pendingLoansLabel = new JLabel(stringMap.get("pendingLoansLabel")+all_loans.size());
		pendingLoansLabel.setBounds(200, 400, 250, 40);
		homePanel.add(pendingLoansLabel);

	}
	
	public void setColumnWidths(JTable table, int widths[]) {
		TableColumnModel model = table.getColumnModel();
		if (model.getColumnCount() == widths.length) {
			for ( int i = 0; i< widths.length; i++ ) {
				model.getColumn(i).setMinWidth(25);
				model.getColumn(i).setMaxWidth(300);
				model.getColumn(i).setPreferredWidth(widths[i]);
			}
		}else {
			print("\n[!] Mismatch in Column-Count["+model.getColumnCount()+"] and Column-Widths["+widths.length+"] : "+String.valueOf(widths[1]));
		}
		table.setColumnModel(model);
	}
	
	// BOOKS
	public void fillBookModel(DefaultTableModel model, List<Book> list) {
		// set table info
		model.setRowCount(0);
		model.setColumnCount(bookFieldAlias.length);
		model.setColumnIdentifiers(bookFieldAlias);
		// populate samuelmovi.familyLibraryJava.model
		for(Book b:list) {
			Vector<String> vector = new Vector<String>();
			vector.add(String.valueOf(b.getIndex()));
			vector.add(b.getTitle());
			vector.add(b.getAuthor());
			vector.add(b.getGenre());
			vector.add(b.getPublisher());
			vector.add(b.getIsbn());
			vector.add(b.getPublishDate());
			vector.add(b.getPurchase_date());
			vector.add(String.valueOf(b.getLocation()));
			vector.add(String.valueOf(b.isLoaned()));
			vector.add(b.getRegistrationDate());
			model.addRow(vector);
		}
	}

	public void fillBookViewModel(DefaultTableModel model, List<BookView> list) {
		// set table info
		model.setRowCount(0);
		model.setColumnCount(bookJoinAliases.length);
		model.setColumnIdentifiers(bookJoinAliases);
		// populate samuelmovi.familyLibraryJava.model
		for(BookView b:list) {
			Vector<String> vector = new Vector<String>();
			// vector.add(String.valueOf(b.getBookview_index()));
			vector.add(b.getTitle());
			vector.add(b.getAuthor());
			vector.add(b.getGenre());
			vector.add(b.getPublisher());
			vector.add(b.getIsbn());
			vector.add(b.getPublish_date());
			vector.add(b.getPurchase_date());
			vector.add(b.getLocation());
			vector.add(String.valueOf(b.isLoaned()));
			vector.add(b.getRegistration_date());
			vector.add(b.getModification_date());
			model.addRow(vector);
		}
	}
	
	public void fillBooksPanel() {
		
		booksTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		booksTabbedPane.setBounds(12, 12, 950, 575);
		booksPanel.add(booksTabbedPane);
		
		bookLabelsText = stringMap.get("bookLabelsText").split(",");
		try {
			// fillBookModel(allBooksModel, all_books);
			fillBookViewModel(allBooksModel, allBookViews);
		}catch(Exception e) {
			
		}
		createAllBooksTab();
		createSearchBookTab();
		createAddBookTab();
		createModifyBookTab();
		createDeleteBookTab();
		
		try {
			setColumnWidths(allBooksTabTable, bookColumnWidths);
			setColumnWidths(deleteBookTabTable, bookColumnWidths);
			fillBookModel(modifyBooksModel, all_books);
			fillBookViewModel(allBooksModel, allBookViews);
			setColumnWidths( modifyBookTabTable, bookColumnWidths);
		}catch (Exception e) {
			JOptionPane.showMessageDialog(frame, stringMap.get("refreshBookTablesX")+" :\n"+e, stringMap.get("refreshBookTablesXHeader"), JOptionPane.ERROR_MESSAGE);
		    System.out.println(e.getMessage());
		}		
	}
	
	public void refreshBookTables() {
		try {
			// fillBookModel(allBooksModel, all_books);
			fillBookViewModel(allBooksModel, allBookViews);
			setColumnWidths(allBooksTabTable, bookColumnWidths);
			setColumnWidths(deleteBookTabTable, bookColumnWidths);
			setColumnWidths(makeLoanTabTable, bookColumnWidths);
			fillBookModel(modifyBooksModel, all_books);
			setColumnWidths( modifyBookTabTable, bookColumnWidths);
		}catch (Exception e) {
			JOptionPane.showMessageDialog(frame, stringMap.get("refreshBookTablesXHeader")+" :\n"+e, stringMap.get("refreshBookTablesXHeader"), JOptionPane.ERROR_MESSAGE);
		    System.out.println(e.getMessage());
		}
	}
	
	public void createAllBooksTab() {
		booksTabbedPane.addTab(stringMap.get("everything"), allBooksTab);
		allBooksTab.setLayout(null);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(12, 82, 920, 460);
		allBooksTab.add(scroll);
		
		allBooksTabTable = new JTable(allBooksModel);
		allBooksTabTable.setFont(mediumBoldFont);
		allBooksSorter=new TableRowSorter<TableModel>(allBooksModel);
		allBooksTabTable.setRowSorter(allBooksSorter);
		allBooksTabTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setColumnWidths(allBooksTabTable, bookColumnWidths);
		scroll.setViewportView(allBooksTabTable);
		
		JTableHeader header=allBooksTabTable.getTableHeader();
		header.setFont(mediumBoldFont);
		
		refreshAllBooksTabB=new JButton(stringMap.get("refresh"));
		refreshAllBooksTabB.setBounds(375,25,120,25);
		allBooksTab.add(refreshAllBooksTabB);
	}
	
	public void createSearchBookTab() {
		
		JPanel searchBooks = new JPanel();
		booksTabbedPane.addTab(stringMap.get("search"), searchBooks);
		searchBooks.setLayout(null);

		JScrollPane consultationScroll = new JScrollPane();
		consultationScroll.setBounds(12, 108, 920, 435);
		searchBooks.add(consultationScroll);
		
		searchBooksTabTable = new JTable(searchBooksModel);
		searchBooksSorter=new TableRowSorter<TableModel>(searchBooksModel);
		searchBooksTabTable.setRowSorter(searchBooksSorter);
		searchBooksTabTable.setFont(mediumBoldFont);
		consultationScroll.setViewportView(searchBooksTabTable);
		
		JTableHeader header=searchBooksTabTable.getTableHeader();
		header.setFont(mediumBoldFont);
		
		// String fieldList[]= null;//mod.loadFieldList();

		searchBookCombo=new JComboBox<String>();
		searchBookCombo.setBounds(120,35,150,25);
		searchBooks.add(searchBookCombo);
		searchBookCombo.setModel(new DefaultComboBoxModel<String>(searchFields));
		searchBookCombo.setSelectedItem(null);
		
		searchTerm = new JTextField();
		searchTerm.setBounds(325, 35, 250, 25);
		searchBooks.add(searchTerm);
		searchTerm.setColumns(10);
		
		searchBookB = new JButton(stringMap.get("search"));
		searchBookB.setBounds(650, 35, 117, 25);
		searchBooks.add(searchBookB);
	}
	
	public void createAddBookTab() {
		// NEW BOOK TAB
		JPanel addBookTab = new JPanel();
		booksTabbedPane.addTab(stringMap.get("new"), addBookTab);
		addBookTab.setLayout(null);
		
		JLabel addBookLabelObjects[]=new JLabel[bookLabelsText.length];
		int height=40;
		
		for(int i=0;i<bookLabelsText.length;i++) {
			addBookLabelObjects[i] = new JLabel(bookLabelsText[i]);
			addBookLabelObjects[i].setBounds(12, height, 250, 15);
			addBookTab.add(addBookLabelObjects[i]);
			height+=25;
		}
		
		addBookTextFields=new JTextField[bookLabelsText.length-1];
		height=40;
		
		for(int i=0;i<addBookTextFields.length;i++) {
			addBookTextFields[i]= new JTextField();
			addBookTextFields[i].setBounds(300, height, 302, 19);
			addBookTab.add(addBookTextFields[i]);
			height+=25;
		}
		
		comboLocations=new JComboBox<String>();
		comboLocations.setBounds(300,height,450,19);
		comboLocations.setModel(new DefaultComboBoxModel<String>(locationsArray));
		
		comboLocations.setSelectedItem(null);
		addBookTab.add(comboLocations);
		
		addBookB = new JButton(stringMap.get("add"));
		addBookB.setBounds(293, 400, 200, 25);
		addBookTab.add(addBookB);
		
		clearAddBookFieldsB = new JButton(stringMap.get("clear"));
		clearAddBookFieldsB.setBounds(293, 450, 200, 25);
		addBookTab.add(clearAddBookFieldsB);
	}
	
	@SuppressWarnings("unchecked")
	public void createModifyBookTab() {
		JPanel modifyBookTab = new JPanel();
		booksTabbedPane.addTab(stringMap.get("modify"), modifyBookTab);
		modifyBookTab.setLayout(null);
		
		JLabel modifyBookLabelObjects[]=new JLabel[bookLabelsText.length];
		
		int height=260;
		
		for(int i=0;i<bookLabelsText.length;i++) {
			modifyBookLabelObjects[i] = new JLabel(bookLabelsText[i]);
			modifyBookLabelObjects[i].setBounds(26, height, 200, 15);
			modifyBookTab.add(modifyBookLabelObjects[i]);
			height+=25;
		}
		
		height=260;

		for(int i=0;i<modifyBookInputObjects.length;i++) {
			if(modifyBookInputObjects[i] instanceof JTextField ) {
				JTextField textField=(JTextField)modifyBookInputObjects[i];
				textField.setBounds(300, height, 302, 19);
				modifyBookTab.add(textField);
				height+=25;
			}
			else if(modifyBookInputObjects[i] instanceof JComboBox) {
				modifyBookCB=new JComboBox<String>();
				modifyBookCB=(JComboBox<String>)modifyBookInputObjects[i];
				modifyBookCB.setBounds(300,435,450,19);
				modifyBookCB.setModel(new DefaultComboBoxModel<String>(locationsArray));
				
				modifyBookCB.setSelectedItem(null);
				modifyBookTab.add(modifyBookCB);
			}
		}
		JScrollPane modificationScroll = new JScrollPane();
		modificationScroll.setBounds(12, 12, 920, 223);
		modifyBookTab.add(modificationScroll);
		
		modifyBookTabTable = new JTable(modifyBooksModel);
		modifyBooksSorter=new TableRowSorter<TableModel>(modifyBooksModel);
		modifyBookTabTable.setFont(mediumBoldFont);
		modifyBookTabTable.setRowSorter(modifyBooksSorter);
		modifyBookTabTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setColumnWidths(modifyBookTabTable, bookColumnWidths);
		modificationScroll.setViewportView(modifyBookTabTable);		
		
		JTableHeader header=modifyBookTabTable.getTableHeader();
		header.setFont(mediumBoldFont);
		
		modifyBookB = new JButton(stringMap.get("modify"));
		modifyBookB.setBounds(750, 308, 117, 25);
		modifyBookTab.add(modifyBookB);
		
		resetModifyBookTabB = new JButton(stringMap.get("refresh"));
		resetModifyBookTabB.setBounds(750, 350, 117, 25);
		modifyBookTab.add(resetModifyBookTabB);
	}
	
	public void createDeleteBookTab() {
		booksTabbedPane.addTab(stringMap.get("delete"), deleteBooksTab);
		deleteBooksTab.setLayout(null);
		
		JScrollPane deleteScroll = new JScrollPane();
		deleteScroll.setBounds(12, 12, 920, 450);
		deleteBooksTab.add(deleteScroll);
		
		deleteBookTabTable = new JTable(allBooksModel);
		deleteBookTabTable.setFont(mediumBoldFont);
		deleteBooksSorter=new TableRowSorter<TableModel>(allBooksModel);
		deleteBookTabTable.setRowSorter(deleteBooksSorter);
		deleteBookTabTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setColumnWidths(deleteBookTabTable, bookColumnWidths);
		deleteScroll.setViewportView(deleteBookTabTable);
		
		JTableHeader header=deleteBookTabTable.getTableHeader();
		header.setFont(mediumBoldFont);
		
		deleteBookB = new JButton(stringMap.get("delete"));
		deleteBookB.setBounds(400, 500, 117, 25);
		deleteBooksTab.add(deleteBookB);
	}
	
	// LOCATIONS
	public void createAllLocationsTab(JPanel everythingTab, JTabbedPane tabbedPane,JTable table, DefaultTableModel model,TableRowSorter<TableModel> sorter, String alias, String tableName, int[] widths) {
		tabbedPane.addTab("Todos", everythingTab);
		everythingTab.setLayout(null);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(12, 82, 920, 460);
		everythingTab.add(scroll);
		
		refreshAllLocationsB = new JButton("Refrescar");
		refreshAllLocationsB.setBounds(375,25,120,25);
		everythingTab.add(refreshAllLocationsB);
		
		table.setFont(mediumBoldFont);
		sorter=new TableRowSorter<TableModel>(model);
		table.setRowSorter(sorter);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setColumnWidths(table, widths);
		scroll.setViewportView(table);
		
		JTableHeader header=table.getTableHeader();
		header.setFont(mediumBoldFont);
		
	}
	
	public void fillLocationModel(DefaultTableModel model, List<Location> list) {
		// set table info
		model.setRowCount(0);
		model.setColumnCount(locationFieldAlias.length);
		model.setColumnIdentifiers(locationFieldAlias);
		// populate samuelmovi.familyLibraryJava.model
		
		for(Location loc:list) {
			Vector<String> vector = new Vector<String>();
			vector.add(String.valueOf(loc.getIndex()));
			vector.add(loc.getAddress());
			vector.add(loc.getRoom());
			vector.add(loc.getFurniture());
			vector.add(loc.getDetails());
			model.addRow(vector);
		}	
	}
	
	public void fillLocationsPanel() {
		locationsTabbedPane=new JTabbedPane(JTabbedPane.TOP);
		locationsTabbedPane.setBounds(12, 12, 950, 575);
		locationsPanel.add(locationsTabbedPane);
		
		locationLabelsText = stringMap.get("locationLabelsText").split(",");
		
		try {
			fillLocationModel(allLocationsModel, all_locations);
			fillBookModel(availableBooksModel, availableBooks)	;
		}catch(Exception e) {
			
		}
		
		allLocationsTabTable = new JTable(allLocationsModel);		
		
		createAllLocationsTab(allLocationsTab, locationsTabbedPane, allLocationsTabTable, allLocationsModel, locationsSorter, stringMap.get("locations"), "locations", locationColumnWidths);
		createAddLocationTab();
		createModifyLocationTab();
		createDeleteLocationTab();
		
		refreshLocationTables();
		
	}
	
	public void refreshLocationTables() {
		try {
			fillLocationModel(allLocationsModel, all_locations);
			setColumnWidths( allLocationsTabTable, locationColumnWidths);
			setColumnWidths(modifyLocationTabTable, locationColumnWidths);
			setColumnWidths(deleteLocationTabTable, locationColumnWidths);
		}catch (Exception e){
			JOptionPane.showMessageDialog(frame, stringMap.get("refreshLocationTablesX")+" :\n"+e, stringMap.get("refreshLocationTablesXHeader"), JOptionPane.ERROR_MESSAGE);
	        System.out.println(e.getMessage());
		}
	}
	
	public void createAddLocationTab() {
		JPanel addLocationTab = new JPanel();
		locationsTabbedPane.addTab(stringMap.get("new"), addLocationTab);
		addLocationTab.setLayout(null);
		
		JLabel addLocationLabelObjects[]=new JLabel[locationLabelsText.length];
		int height=40;
		
		for(int i=0;i<locationLabelsText.length;i++) {
			addLocationLabelObjects[i] = new JLabel(locationLabelsText[i]);
			addLocationLabelObjects[i].setBounds(12, height, 135, 15);
			addLocationTab.add(addLocationLabelObjects[i]);
			height+=25;
		}
		
		height=40;
		
		for(int i=0;i<addLocationTextFields.length;i++) {
			addLocationTextFields[i]= new JTextField();
			addLocationTextFields[i].setBounds(208, height, 302, 19);
			addLocationTab.add(addLocationTextFields[i]);
			height+=25;
		}
		
		addLocationB = new JButton(stringMap.get("add"));
		addLocationB.setBounds(293, 395, 200, 25);
		addLocationTab.add(addLocationB);
		
		clearAddLocationFieldsB = new JButton(stringMap.get("clear"));
		clearAddLocationFieldsB.setBounds(293, 450, 200, 25);
		addLocationTab.add(clearAddLocationFieldsB);
	}
	
	public void createModifyLocationTab() {
		JPanel modifyLocationTab = new JPanel();
		locationsTabbedPane.addTab(stringMap.get("modify"), modifyLocationTab);
		modifyLocationTab.setLayout(null);
		JLabel modifyBookLabelObjects[]=new JLabel[4];
		int height=260;
		
		for(int i=0;i<locationLabelsText.length;i++) {
			modifyBookLabelObjects[i] = new JLabel(locationLabelsText[i]);
			modifyBookLabelObjects[i].setBounds(26, height, 135, 15);
			modifyLocationTab.add(modifyBookLabelObjects[i]);
			height+=25;
		}
		
		modifyLocationTextFields=new JTextField[4];
		height=260;
		
		for(int i=0;i<modifyLocationTextFields.length;i++) {
			modifyLocationTextFields[i]= new JTextField();
			modifyLocationTextFields[i].setBounds(215, height, 302, 19);
			 modifyLocationTab.add(modifyLocationTextFields[i]);
			height+=25;
		}
		
		JScrollPane modificationScroll = new JScrollPane();
		modificationScroll.setBounds(12, 12, 920, 223);
		modifyLocationTab.add(modificationScroll);
		
		modifyLocationTabTable = new JTable(allLocationsModel);
		locationsSorter=new TableRowSorter<TableModel>(allLocationsModel);
		modifyLocationTabTable.setRowSorter(locationsSorter);
		modifyLocationTabTable.setFont(mediumBoldFont);
		modifyLocationTabTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setColumnWidths(modifyLocationTabTable, locationColumnWidths);
		
		JTableHeader header=modifyLocationTabTable.getTableHeader();
		header.setFont(mediumBoldFont);
		
		modificationScroll.setViewportView(modifyLocationTabTable);
		
		modifyLocationB = new JButton(stringMap.get("modify"));
		modifyLocationB.setBounds(602, 308, 117, 25);
		modifyLocationTab.add(modifyLocationB);
		
		refreshModifyLocationFieldsB = new JButton(stringMap.get("refresh"));
		refreshModifyLocationFieldsB.setBounds(602, 375, 117, 25);
		modifyLocationTab.add(refreshModifyLocationFieldsB);
	}
	
	public void createDeleteLocationTab() {
		locationsTabbedPane.addTab(stringMap.get("delete"), deleteLocationTab);
		deleteLocationTab.setLayout(null);
		
		JScrollPane deleteScroll = new JScrollPane();
		deleteScroll.setBounds(12, 12, 920, 450);
		deleteLocationTab.add(deleteScroll);
		
		deleteLocationTabTable = new JTable(allLocationsModel);
		deleteLocationTabTable.setFont(mediumBoldFont);
		locationsSorter=new TableRowSorter<TableModel>(allLocationsModel);
		deleteLocationTabTable.setRowSorter(locationsSorter);
		deleteLocationTabTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setColumnWidths(deleteLocationTabTable, locationColumnWidths);
		deleteScroll.setViewportView(deleteLocationTabTable);
		
		JTableHeader header=deleteLocationTabTable.getTableHeader();
		header.setFont(mediumBoldFont);

		deleteLocationB = new JButton(stringMap.get("delete"));
		deleteLocationB.setBounds(400, 500, 117, 25);
		deleteLocationTab.add(deleteLocationB);
	}
	
	// LOANS
	public void fillLoanModel(DefaultTableModel model, List<Loan> list) {
		// set table info
		model.setRowCount(0);
		model.setColumnCount(loanFieldAlias.length);
		model.setColumnIdentifiers(loanFieldAlias);
		// populate samuelmovi.familyLibraryJava.model
		for(Loan loan:list) {
			Vector<String> vector = new Vector<String>();
			vector.add(String.valueOf(loan.getLoan_index()));
			vector.add(String.valueOf(loan.getBook()));
			vector.add(loan.getBorrower());
			vector.add(loan.getLoan_date());
			vector.add(loan.getReturn_date());
			model.addRow(vector);
		}
	}
	
	public void fillLoansPanel() {
    	loansTabbedPane=new JTabbedPane(JTabbedPane.TOP);
    	loansTabbedPane.setBounds(12, 12, 950, 575);
		loansPanel.add(loansTabbedPane);
		
		try {
			fillLoanModel(loansModel, all_loans);
			fillBookModel(availableBooksModel, availableBooks);			
		}catch(Exception e) {
			
		}
		
		allLoansTabTable = new JTable(loansModel);
		
		createAllLoansTab(allLoansTab, loansTabbedPane, allLoansTabTable, loansModel, allLoansSorter, stringMap.get("loans"), "loans", loansColumnWidths);
		createLoanBookTab();
		createReturnBookTab(loansColumnWidths);
		
		refreshLoanTables();
	}
	
	public void refreshLoanTables() {
		try {
			// fillJoinedModel(loansModel, loanJoinAliases, "loans", "books", "book", "books_index");
			fillLoanModel(loansModel, all_loans);
			setColumnWidths(allLoansTabTable, loansColumnWidths);
			setColumnWidths(returnLoanTabTable, loansColumnWidths);
		}catch (Exception e) {
			JOptionPane.showMessageDialog(frame, stringMap.get("refreshLoanTablesX")+" :\n"+e, stringMap.get("refreshLoanTablesXHeader"), JOptionPane.ERROR_MESSAGE);
		    System.out.println(e.getMessage());
		}
	}
	
	public void createAllLoansTab(JPanel everythingTab, JTabbedPane tabbedPane,JTable table, DefaultTableModel model,TableRowSorter<TableModel> sorter, String alias, String tableName, int widths[]) {
		tabbedPane.addTab(stringMap.get("everything"), everythingTab);
		everythingTab.setLayout(null);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(12, 82, 920, 460);
		everythingTab.add(scroll);
		
		table.setFont(mediumBoldFont);
		sorter=new TableRowSorter<TableModel>(model);
		table.setRowSorter(sorter);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setColumnWidths(table, widths);
		scroll.setViewportView(table);
		
		JTableHeader header=table.getTableHeader();
		header.setFont(mediumBoldFont);
		
		refreshLoanTablesB = new JButton(stringMap.get("refresh"));
		refreshLoanTablesB.setBounds(375,25,120,25);
		everythingTab.add(refreshLoanTablesB);
	}
	
	public void createLoanBookTab() {
		JPanel loanBookTab=new JPanel();
		loansTabbedPane.addTab(stringMap.get("loan"), loanBookTab);
		loanBookTab.setLayout(null);
		
		JScrollPane loanScroll = new JScrollPane();
		loanScroll.setBounds(12, 12, 920, 286);
		loanBookTab.add(loanScroll);
		
		makeLoanTabTable = new JTable(allBooksModel);
		loansSorter=new TableRowSorter<TableModel>(allBooksModel);
		makeLoanTabTable.setRowSorter(loansSorter);
		makeLoanTabTable.setFont(mediumBoldFont);
		makeLoanTabTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setColumnWidths(makeLoanTabTable, bookColumnWidths);
		loanScroll.setViewportView(makeLoanTabTable);		
		
		JTableHeader header=makeLoanTabTable.getTableHeader();
		header.setFont(mediumBoldFont);
		
		chunks = stringMap.get("loanLabelsText").split(",");
		
		JLabel loanedBookID=new JLabel(chunks[0]);
		loanedBookID.setBounds(200, 420, 150, 25);
		loanBookTab.add(loanedBookID);
		
		JLabel loanRecipient=new JLabel(chunks[1]);
		loanRecipient.setBounds(200, 450, 150, 25);
		loanBookTab.add(loanRecipient);
		
		loanBookTextFields=new JTextField[2];
		int height=420;
		for(int i=0;i<loanBookTextFields.length;i++) {
			loanBookTextFields[i]= new JTextField();
			loanBookTextFields[i].setBounds(300, height, 302, 25);
			loanBookTab.add(loanBookTextFields[i]);
			height+=30;
		}
		loanBookB = new JButton(stringMap.get("loan"));
		loanBookB.setBounds(325,500,120,25);
		loanBookTab.add(loanBookB);

	}
	
	public void createReturnBookTab(int widths[]) {
		JPanel returnBookTab=new JPanel();
		loansTabbedPane.addTab(stringMap.get("returns"),returnBookTab);
		returnBookTab.setLayout(null);
		
		JScrollPane deleteScroll = new JScrollPane();
		deleteScroll.setBounds(12, 12, 920, 286);
		returnBookTab.add(deleteScroll);
		
		returnLoanTabTable = new JTable(loansModel);
		loansSorter=new TableRowSorter<TableModel>(loansModel);
		returnLoanTabTable.setRowSorter(loansSorter);
		returnLoanTabTable.setFont(mediumBoldFont);
		returnLoanTabTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setColumnWidths(returnLoanTabTable, widths);
		
		JTableHeader header=returnLoanTabTable.getTableHeader();
		header.setFont(mediumBoldFont);
		
		deleteScroll.setViewportView(returnLoanTabTable);
		
		returnBookB = new JButton(stringMap.get("return"));
		returnBookB.setBounds(425, 400, 117, 25);
		returnBookTab.add(returnBookB);
	}
	
	//BOOKS setters and getters


	public List<BookView> getAllBookViews() {
		return allBookViews;
	}

	public void setAllBookViews(List<BookView> allBookViews) {
		this.allBookViews = allBookViews;
	}

	public List<Book> getAll_books() {
		return all_books;
	}

	public void setAll_books(List<Book> all_books) {
		this.all_books = all_books;
	}


	public String[] getBookJoinAliases() {
		return bookJoinAliases;
	}

	public void setBookJoinAliases(String[] bookJoinAliases) {
		this.bookJoinAliases = bookJoinAliases;
	}
	
	public JComboBox<String> getSearchBookCombo() {
		return searchBookCombo;
	}

	public JTable getDeleteBookTabTable() {
		return deleteBookTabTable;
	}
	
	public String[] getBookFieldAlias() {
		return bookFieldAlias;
	}

	public void setBookFieldAlias(String[] bookFieldAlias) {
		this.bookFieldAlias = bookFieldAlias;
	}
	
	public JTable getAllBooksTabTable() {
		return allBooksTabTable;
	}
	
	public int[] getBookColumnWidths() {
		return bookColumnWidths;
	}

	public JTextField[] getAddBookTextFields() {
		return addBookTextFields;
	}

	public Object[] getModifyBookInputObjects() {
		return modifyBookInputObjects;
	}
	
	public JComboBox<String> getModifyBookCB() {
		return modifyBookCB;
	}
	
	public JTable getModifyBookTabTable() {
		return modifyBookTabTable;
	}

	public JPanel getBooksPanel() {
		return booksPanel;
	}

	public void setBooksPanel(JPanel booksPanel) {
		this.booksPanel = booksPanel;
	}
	
	public JPanel getAllBooksTab() {
		return allBooksTab;
	}

	public void setAllBooksTab(JPanel allBooksTab) {
		this.allBooksTab = allBooksTab;
	}
	
	public JPanel getDeleteBooksTab() {
		return deleteBooksTab;
	}
	
	public void setDeleteBooksTab(JPanel deleteBooksTab) {
		this.deleteBooksTab = deleteBooksTab;
	}
	
	public JButton getBooksB() {
		return booksB;
	}
	
	public JButton getSearchBookB() {
		return searchBookB;
	}
	
	public JButton getAddBookB() {
		return addBookB;
	}
	
	public JButton getClearAddBookFieldsB() {
		return clearAddBookFieldsB;
	}
	
	public JButton getModifyBookB() {
		return modifyBookB;
	}
	
	public void clearModifyBookFields() {
		JTextField textField;
		for(int i=0;i<modifyBookInputObjects.length;i++) {
			if (getModifyBookInputObjects()[i] instanceof JTextField) {
				textField=(JTextField) getModifyBookInputObjects()[i];
				textField.setText("");
			}
			else if(modifyBookInputObjects[i] instanceof JComboBox) {
				// setLocationsArray(locations.locationsList());
				// setLocationsArray(locationsList);
				// modifyBookCB.setModel(new DefaultComboBoxModel<String>(locations.locationsList()));
				modifyBookCB.setSelectedItem(null);
			}
		}
	}
	
	public JButton getResetModifyBookTabB() {
		return resetModifyBookTabB;
	}
	
	public JButton getDeleteBookB() {
		return deleteBookB;
	}
	
	public DefaultTableModel getAllBooksModel() {
		return allBooksModel;
	}

	public DefaultTableModel getSearchBooksModel() {
		return searchBooksModel;
	}

	public DefaultTableModel getModifyBooksModel() {
		return modifyBooksModel;
	}
	
	public JButton getRefreshAllBooksTabB() {
		return refreshAllBooksTabB;
	}
	
	public String[] getSearchFields() {
		return searchFields;
	}

	public void setSearchFields(String[] searchFields) {
		this.searchFields = searchFields;
	}
	
	//LOCATIONS setters and getters
	public List<Location> getAll_locations() {
		return all_locations;
	}

	public void setAll_locations(List<Location> all_locations) {
		this.all_locations = all_locations;
	}

	public void setLocationAliases(String locationAliases) {
		this.locationAliases = locationAliases;
	}

	public String getLocationAliases() {
		return locationAliases;
	}

	public JButton getRefreshAllLocationsB() {
		return refreshAllLocationsB;
	}
	
	public JTable getAllLocationsTabTable() {
		return allLocationsTabTable;
	}
	
	public int[] getLocationColumnWidths() {
		return locationColumnWidths;
	}

	public JComboBox<String> getComboLocations() {
		return comboLocations;
	}
	
	public String[] getLocationsArray() {
		return locationsArray;
	}
	
	public void setLocationsArray(String[] locationsList) {
		this.locationsArray = locationsList;
	}
	
	public JTextField[] getAddLocationTextFields() {
		return addLocationTextFields;
	}
	
	public JTextField[] getModifyLocationTextFields() {
		return modifyLocationTextFields;
	}

	public JTable getModifyLocationTabTable() {
		return modifyLocationTabTable;
	}

	public JTable getDeleteLocationTabTable() {
		return deleteLocationTabTable;
	}

	public JTable getReturnLoanTabTable() {
		return returnLoanTabTable;
	}
	
	public JPanel getLocationsPanel() {
		return locationsPanel;
	}
	
	public void setLocationsPanel(JPanel locationsPanel) {
		this.locationsPanel = locationsPanel;
	}
	
	public JButton getModifyLocationB() {
		return modifyLocationB;
	}

	public JButton getRefreshModifyLocationFieldsB() {
		return refreshModifyLocationFieldsB;
	}

	public JButton getClearAddLocationFieldsB() {
		return clearAddLocationFieldsB;
	}
	
	public JButton getDeleteLocationB() {
		return deleteLocationB;
	}

	public JButton getLocationsB() {
		return locationsB;
	}
	
	public JButton getAddLocationB() {
		return addLocationB;
	}

	public DefaultTableModel getAllLocationsModel() {
		return allLocationsModel;
	}
	
	public String[] getLocationFieldAlias() {
		return locationFieldAlias;
	}

	public void setLocationFieldAlias(String[] locationFieldAlias) {
		this.locationFieldAlias = locationFieldAlias;
	}

	//LOANS setters and getters
	public List<Loan> getAll_loans() {
		return all_loans;
	}

	public void setAll_loans(List<Loan> all_loans) {
		this.all_loans = all_loans;
	}

	public JTable getMakeLoanTabTable() {
		return makeLoanTabTable;
	}

	public JTextField[] getLoanBookTextFields() {
		return loanBookTextFields;
	}

	public JPanel getLoansPanel() {
		return loansPanel;
	}
	
	public void setLoansPanel(JPanel loansPanel) {
		this.loansPanel = loansPanel;
	}
	
	public JButton getRefreshLoanTablesB() {
		return refreshLoanTablesB;
	}

	public JButton getReturnBookB() {
		return returnBookB;
	}

	public JButton getLoansB() {
		return loansB;
	}
	
	public JButton getLoanBookB() {
		return loanBookB;
	}
	
	public DefaultTableModel getLoansModel() {
		return loansModel;
	}
	
	public String getLoanJoinAliases() {
		return loanJoinAliases;
	}

	public void setLoanJoinAliases(String loanJoinAliases) {
		this.loanJoinAliases = loanJoinAliases;
	}

	public String[] getLoanFieldAlias() {
		return loanFieldAlias;
	}

	public void setLoanFieldAlias(String[] loanFieldAlias) {
		this.loanFieldAlias = loanFieldAlias;
	}

	// SYSTEM setters and getters	
	public JPanel getHomePanel() {
		return homePanel;
	}

	public void setHomePanel(JPanel homePanel) {
		this.homePanel = homePanel;
	}
	
	public JTextField getSearchTerm() {
		return searchTerm;
	}

	public JMenuItem getSaveDataMI() {
		return saveDataMI;
	}

	public JFrame getFrame() {
		return frame;
	}

	public JMenuItem getExitMI() {
		return exitMI;
	}

	public Map<String, String> getStringMap() {
		return stringMap;
	}

	public void setStringMap(Map<String, String> stringMap) {
		this.stringMap = stringMap;
	}
	
	static void print(String s) {
		System.out.print(s+"\n");
	}

	public DefaultTableModel getAvailableBooksModel() {
		return availableBooksModel;
	}

	public void setAvailableBooksModel(DefaultTableModel availableBooksModel) {
		this.availableBooksModel = availableBooksModel;
	}

	public List<Book> getAvailableBooks() {
		return availableBooks;
	}

	public void setAvailableBooks(List<Book> availableBooks) {
		this.availableBooks = availableBooks;
	}


}
