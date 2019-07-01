package model;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Loans {
	private SqliteClient db;
	private Validator val;
	private ArrayList<String> textStrings;
	private String fields="book,recipient,loan_date ";


	
	public String getFields() {
		return fields;
	}

	public Loans(SqliteClient db,  Validator val, ArrayList<String> text) {
		this.db = db;
		this.val = val;
		textStrings = text;
	}
	
	public boolean loanThisBook(JTextField inputFields[]) {
		String values="";
		String text="";
		
		for(int i=0;i<inputFields.length;i++) {
			text=inputFields[i].getText().trim();
			if(val.validateString(text)) {
				values+="'"+text+"',";
				inputFields[i].setBackground(Color.WHITE);
			}
			else {
				inputFields[i].setBackground(Color.RED);
				return false;
			}
			
		}
		values+="'"+LocalDate.now()+"'";
		db.insert(fields, values, "loans");
		return true;
	}
	
	public void returnBook(String loanId, DefaultTableModel loansModel, String loansAliases) {
		if(val.validateID(loanId)) {
			String query="update loans set return_date='"+LocalDate.now()+"' where loans_index="+loanId;
			db.execute(query);	
			fillModel(loansModel,loansAliases,"loans");
		}else {
			JOptionPane.showMessageDialog(null, textStrings.get(49), textStrings.get(50), JOptionPane.ERROR_MESSAGE);
		}
		String query="update loans set return_date='"+LocalDate.now()+"' where loans_index="+loanId;
		db.execute(query);		
	}
	
	public void fillModel(DefaultTableModel model,String fields, String tableName) {
		try {
			ResultSet results=db.selectSome(fields, tableName);
			ResultSetMetaData resMeta=results.getMetaData();
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
	
	static void print(String s) {
		System.out.print(s+"\n");
	}
}
