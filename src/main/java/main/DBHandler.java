package main;


//Handles more complex tasks in addition to that of DBManager
public class DBHandler extends DBManager{
	
	
	public DBHandler(String jdbcDriver, String dbURL, String user, String pass) {
		super(jdbcDriver, dbURL, user, pass);
	}
	
	
	//Makes forced false by default
	public boolean createNewTable(String tableName, String[]... sets) {
		return createNewTable(false, tableName, sets);
	}
	
	// Each set should have the instruction property as the first item, if there is only one item in a set
	// the item will be added in its entirety as a table field, forced will ensure that if an existing
	// table shares the given tableName, it will first be removed! This command will make the table
	// in whichever database is currently being used
	public boolean createNewTable(boolean forced, String tableName, String[]... sets) {
		
		// Ensure there is a working connection to a database
		if(isDBConnected()) {
			String result = "";
			String opener = "";
			
			for(int i = 0; i < sets.length; i++) {
				
				String[] currentSet = sets[i];
				
				//If the set only contains a single item!
				if(currentSet.length < 2) {
					result += opener + currentSet[0];
					opener = ",";
					
				} else { // If the set contains multiple items
					
					//The first item is the properties for each item after it!
					for(int j = 1; j < currentSet.length; j++) {					
						String currentItem = currentSet[j];
						result += opener + currentItem + " " + currentSet[0];
						opener = ",";
						
					}
				}
			}
			
			//At this point the update command is fully formed!
			result = "CREATE TABLE " + tableName + "(" + result + ");";
			
			// Remove existing table with the given table name (If it exists)
			if(forced) {
				update("DROP TABLE IF EXISTS " + tableName + ";");
			}
			
			return update(result);
			
		} else {
			System.out.println("Please Use accessDB to Connect to a DB...");
			return false;
		}
	}
	

}
