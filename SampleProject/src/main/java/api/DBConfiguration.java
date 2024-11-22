package api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConfiguration {	
	private static Connection con = null;
	
	public void getDBConnection(){
		try {
			String dbUrl = "DB_URL";					
			String username = "DB_USR";		
			String password = "DB_PWD";				
			String dbDriver = "DB_DRIVER";	

			Class.forName(dbDriver);	
			//Create Connection to DB		
			con = DriverManager.getConnection(dbUrl,username,password);
			System.out.println("Connected to DB");
		}catch(Exception e) {
			System.err.println("Failed to connect or execute in DB due to exception" + e.getMessage());
		}
	}

	public ResultSet executeQuery(String query) {
		ResultSet rs = null;
		try {
			//Set Schema given in run property file
			String schema = "ALTER SESSION SET CURRENT_SCHEMA = "+"schemaName";
			//Create Statement Object		
			//Statement stmt = con.createStatement();
			Statement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs= stmt.executeQuery(schema);
			// Execute the SQL Query. Store results in ResultSet		
			rs= stmt.executeQuery(query);
		}catch(Exception e) {

		}
		return rs;
	}

	public void closeDB() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}
