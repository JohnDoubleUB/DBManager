package main;

import java.util.Random;
import java.sql.SQLException;

//import java.sql.ResultSet;

public class AppImplementation {
	private static String dbName = "dnd_db";
	private static String table1 = "character_stats";
	private static String[][] table1Fields = {
			{"id INT PRIMARY KEY AUTO_INCREMENT"},
			
			{"name VARCHAR(50) NOT NULL"},
			
			{"VARCHAR(20) NOT NULL", "race", "class"},
			
			{"VARCHAR(30) NOT NULL", "alignment", "background"},
			
			{"INT(3) NOT NULL", "level", "base_str", "base_dex", "base_con", "base_int", "base_wis", "base_cha", "base_hp", "proficiency"},
			
			{"VARCHAR(8000)", "other_profs_and_languages", "features_and_traits"}
	};
	
	private static String table2 = "character_gear";
	private static String[][] table2Fields = {
			{"id INT PRIMARY KEY AUTO_INCREMENT"},
			
			{"INT(3) NOT NULL", "copper_piece", "silver_piece", "gold_piece"},
			
			{"platinum_piece INT(6) NOT NULL"},
			
			{"equipment VARCHAR(15000)"}
	};
	
	
	private static String table3 = "character_proficiencies";
	private static String[][] table3Fields = {
			{"id INT PRIMARY KEY AUTO_INCREMENT"},
			
			{"BOOLEAN NOT NULL", "prof_acro", "prof_ahan", "prof_arca", "prof_athl", "prof_decp", "prof_hist", "prof_insi", "prof_itmd", 
				"prof_invs", "prof_medi", "prof_natr", "prof_perp", "prof_perf", "prof_pers", "prof_reli", "prof_slei", "prof_slth", 
				"prof_surv", "svgthr_str", "svgthr_dex", "svgthr_con", "svgthr_int", "svgthr_wis", "svgthr_cha"}
	};
	
	
	public static void main(String[] args) {
		//DBHandler db = new DBHBuilder("jdbc:mysql://34.76.250.24:3306/").pass("sqljenkinscombo").buildHandler(); //This one is for the sql db I set up
		DBHandler db = new DBHBuilder("jdbc:mysql://localhost:3307").buildHandler(); // This is for local tests!
		//ResultSet result;
		
		// Provided connection is successful
		if(db.accessDB()) {
			System.out.println("Database connection sucessful!");
			
			
			db.update("DROP DATABASE IF EXISTS " + dbName + ";");
			db.update("CREATE DATABASE " + dbName + ";");
			db.update("USE " + dbName + ";");
			
			// Create the first table!
			
			System.out.println("Here we go");
			
			db.createNewTable(table1, table1Fields);
			
			db.createNewTable(table2, table2Fields);
			
			db.createNewTable(table3, table3Fields);
			
			
			String variables = "";
			String variables2 = "";
			
			//add a record into table1
			
			for(int i = 1; i<table1Fields[4].length;i++) {
				variables+=", "+table1Fields[4][i];
				variables2+=", "+randomNo();
			}
			
			System.out.println(variables2);
			System.out.println(variables);
			
			if(db.update("INSERT INTO " + table1 + "(name, race, class, alignment, background" + variables +", other_profs_and_languages, features_and_traits)" + " VALUES('Socrowtes', 'Monk', 'Kenku', 'Chaotic Neutral', 'Criminal'" + variables2 + ", 'He speaks bird, can mimic', 'Likes shiney things!');" )) {
				System.out.println("one table1 entry done!");
			}
			
			
			//add some information to table2
			
			if(db.update("INSERT INTO " + table2 + "(copper_piece, silver_piece, gold_piece, platinum_piece)" + " VALUES(2,4,5,90);")) {
				System.out.println("one table2 entry done!");
				
				//db.closeQuery();
				
				if(db.query("SELECT * FROM " + table2)) {
					System.out.println("Information pulled from " + table2 + " successfully!");
					
					try {
						while(db.getQueryResult().next()) {
							int id = db.getQueryResult().getInt("id");
							int cp = db.getQueryResult().getInt("copper_piece");
							int sp = db.getQueryResult().getInt("silver_piece");
							int gp = db.getQueryResult().getInt("gold_piece");
							int pp = db.getQueryResult().getInt("platinum_piece");
							
							System.out.println("id: " + id + ", cp: " + cp + ", sp: " + sp + ", gp: " + gp + ", pp: " + pp);
							
						}
						
						db.closeQuery();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
			}
			
			
			
			//add a record into table3
			
			variables = table3Fields[1][1];
			variables2 = Integer.toString(randomZeroOne());
			for(int i = 2; i<table3Fields[1].length; i++ ) {
				variables+=", "+table3Fields[1][i];
				variables2+=", "+Integer.toString(randomZeroOne());
			}
			variables = "(" + variables + ")";
			variables2 = "(" + variables2 + ");";
			//System.out.println(variables);
			//System.out.println(variables2);
			if(db.update("INSERT INTO " + table3 + variables + " VALUES" + variables2)) {
				System.out.println("one table3 entry done!");
				}
			
			
			db.closeDB();
		}
		
	}
	
	
	static int randomZeroOne() {
		return (Math.random()<0.5)?0:1;
	}
	
	
	static int randomNo() {
		int test = new Random().nextInt(60);
		if(test<10) { test = 10; };
		return test;
	}
}
