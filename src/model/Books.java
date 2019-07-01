package model;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class Books {
	private SqliteClient db;
	private Validator val;
	private ResultSet results;
	private ResultSetMetaData resMeta;
	private ArrayList<String> textStrings;

	
	public Books(SqliteClient db,  Validator val, ArrayList<String> text) {
		this.db = db;
		this.val = val;
		textStrings = text;
	}
	
	public void fillModel(DefaultTableModel model,String fields, String tableName) {
		try {
			results=db.selectSome(fields, tableName);
			resMeta=results.getMetaData();
			int columnCount=resMeta.getColumnCount();
			String columnNames[]=new String[columnCount];
			for (int j=0;j<columnCount;j++) {
				columnNames[j]=resMeta.getColumnLabel(j+1);
			}			
			model.setRowCount(0);
			model.setColumnCount(columnCount);
			model.setColumnIdentifiers(columnNames);
     	    Object datos[]=new Object[columnCount];
            while (results.next()) {
               for (int j = 0; j < columnCount; j++) {
                    datos[j] = results.getObject(j+1);
	               }
               model.addRow(datos);     
            }
           
	     } catch (Exception e) {
			JOptionPane.showMessageDialog(null, textStrings.get(51)+" [fillModel]:\n"+e, textStrings.get(52), JOptionPane.ERROR_MESSAGE);
	        System.out.println(e.getMessage());
	     }
	}
	
	public void searchBooks(String field, String value, String alias, DefaultTableModel model) {
		try {
		//	String query="select books_index as 'ID',title as 'Titulo',author as 'Autor',editorial as 'Editorial', publish_date as 'Publicacion', purchase_date 'Compra',genre as'Genero',isbn as 'ISBN' from books where "+field+"='"+value+"';";
			String query="select "+alias+" from books where "+field+"='"+value+"';";
			System.out.println(query);
			results=db.executeQuery(query);
			model.setRowCount(0);
			resMeta=results.getMetaData();
			int columnCount=resMeta.getColumnCount();
			String columnNames[]=new String[columnCount];
			for (int i=0;i<columnCount;i++) {
				columnNames[i]=resMeta.getColumnLabel(i+1);
			}
			model.setColumnCount(columnCount);
			model.setColumnIdentifiers(columnNames);
	 	    Object datos[]=new Object[columnCount];
	        while (results.next()) {
	           for (int i = 0; i < columnCount; i++) {
	                datos[i] = results.getObject(i+1);
	               }
	           model.addRow(datos);     
	        }
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, textStrings.get(43)+" : "+e, textStrings.get(44), JOptionPane.ERROR_MESSAGE);
	        System.out.println(e.getMessage());
	     }
		
	}
	
	public boolean insertBook( JTextField textInput[], JComboBox<String> comboLocations) {
		String fields="title,author,genre,editorial,isbn,publish_date,purchase_date,location,registration_date";
		String values="";
		String text="";
		
		for(int i=0;i<textInput.length;i++) {
			text=textInput[i].getText();
			if(i==4) {
				if ( val.validateISBN(text)) {
					values+="'"+text+"',";
					textInput[i].setBackground(Color.WHITE);
				}else {
					textInput[i].setBackground(Color.RED);
					return false;
				}
				
			}
			else if(val.validateString(text)) {
				values+="'"+text+"',";
				textInput[i].setBackground(Color.WHITE);
			}
			else {
				textInput[i].setBackground(Color.RED);
				return false;
			}
		}
		values+=String.valueOf(comboLocations.getSelectedIndex()+1)+", ";
		values+="'"+LocalDate.now()+"'";
		db.insert(fields, values, "books");
		return true;
	}
	
	public boolean modifyBook(Object inputFields[], String columns[], String id) {
		String query="update books set ";
		String text="";
		JTextField textField;
		JComboBox<String> combo;
		int i;
		for(i=0;i<inputFields.length;i++) {
			if(inputFields[i] instanceof JTextField) {
				textField=(JTextField)inputFields[i];
				text=textField.getText();
				if(val.validateString(text)) {
					query+=columns[i]+"='"+text+"',";
					textField.setBackground(Color.WHITE);
				}else {
					textField.setBackground(Color.RED);
					return false;
				}
			}
			else if(inputFields[i] instanceof JComboBox) {
				combo=(JComboBox<String>)inputFields[i];
				query+="location ="+(combo.getSelectedIndex()+1)+", ";
			}
		}
		
		query+="modification_date='"+LocalDate.now()+"'";
		query+=" where books_index="+id+";";
		
		if(db.execute(query)) {
			return true;
		}else {
			return false;
		}
	}
	
	public String[] loadFieldList() {
		String fieldList[]=null;
		try {
			ResultSet resultados= db.selectSome("*", "books");
			ResultSetMetaData resMeta=resultados.getMetaData();
			ArrayList<String>array=new ArrayList<String>();
			int numeroColumnas=resMeta.getColumnCount();
			
			for(int i=1;i<=numeroColumnas;i++) {
				array.add(resMeta.getColumnName(i));
			}
			
			fieldList=new String[array.size()];
			for(int i=0;i<fieldList.length;i++) {
				fieldList[i]=array.get(i);
			}
		}catch(Exception e) {
			print("\n[!] Error with loadFieldList : "+e);
			
		}
		return fieldList;
	}
	
	public void deleteBook(String text, String tableName) {
		if(val.validateID(text)) {
			int reply=JOptionPane.showConfirmDialog(null,textStrings.get(45),textStrings.get(46),JOptionPane.YES_NO_OPTION);
			if(reply==JOptionPane.YES_OPTION) {
				deleteRow(tableName, text);
			}
		}else {
			JOptionPane.showMessageDialog(null, textStrings.get(47), textStrings.get(48), JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public boolean deleteRow(String tableName, String index) {
		String query="delete from "+tableName+" where "+tableName+"_index = "+index+";";
		if(db.execute(query)) return true;
		else return false;
	}
	
	static void print(String s) {
		System.out.print(s+"\n");
	}

}
