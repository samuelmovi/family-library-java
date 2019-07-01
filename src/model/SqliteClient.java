package model;

import java.sql.Statement;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SqliteClient {
	
	private String dbName="LibraryData.sqlite";
	private Connection conn=null;
	private boolean success=false;
	private ArrayList<String> sqlCommands=new ArrayList<String>();
	private String sqlFilePath = "commands.sql";
	
	public SqliteClient() {
		try {
			conn=DriverManager.getConnection("jdbc:sqlite:"+dbName);
			initializeDB();
		}catch(SQLException e) {
			print("\n[!] Error during database operation: "+e);
		}catch(Exception e) {
			print("\n[!] Error : "+e);
		}
	}
	

	public ResultSet selectJoin(String fields,String tableName, String refTable,String fK, String refField) {
		String query="select "+fields+" from "+tableName+" inner join "+refTable+" on "+tableName+"."+fK+"="+refTable+"."+refField;
		ResultSet results=null;
		try {
			Statement stmnt=conn.createStatement();
			results=stmnt.executeQuery(query);
		}catch(SQLException e) {
			print("\n[!] Error during database operation: "+e);
		}catch(Exception e) {
			print("\n[!] Error : "+e);
		}
		return results;
	}
	
	// EXECUTES THE REQUIRED SQL STATEMENTS FOR A FULLY FUNCTIONING DB
	public void readSqlCommands() {
		String text;
		String command="";
		
		try(BufferedReader br=new BufferedReader(new FileReader(sqlFilePath))) {
			text = br.readLine().trim();
			while(text != null) {
				command += text;
				if(text.endsWith(";")) {
					sqlCommands.add(command);
					command="";
				}
				text= br.readLine();
			} 
		}catch(EOFException eofe) {
			print("\n[!] Lectura de archivo completada\n");
		}catch(IOException ioe) {
			print("\n[!] I/O error: "+ioe);
		}catch(Exception e) {
			print("\n[!] Error: "+e);
		}
	}
	
	public void initializeDB() {
		readSqlCommands();
		for(int i = 0; i < sqlCommands.size(); i++) {
			update(sqlCommands.get(i));
		}
	}
	
	// TAKES A FULLY FORMED SELECT SQL QUERY AND RETURNS THE RESULTS (OR NULL)
	public ResultSet executeQuery(String query) {
		print("[#] executeQuery(): "+query);
		ResultSet results=null;
		try {
			Statement stmnt=conn.createStatement();
			results=stmnt.executeQuery(query);
			print("[$] Query correctly executed");
		}catch(SQLException e) {
			print("\n[!] Error during executeQuery() : "+e);
		}catch(Exception e) {
			print("\n[!] Error : "+e);
		}
		return results;
		
	}
	
	// TAKES TABLE NAME, THE NAME OF THE FIELDS AND ITS VALUES AND INSERTS THEM
	public void insert(String fields, String values, String table) {
		String query="insert into "+table+" ("+fields+") values ("+values+");";
		print("[#] insert(): "+query);
		try{
			Statement stmnt=conn.createStatement();
			stmnt.execute(query);
			print("[$] Query correctly executed");
		}catch(SQLException e) {
			print("\n[!] Error during insert() : "+e);
		}catch(Exception e) {
			print("\n[!] Error : "+e);
		}
		
	}
	
	public void update(String query) {
		print("[#] update(): "+ query);
		try {
			Statement stmnt=conn.createStatement();
			stmnt.executeUpdate(query);
			print("[$] Query correctly executed");
		}catch(SQLException e) {
			print("\n[!] Error during update() : "+e);
		}catch(Exception e) {
			print("\n[!] Error : "+e);
		}
	}
	
	public boolean execute(String query) {
		print("\n[#] execute: "+query);
		success=false;
		try {
			Statement stmnt=conn.createStatement();
			stmnt.executeUpdate(query);
			success=true;
			print("[$] Query correctly executed");
		}catch(SQLException e) {
			print("\n[!] Error during execute() : "+e);
		}catch(Exception e) {
			print("\n[!] Error : "+e);
		}
		return success;
	}
	
	public ResultSet selectSome(String fields, String table) {
		String query="select "+fields+" from "+table+";";
		print("[#] selectSome(): "+query);
		ResultSet results=null;
		try {
			Statement stmnt=conn.createStatement();
			results=stmnt.executeQuery(query);
			print("[$] Query correctly executed");
		}catch(SQLException e) {
			print("\n[!] Error during selectSome() : "+e);
		}catch(Exception e) {
			print("\n[!] Error : "+e);
		}
		return results;
	}
	
	// TAKES A TABLE NAME AND RETURNS RESULTS FOR SELECT *
	public ResultSet selectAll(String table) {
		String query="select * from "+table+";";
		print("[#] selectAll(): "+ query);
		ResultSet results=null;
		try {
			Statement stmnt=conn.createStatement();
			results=stmnt.executeQuery(query);
			print("[$] Query correctly executed");
		}catch(SQLException e) {
			print("\n[!] Error during selectAll() : "+e);
		}catch(Exception e) {
			print("\n[!] Error : "+e);
		}
		return results;
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
	
	public void closeConnection() {
		try {
			if(!conn.isClosed()) {
				conn.close();
			}
		}catch (Exception e) {
			print("\n[!] Exception attempting to close DB connection: "+ e);
		}
		
	}

	static void print(String s) {
		System.out.print(s+"\n");
	}
}
