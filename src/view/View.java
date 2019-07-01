package view;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.Model;

public class View {
	
	private JPanel contentPane;
	
	// book stuff
	private JPanel booksPanel;
	private JPanel allBooksTab = new JPanel();
	private JPanel deleteBooksTab = new JPanel();
	private JButton booksB;
	private JButton searchBookB; 
	private JButton addBookB;
	private JButton clearAddBookFieldsB;
	private JButton modifyBookB;
	private JButton refreshModifyBookFieldsB;
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
	private String[] bookColumns = {"title", "author", "genre", "editorial", "isbn", "publish_date", "purchase_date", "location"};

	private String bookAliases="books_index as 'ID',title as 'Titulo',author as 'Autor',genre as'Genero',editorial as 'Editorial',isbn as 'ISBN', publish_date as 'Publicacion', purchase_date 'Adquisicion', location as 'Ubicacion', registration_date as 'Registrado', modification_date as 'Modificado' ";
	private String bookJoinAliases="books.books_index as 'ID',books.title as 'Titulo',books.author as 'Autor',books.genre as'Genero',books.editorial as 'Editorial',books.isbn as 'ISBN', books.publish_date as 'Publicacion', books.purchase_date 'Adquisicion', locations.address as 'Ubicacion', books.registration_date as 'Registrado', books.modification_date as 'Modificado'";
	private JComboBox<String> comboLocations;
	private JComboBox<String> modifyBookCB;

	//  location stuff
	private String[] locationsList;
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
	private JTextField addLocationTextFields[];
	private JTabbedPane locationsTabbedPane;
	private String locationLabelsText[];
	private JTextField modifyLocationTextFields[];
	private String locationFields[] = {"address","room","furniture","details"};
	private String locationAliases="locations_index as 'ID',address as 'Direccion', room as 'Habitacion', furniture as 'Mueble', details as 'Detalles'";

	// loans stuff
	private JPanel loansPanel;
	private JButton refreshLoanTablesB;
	private JButton returnBookB;
	private JPanel allLoansTab = new JPanel();
	private JButton loansB;
	private JButton loanBookB;	
	private DefaultTableModel loansModel=new DefaultTableModel();
	private JTable allLoansTabTable;
	private JTable makeLoanTabTable;
	private JTable returnLoanTabTable;
	private int loansColumnWidths[]= {27, 290, 250, 175, 175}; 
	private TableRowSorter<TableModel> loansSorter=new TableRowSorter<TableModel>();
	private TableRowSorter<TableModel> allLoansSorter=new TableRowSorter<TableModel>();
	private JTextField loanBookTextFields[];
	private JTabbedPane loansTabbedPane;

	// SYSTEM
	private JFrame frame;
	private JPanel homePanel;
	public Color booksColor=new Color(88,208,244);
	public Color locationsColor=new Color(255, 204, 102);
	public Color loansColor=new Color(51,234,185);
	private JTextField searchTerm;
	private Font mediumBoldFont=new Font(Font.SANS_SERIF, Font.BOLD,13);
	private ArrayList<String> textStrings = new ArrayList<String>();
	private String chunks[];
	private JMenuItem saveDataMI;
	private JMenuItem exitMI;
	private Model mod;


	public View(Model model, ArrayList<String> text) {
		print("[#] Starting View...");
		frame = new JFrame("window_title");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setBounds(100, 100, 1000, 700);
		frame.setResizable(false);
		frame.setVisible(true);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		frame.setContentPane(contentPane);
		textStrings = text;
		mod = model;
		
		locationsList=mod.createLocationsList();
		
		createMenuBar();
		createPanels();
		fillHomePanel();
		fillBooksPanel();
		fillLocationsPanel();
		fillLoansPanel();
	}
	
	public void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menuM = new JMenu(textStrings.get(0));
		menuBar.add(menuM);
		
		saveDataMI = new JMenuItem(textStrings.get(1));
		menuM.add(saveDataMI);
		
		exitMI = new JMenuItem(textStrings.get(2));
		menuM.add(exitMI);
		
		JMenu tableM = new JMenu(textStrings.get(3));
		menuBar.add(tableM);
		
		JMenuItem booksMI = new JMenuItem(textStrings.get(4));
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
		
		JMenuItem locationsMI = new JMenuItem(textStrings.get(5));
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
		
		JMenuItem loansMI = new JMenuItem(textStrings.get(6));
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
		
		JMenu helpM=new JMenu(textStrings.get(7));
		menuBar.add(helpM);
		JMenuItem aboutMI=new JMenuItem(textStrings.get(8));
		aboutMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame, textStrings.get(87), textStrings.get(88), JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		helpM.add(aboutMI);
	}
	
	public void createPanels() {
		
		homePanel = new JPanel();
		homePanel.setBounds(5, 50, 975, 795);
		contentPane.add(homePanel);
		homePanel.setLayout(null);
		homePanel.setVisible(true);
		
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
		
		
	}
	
	public void fillHomePanel() {
		booksB= new JButton(textStrings.get(9));
		locationsB= new JButton(textStrings.get(10));
		loansB = new JButton(textStrings.get(11));
		
		JLabel welcomeLabel = new JLabel(textStrings.get(12));
		welcomeLabel.setBounds(200, 200, 600, 100);
		welcomeLabel.setFont(new Font("Dialog", Font.BOLD, 25));
		homePanel.add(welcomeLabel);
		booksB.setBackground(Color.WHITE);
		booksB.setBounds(5, 5, 320, 40);
		contentPane.add(booksB);
		
		locationsB.setBackground(Color.WHITE);
		locationsB.setBounds(330, 5,  320, 40);
		contentPane.add(locationsB);
		
		loansB.setBackground(Color.WHITE);
		loansB.setBounds(655, 5, 320, 40);
		contentPane.add(loansB);
		
	}
	
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

	//GENERIC METHODS
	public void createEverythingTab(JPanel everythingTab, JTabbedPane tabbedPane,JTable table, DefaultTableModel model,TableRowSorter<TableModel> sorter, String alias, String tableName, int[] widths) {
		tabbedPane.addTab("Todos", everythingTab);
		everythingTab.setLayout(null);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(12, 82, 920, 460);
		everythingTab.add(scroll);
		
		JButton refreshB=new JButton("Refrescar");
		refreshB.setBounds(375,25,120,25);
		everythingTab.add(refreshB);
		
		table.setFont(mediumBoldFont);
		sorter=new TableRowSorter<TableModel>(model);
		table.setRowSorter(sorter);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mod.setColumnWidths(table, widths);
		scroll.setViewportView(table);
		
		JTableHeader header=table.getTableHeader();
		header.setFont(mediumBoldFont);
		
	}
	
	//BOOKS
	public JComboBox<String> getSearchBookCombo() {
		return searchBookCombo;
	}

	public JTable getDeleteBookTabTable() {
		return deleteBookTabTable;
	}

	public JTable getAllBooksTabTable() {
		return allBooksTabTable;
	}
	
	public int[] getBookColumnWidths() {
		return bookColumnWidths;
	}
	
	public String getBookAliases() {
		return bookAliases;
	}
	
	public JTextField[] getAddBookTextFields() {
		return addBookTextFields;
	}

	public Object[] getModifyBookInputObjects() {
		return modifyBookInputObjects;
	}
	
	public String[] getBookColumns() {
		return bookColumns;
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
	
	public JButton getRefreshModifyBookFieldsB() {
		return refreshModifyBookFieldsB;
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

	public void fillBooksPanel() {
		
		booksTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		booksTabbedPane.setBounds(12, 12, 950, 575);
		booksPanel.add(booksTabbedPane);
		
		bookLabelsText = textStrings.get(33).split(",");
		try {
			mod.fillJoinedModel(allBooksModel, bookJoinAliases, "books", "locations", "books.location", "locations.locations_index");
		}catch(Exception e) {
			
		}
		createAllBooksTab();
		createSearchBookTab();
		createAddBookTab();
		createModifyBookTab();
		createDeleteBookTab();
		
		try {
			mod.setColumnWidths(allBooksTabTable, bookColumnWidths);
			mod.setColumnWidths(deleteBookTabTable, bookColumnWidths);
			mod.fillModel(modifyBooksModel,bookAliases,"books");
			mod.setColumnWidths( modifyBookTabTable, bookColumnWidths);
		}catch (Exception e) {
			JOptionPane.showMessageDialog(frame, textStrings.get(37)+" :\n"+e, textStrings.get(38), JOptionPane.ERROR_MESSAGE);
		    System.out.println(e.getMessage());
		}		
	}
	
	public void refreshBookTables() {
		try {
			mod.fillJoinedModel(allBooksModel, bookJoinAliases, "books", "locations", "books.location", "locations.locations_index");
			mod.setColumnWidths(allBooksTabTable, bookColumnWidths);
			mod.setColumnWidths(deleteBookTabTable, bookColumnWidths);
			mod.setColumnWidths(makeLoanTabTable, bookColumnWidths);
			mod.fillModel(modifyBooksModel,bookAliases,"books");
			mod.setColumnWidths( modifyBookTabTable, bookColumnWidths);
		}catch (Exception e) {
			JOptionPane.showMessageDialog(frame, textStrings.get(37)+" :\n"+e, textStrings.get(38), JOptionPane.ERROR_MESSAGE);
		    System.out.println(e.getMessage());
		}
	}
	
	public void createAllBooksTab() {
		booksTabbedPane.addTab(textStrings.get(13), allBooksTab);
		allBooksTab.setLayout(null);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(12, 82, 920, 460);
		allBooksTab.add(scroll);
		
		allBooksTabTable = new JTable(allBooksModel);
		allBooksTabTable.setFont(mediumBoldFont);
		allBooksSorter=new TableRowSorter<TableModel>(allBooksModel);
		allBooksTabTable.setRowSorter(allBooksSorter);
		allBooksTabTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mod.setColumnWidths(allBooksTabTable, bookColumnWidths);
		scroll.setViewportView(allBooksTabTable);
		
		JTableHeader header=allBooksTabTable.getTableHeader();
		header.setFont(mediumBoldFont);
		
		refreshAllBooksTabB=new JButton(textStrings.get(20));
		refreshAllBooksTabB.setBounds(375,25,120,25);
		allBooksTab.add(refreshAllBooksTabB);
	}
	
	public void createSearchBookTab() {
		
		JPanel searchBooks = new JPanel();
		booksTabbedPane.addTab(textStrings.get(14), searchBooks);
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
		
		String fieldList[]=mod.loadFieldList();

		searchBookCombo=new JComboBox<String>();
		searchBookCombo.setBounds(120,35,150,25);
		searchBooks.add(searchBookCombo);
		searchBookCombo.setModel(new DefaultComboBoxModel<String>(fieldList));
		searchBookCombo.setSelectedItem(null);
		
		searchTerm = new JTextField();
		searchTerm.setBounds(325, 35, 250, 25);
		searchBooks.add(searchTerm);
		searchTerm.setColumns(10);
		
		searchBookB = new JButton(textStrings.get(14));
		searchBookB.setBounds(650, 35, 117, 25);
		searchBooks.add(searchBookB);
	}
	
	public void createAddBookTab() {
		// NEW BOOK TAB
		JPanel addBookTab = new JPanel();
		booksTabbedPane.addTab(textStrings.get(15), addBookTab);
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
		comboLocations.setModel(new DefaultComboBoxModel<String>(locationsList));
		comboLocations.setSelectedItem(null);
		addBookTab.add(comboLocations);
		
		addBookB = new JButton(textStrings.get(22));
		addBookB.setBounds(293, 400, 200, 25);
		addBookTab.add(addBookB);
		
		clearAddBookFieldsB = new JButton(textStrings.get(23));
		clearAddBookFieldsB.setBounds(293, 450, 200, 25);
		addBookTab.add(clearAddBookFieldsB);
	}

	@SuppressWarnings("unchecked")
	public void createModifyBookTab() {
		JPanel modifyBookTab = new JPanel();
		booksTabbedPane.addTab(textStrings.get(16), modifyBookTab);
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
		JTextField textField=new JTextField();
		
		for(int i=0;i<modifyBookInputObjects.length;i++) {
			if(modifyBookInputObjects[i] instanceof JTextField ) {
				textField=(JTextField)modifyBookInputObjects[i];
				textField.setBounds(300, height, 302, 19);
				modifyBookTab.add(textField);
				height+=25;
			}
			else if(modifyBookInputObjects[i] instanceof JComboBox) {
				modifyBookCB=new JComboBox<String>();
				modifyBookCB=(JComboBox<String>)modifyBookInputObjects[i];
				modifyBookCB.setBounds(300,435,450,19);
				modifyBookCB.setModel(new DefaultComboBoxModel<String>(locationsList));
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
		mod.setColumnWidths(modifyBookTabTable, bookColumnWidths);
		modificationScroll.setViewportView(modifyBookTabTable);		
		
		JTableHeader header=modifyBookTabTable.getTableHeader();
		header.setFont(mediumBoldFont);
		
		modifyBookB = new JButton(textStrings.get(24));
		modifyBookB.setBounds(750, 308, 117, 25);
		modifyBookTab.add(modifyBookB);
		
		refreshModifyBookFieldsB = new JButton(textStrings.get(20));
		refreshModifyBookFieldsB.setBounds(750, 350, 117, 25);
		modifyBookTab.add(refreshModifyBookFieldsB);
	}
	
	public void createDeleteBookTab() {
		booksTabbedPane.addTab(textStrings.get(17), deleteBooksTab);
		deleteBooksTab.setLayout(null);
		
		JScrollPane deleteScroll = new JScrollPane();
		deleteScroll.setBounds(12, 12, 920, 450);
		deleteBooksTab.add(deleteScroll);
		
		deleteBookTabTable = new JTable(allBooksModel);
		deleteBookTabTable.setFont(mediumBoldFont);
		deleteBooksSorter=new TableRowSorter<TableModel>(allBooksModel);
		deleteBookTabTable.setRowSorter(deleteBooksSorter);
		deleteBookTabTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mod.setColumnWidths(deleteBookTabTable, bookColumnWidths);
		deleteScroll.setViewportView(deleteBookTabTable);
		
		JTableHeader header=deleteBookTabTable.getTableHeader();
		header.setFont(mediumBoldFont);
		
		deleteBookB = new JButton(textStrings.get(25));
		deleteBookB.setBounds(400, 500, 117, 25);
		deleteBooksTab.add(deleteBookB);
	}
	
	//LOCATIONS
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

	public String[] getLocationsList() {
		return locationsList;
	}

	public JTextField[] getAddLocationTextFields() {
		return addLocationTextFields;
	}
	
	public String[] getLocationFields() {
		return locationFields;
	}

	public JTextField[] getModifyLocationTextFields() {
		return modifyLocationTextFields;
	}

	public void setLocationsList(String[] locationsList) {
		this.locationsList = locationsList;
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
		mod.setColumnWidths(table, widths);
		scroll.setViewportView(table);
		
		JTableHeader header=table.getTableHeader();
		header.setFont(mediumBoldFont);
		
	}
	
	public void fillLocationsPanel() {
		locationsTabbedPane=new JTabbedPane(JTabbedPane.TOP);
		locationsTabbedPane.setBounds(12, 12, 950, 575);
		locationsPanel.add(locationsTabbedPane);
		
		locationLabelsText = textStrings.get(34).split(",");
		
		try {
			mod.fillModel(allLocationsModel, locationAliases, "locations");
		}catch(Exception e) {
			
		}
		
		allLocationsTabTable = new JTable(allLocationsModel);		
		
		createAllLocationsTab(allLocationsTab, locationsTabbedPane, allLocationsTabTable, allLocationsModel, locationsSorter, textStrings.get(30), "locations", locationColumnWidths);
		createAddLocationTab();
		createModifyLocationTab();
		createDeleteLocationTab();
		
		refreshLocationTables();
		
	}
	
	public void refreshLocationTables() {
		try {
			mod.fillModel(allLocationsModel, locationAliases, "locations");
			mod.setColumnWidths( allLocationsTabTable, locationColumnWidths);
			mod.setColumnWidths(modifyLocationTabTable, locationColumnWidths);
			mod.setColumnWidths(deleteLocationTabTable, locationColumnWidths);
		}catch (Exception e){
			JOptionPane.showMessageDialog(frame, textStrings.get(39)+" :\n"+e, textStrings.get(40), JOptionPane.ERROR_MESSAGE);
	        System.out.println(e.getMessage());
		}
	}
	
	public void createAddLocationTab() {
		JPanel addLocationTab = new JPanel();
		locationsTabbedPane.addTab(textStrings.get(15), addLocationTab);
		addLocationTab.setLayout(null);
		
		JLabel addLocationLabelObjects[]=new JLabel[locationLabelsText.length];
		int height=40;
		
		for(int i=0;i<locationLabelsText.length;i++) {
			addLocationLabelObjects[i] = new JLabel(locationLabelsText[i]);
			addLocationLabelObjects[i].setBounds(12, height, 135, 15);
			addLocationTab.add(addLocationLabelObjects[i]);
			height+=25;
		}
		
		addLocationTextFields=new JTextField[4];
		height=40;
		
		for(int i=0;i<addLocationTextFields.length;i++) {
			addLocationTextFields[i]= new JTextField();
			addLocationTextFields[i].setBounds(208, height, 302, 19);
			addLocationTab.add(addLocationTextFields[i]);
			height+=25;
		}
		
		addLocationB = new JButton(textStrings.get(22));
		addLocationB.setBounds(293, 395, 200, 25);
		addLocationTab.add(addLocationB);
		
		clearAddLocationFieldsB = new JButton(textStrings.get(23));
		clearAddLocationFieldsB.setBounds(293, 450, 200, 25);
		addLocationTab.add(clearAddLocationFieldsB);
	}
	
	public void createModifyLocationTab() {
		JPanel modifyLocationTab = new JPanel();
		locationsTabbedPane.addTab(textStrings.get(16), modifyLocationTab);
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
		mod.setColumnWidths(modifyLocationTabTable, locationColumnWidths);
		
		JTableHeader header=modifyLocationTabTable.getTableHeader();
		header.setFont(mediumBoldFont);
		
		modificationScroll.setViewportView(modifyLocationTabTable);
		
		modifyLocationB = new JButton(textStrings.get(24));
		modifyLocationB.setBounds(602, 308, 117, 25);
		modifyLocationTab.add(modifyLocationB);
		
		refreshModifyLocationFieldsB = new JButton(textStrings.get(20));
		refreshModifyLocationFieldsB.setBounds(602, 375, 117, 25);
		modifyLocationTab.add(refreshModifyLocationFieldsB);
	}
	
	public void createDeleteLocationTab() {
		locationsTabbedPane.addTab(textStrings.get(17), deleteLocationTab);
		deleteLocationTab.setLayout(null);
		
		JScrollPane deleteScroll = new JScrollPane();
		deleteScroll.setBounds(12, 12, 920, 450);
		deleteLocationTab.add(deleteScroll);
		
		deleteLocationTabTable = new JTable(allLocationsModel);
		deleteLocationTabTable.setFont(mediumBoldFont);
		locationsSorter=new TableRowSorter<TableModel>(allLocationsModel);
		deleteLocationTabTable.setRowSorter(locationsSorter);
		deleteLocationTabTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mod.setColumnWidths(deleteLocationTabTable, locationColumnWidths);
		deleteScroll.setViewportView(deleteLocationTabTable);
		
		JTableHeader header=deleteLocationTabTable.getTableHeader();
		header.setFont(mediumBoldFont);

		deleteLocationB = new JButton(textStrings.get(25));
		deleteLocationB.setBounds(400, 500, 117, 25);
		deleteLocationTab.add(deleteLocationB);
	}
	
	//LOANS
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
	
	public void fillLoansPanel() {
    	loansTabbedPane=new JTabbedPane(JTabbedPane.TOP);
    	loansTabbedPane.setBounds(12, 12, 950, 575);
		loansPanel.add(loansTabbedPane);
		
		try {
			mod.fillJoinedModel(loansModel, textStrings.get(32), "loans", "books", "book", "books_index");
		}catch(Exception e) {
			
		}
		
		allLoansTabTable = new JTable(loansModel);
		
		createAllLoansTab(allLoansTab, loansTabbedPane, allLoansTabTable, loansModel, allLoansSorter, textStrings.get(32), "loans", loansColumnWidths);
		createLoanBookTab();
		createReturnBookTab(loansColumnWidths);
		
		refreshLoanTables();
	}
	
	public void refreshLoanTables() {
		try {
			mod.fillJoinedModel(loansModel, textStrings.get(32), "loans", "books", "book", "books_index");
			mod.setColumnWidths(allLoansTabTable, loansColumnWidths);
			mod.setColumnWidths(returnLoanTabTable, loansColumnWidths);
		}catch (Exception e) {
			JOptionPane.showMessageDialog(frame, textStrings.get(41)+" :\n"+e, textStrings.get(42), JOptionPane.ERROR_MESSAGE);
		    System.out.println(e.getMessage());
		}
	}
	
	public void createAllLoansTab(JPanel everythingTab, JTabbedPane tabbedPane,JTable table, DefaultTableModel model,TableRowSorter<TableModel> sorter, String alias, String tableName, int widths[]) {
		tabbedPane.addTab(textStrings.get(13), everythingTab);
		everythingTab.setLayout(null);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(12, 82, 920, 460);
		everythingTab.add(scroll);
		
		table.setFont(mediumBoldFont);
		sorter=new TableRowSorter<TableModel>(model);
		table.setRowSorter(sorter);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mod.setColumnWidths(table, widths);
		scroll.setViewportView(table);
		
		JTableHeader header=table.getTableHeader();
		header.setFont(mediumBoldFont);
		
		refreshLoanTablesB = new JButton(textStrings.get(20));
		refreshLoanTablesB.setBounds(375,25,120,25);
		everythingTab.add(refreshLoanTablesB);
	}
	
	public void createLoanBookTab() {
		JPanel loanBookTab=new JPanel();
		loansTabbedPane.addTab(textStrings.get(18), loanBookTab);
		loanBookTab.setLayout(null);
		
		JScrollPane loanScroll = new JScrollPane();
		loanScroll.setBounds(12, 12, 920, 286);
		loanBookTab.add(loanScroll);
		
		makeLoanTabTable = new JTable(allBooksModel);
		loansSorter=new TableRowSorter<TableModel>(allBooksModel);
		makeLoanTabTable.setRowSorter(loansSorter);
		makeLoanTabTable.setFont(mediumBoldFont);
		makeLoanTabTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mod.setColumnWidths(makeLoanTabTable, bookColumnWidths);
		loanScroll.setViewportView(makeLoanTabTable);		
		
		JTableHeader header=makeLoanTabTable.getTableHeader();
		header.setFont(mediumBoldFont);
		
		chunks = textStrings.get(35).split(",");
		
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
		loanBookB = new JButton(textStrings.get(26));
		loanBookB.setBounds(325,500,120,25);
		loanBookTab.add(loanBookB);

	}
	
	public void createReturnBookTab(int widths[]) {
		JPanel returnBookTab=new JPanel();
		loansTabbedPane.addTab(textStrings.get(19),returnBookTab);
		returnBookTab.setLayout(null);
		
		JScrollPane deleteScroll = new JScrollPane();
		deleteScroll.setBounds(12, 12, 920, 286);
		returnBookTab.add(deleteScroll);
		
		returnLoanTabTable = new JTable(loansModel);
		loansSorter=new TableRowSorter<TableModel>(loansModel);
		returnLoanTabTable.setRowSorter(loansSorter);
		returnLoanTabTable.setFont(mediumBoldFont);
		returnLoanTabTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mod.setColumnWidths(returnLoanTabTable, widths);
		
		JTableHeader header=returnLoanTabTable.getTableHeader();
		header.setFont(mediumBoldFont);
		
		deleteScroll.setViewportView(returnLoanTabTable);
		
		returnBookB = new JButton(textStrings.get(27));
		returnBookB.setBounds(425, 400, 117, 25);
		returnBookTab.add(returnBookB);

	}
		
	static void print(String s) {
		System.out.print(s+"\n");
	}
}
