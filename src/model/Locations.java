package model;

import java.awt.Color;

import javax.swing.JTextField;

public class Locations {
	private SqliteClient db;
	private Validator val;
	private String fields = "address,room,furniture,details";
	
	public String getFields() {
		return fields;
	}

	public Locations(SqliteClient db, Validator val) {
		this.db = db;
		this.val = val;
	}
	
	public boolean insertLocation(JTextField inputFields[]) {
		
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
		values=values.substring(0,values.length()-1);
		db.insert(fields, values, "locations");
		return true;
		
	}
	
	public boolean modifyLocation(JTextField inputFields[], String locationFields[], String id) {
		String query="update locations set ";
		String text="";
		for(int i=0;i<inputFields.length;i++) {
			text=inputFields[i].getText();
			if(val.validateString(text)) {
				query+=locationFields[i]+"='"+text+"',";
				inputFields[i].setBackground(Color.WHITE);
			}else {
				inputFields[i].setBackground(Color.RED);
				return false;
			}
		}
		query=query.substring(0,query.length()-1);
		query+=" where locations_index="+id+";";
		if(db.execute(query)) {
			return true;
		}else {
			return false;
		}
	}
	
	static void print(String s) {
		System.out.print(s+"\n");
	}

}
