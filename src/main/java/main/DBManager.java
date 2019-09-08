package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DBManager implements DBMInterface {    
    private String jdbcDriver;
    private String dbURL;
    
    private String user;
    private String pass;
    
    private Connection conn;
    private Statement stmt;
    
    private ResultSet queryResult; // Contains the result of the most recent query
    
    private boolean dBConnected; // Defaults to false
    private boolean silentExecution; // If print statements should be used to demo
    
    
    public DBManager(String jdbcDriver, String dbURL, String user, String pass) {
        this.jdbcDriver = jdbcDriver;
        this.dbURL = dbURL;
        this.user = user;
        this.pass = pass;
    }
    
    
    // Initialise connection to database, returns false if connection failed
    public boolean accessDB() {
        if(!this.dBConnected) {
	    	try {
	            Class.forName(this.jdbcDriver);
	            printStatus("Connecting to DB...");
	            this.conn = DriverManager.getConnection(this.dbURL, this.user, this.pass);
	            this.stmt = this.conn.createStatement(); // Setup statement
	            
	            this.dBConnected = true; // Database is connected!
	            return true;
	        } catch (ClassNotFoundException | SQLException e) {
	        	printStatus("DB Connection Failed...");
	            e.printStackTrace();
	            
	            this.dBConnected = false; // Database is not connected
	            return false;
	        }
    	} else {
    		printStatus("A DB is already connected...");
    		return false;
    	}
        
    }
    
    //Update the information on the database in some way
    public boolean update(String sql) {
        if(this.dBConnected) {
	    	try {
	            this.stmt.executeUpdate(sql);
	            printStatus("DB Updated...");
	            return true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            printStatus("DB Update Failed...");
	            return false;
	        }
    	} else {
    		printStatus("Please Use accessDB to Connect to a DB...");
    		return false;
    	}
    }
    
    // Attempts to query the database, if succeeded, stores within queryResult
    public boolean query(String sql){
        try {
			this.queryResult = this.stmt.executeQuery(sql);
			printStatus("Query Success...");
			return true;
		} catch (SQLException e) {
			//e.printStackTrace();
			printStatus("Query Failed...");
			return false;
		}
    }
    
    //Attempts to close a query, returns false if failed
    public boolean closeQuery() {
    	try {
			this.queryResult.close();
			printStatus("Query Closed...");
			return true;
		} catch (SQLException | NullPointerException e) {
			//e.printStackTrace();
			printStatus("Query Close Failed...");
			return false;
		}
    }
    
    public boolean closeDB() {
        if(this.dBConnected) {
	    	try {
	            this.conn.close();
	            printStatus("DB Disconnected...");
	            
	            this.dBConnected = false;
	            return true;
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            //e.printStackTrace();
	        	printStatus("DB Disconnect Failed...");
	            return false;
	        }
        } else {
        	printStatus("Not Currently Connected to a DB...");
        	return false;
        }
    }
    
    //Prints status to console provided silent execution is off (It is by default)
    protected void printStatus(String status) {
    	if(!this.silentExecution) {
    		System.out.println(status);
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
    
    //If true, no messages will print out when tasks execute
    public void setSilentExecution(boolean silentExecution) {
    	this.silentExecution = silentExecution;
    }
    
    //This feels like a bad idea...
    protected void setDBConnection(boolean dbConnected) {
    	this.dBConnected = dbConnected;
    }
    
    // getters
    public String getPass() {
        return this.pass;
    }
    
    public String getUser() {
        return this.user;
    }
    
    public String getJdbcDriver() {
        return this.jdbcDriver;
    }
    
    public String getDbURL() {
        return this.dbURL;
    }
    
    
    public ResultSet getQueryResult() {
    	return this.queryResult;
    }
    
    // Returns the current status of the DB connection true if connected
    public boolean isDBConnected() {
    	return this.dBConnected;
    }
    
    //Gets whether or not silent execution is enabled
    public boolean getSilentExecution(){
    	return this.silentExecution;
    }
    
    private void dBConnectionWarning() {
    	if(this.dBConnected) {
    		printStatus("Please note that any changes to DB connection information will not apply until the current connection is closed and a new accessDB request is given...");
    		printStatus("Alternatively, use refreshDBConnection to update the connection now...");
    	}
    }
    
    
}
