package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager implements DBMInterface {
    //private String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://localhost:3306";
    //private String dbURL = "jdbc:mysql://34.76.250.24:3306/TestDatabase?useSSL=false";
    
    private String jdbcDriver;
    private String dbURL;
    
    private String user;
    private String pass;
    
    private Connection conn;
    private Statement stmt;
    
    private ResultSet queryResult; // Contains the result of the most recent query
    
    private boolean dBConnected; // Defaults to false
    
    
    public DBManager(String jdbcDriver, String dbURL, String user, String pass) {
        this.jdbcDriver = jdbcDriver;
        this.dbURL = dbURL;
        this.user = user;
        this.pass = pass;
    }
    
    
    // Initialise connection to database, returns false if connection failed
    public boolean accessDB() {
        if(!dBConnected) {
	    	try {
	            Class.forName(jdbcDriver);
	            System.out.println("Connecting to DB...");
	            conn = DriverManager.getConnection(dbURL, user, pass);
	            stmt = conn.createStatement(); // Setup statement
	            
	            dBConnected = true; // Database is connected!
	            return true;
	        } catch (ClassNotFoundException | SQLException e) {
	            System.out.println("DB Connection Failed...");
	            e.printStackTrace();
	            
	            dBConnected = false; // Database is not connected
	            return false;
	        }
    	} else {
    		System.out.println("A DB is already connected...");
    		return false;
    	}
        
    }
    
    //Attempts to close and then reestablish a connection to the database
    public boolean refreshDBConnection() {
    	if(dBConnected) {
    		if(closeDB()) {
    	   		return accessDB();
    		} else {
    			return false;
    		}
    	} else {
    		System.out.println("No DB to Refresh Connection With...");
    		return false;
    	}
    	
    }
    
    //Update the information on the database in some way
    public boolean update(String sql) {
        if(dBConnected) {
	    	try {
	            stmt.executeUpdate(sql);
	            System.out.println("DB Updated...");
	            return true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("DB Update Failed...");
	            return false;
	        }
    	} else {
    		System.out.println("Please Use accessDB to Connect to a DB...");
    		return false;
    	}
    }
    
    // Attempts to query the database, if succeeded, stores within queryResult
    public boolean query(String sql){
        try {
			queryResult = stmt.executeQuery(sql);
			System.out.println("Query Success...");
			return true;
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("Query Failed...");
			return false;
		}
    }
    
    //Attempts to close a query, returns false if failed
    public boolean closeQuery() {
    	try {
			queryResult.close();
			System.out.println("Query Closed...");
			return true;
		} catch (SQLException | NullPointerException e) {
			//e.printStackTrace();
			System.out.println("Query Close Failed...");
			return false;
		}
    }
    
    public boolean closeDB() {
        if(dBConnected) {
	    	try {
	            conn.close();
	            System.out.println("DB Disconnected...");
	            
	            dBConnected = false;
	            return true;
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            //e.printStackTrace();
	        	System.out.println("DB Disconnect Failed...");
	            return false;
	        }
        } else {
        	System.out.println("Not Currently Connected to a DB...");
        	return false;
        }
    }
    
    
    //Setters
    public void setPass(String pass) {
    	this.pass = pass;
    	dBConnectionWarning();
    }
    
    public void setUser(String user) {
        this.user = user;
        dBConnectionWarning();
    }
    
    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
        dBConnectionWarning();
    }
    
    public void setDbURL(String dbURL) {
        this.dbURL = dbURL;
        dBConnectionWarning();
    }
    
    // getters
    public String getPass() {
        return pass;
    }
    
    public String getUser() {
        return user;
    }
    
    public String getJdbcDriver() {
        return jdbcDriver;
    }
    
    public String getDbURL() {
        return dbURL;
    }
    
    
    public ResultSet getQueryResult() {
    	return queryResult;
    }
    
    // Returns the current status of the DB connection true if connected
    public boolean isDBConnected() {
    	return dBConnected;
    }
    
    public void setDBConnection(boolean dbConnected) {
    	this.dBConnected = dbConnected;
    }
    
    private void dBConnectionWarning() {
    	if(dBConnected) {
    		System.out.println("Please note that any changes to DB connection information will not apply until the current connection is closed and a new accessDB request is given...");
    		System.out.println("Alternatively, use refreshDBConnection to update the connection now...");
    	}
    }
    
}
