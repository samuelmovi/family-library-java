package model;

// import java.awt.Color;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
// import java.time.LocalDate;
import java.util.ArrayList;

// import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
// import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import model.SqliteClient;


public class Model {
	private SqliteClient db;

	private ResultSet results;
	private ResultSetMetaData resMeta;
	private ArrayList<String> textStrings;
	
	public Model(ArrayList<String> text, SqliteClient db) {
		print("[#] Starting Model...");
		this.db = db;
		textStrings = text;
	}
	
		
	//OTHER
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
	
	public void fillJoinedModel(DefaultTableModel model,String fields, String table1, String table2, String referenceField, String indexField) throws Exception{
		String query="select "+fields+" from "+table1+"  join "+table2+" on "+referenceField+"="+indexField;
		print("[*] Joined SQL query: "+query+"\n");
		try {
			results=db.executeQuery(query);
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
	    	 throw e;
	     }
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
	
	public void saveDbToCsv() {
		ArrayList<String> text = new ArrayList<String>();
		String holder="";
		String query = "";
		String tables = "";
		
		tables = "books,locations,loans";
		
		String[] tableNames = tables.split(",");
		for (int i=0; i < tableNames.length; i++) {
			query = "select * from " + tableNames[i];
			try {
				results = db.executeQuery(query);
				resMeta = results.getMetaData();
				int columnCount=resMeta.getColumnCount();
				for (int j=0;j<columnCount;j++) {
					holder += resMeta.getColumnLabel(j+1) + ",";
				}
				holder = holder.substring(0, holder.length()-1);
				text.add(holder);
				holder = "";
				
				while (results.next()) {
	               for (int j = 0; j < columnCount; j++) {
	            	   holder += results.getString(j+1) + ",";
	               }
	               holder = holder.substring(0, holder.length()-1);
	               text.add(holder);
	               holder = "";
	            }
				
				db.writeToCsv(text, tableNames[i]);
				text.clear();
			}catch(Exception e) {
				print("\n[!] Exception in save2csv: "+ e);
			}
		}
	}
	
	public String[] createLocationsList() {
		ArrayList<String> locationsArray=null;
		String locationsVector[]=null;
		try {
			results=db.selectSome("address,room,furniture,details", "locations");
			resMeta=results.getMetaData();
			String holder="";
			locationsArray=new ArrayList<String>();
			int rowCount=resMeta.getColumnCount();
			
			while(results.next()) {
				holder="";
				for(int i=1;i<=rowCount;i++) {
					holder+=results.getString(i);
					holder+=", ";
				}
				locationsArray.add(holder);
			}
			locationsVector=new String[locationsArray.size()];
			for(int i=0;i<locationsVector.length;i++) {
				locationsVector[i]=locationsArray.get(i);
			}
		}catch(Exception e) {
			print("\n[!] Error at createLocationList : "+e+"\n");
		}
		return locationsVector;
	}
	
	public boolean deleteRow(String tableName, String index) {
		String query="delete from "+tableName+" where "+tableName+"_index = "+index+";";
		if(db.execute(query)) return true;
		else return false;
	}
	
	public void closeProgram() {
		db.closeConnection();
	}
	
	static void print(String s) {
		System.out.print(s+"\n");
	}
}
