package main;

public class DBHBuilder {
    private String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    private String dbURL = "";
    
    private String user = "root";
    private String pass = "";
    
    public DBHBuilder(String dbURL) {
        this.dbURL = dbURL;
    }
    
    
    public DBHBuilder dbURL(String dbURL) {
        this.dbURL = dbURL;
        return this;
    }
    
    
    public DBHBuilder jdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
        return this;
    }
    
    
    public DBHBuilder user(String user) {
        this.user = user;
        return this;
    }
    
    
    public DBHBuilder pass(String pass) {
        this.pass = pass;
        return this;
    }
    
    public DBHandler buildHandler() {
        return new DBHandler(jdbcDriver, dbURL, user, pass);
    }
}

