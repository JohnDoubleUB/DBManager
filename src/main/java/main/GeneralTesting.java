package main;


//Just for out of class testing!
public class GeneralTesting {
	
	private static String[][] table1Fields = {
			{"id INT PRIMARY KEY AUTO_INCREMENT"},
			{"name VARCHAR(50) NOT NULL"},
			{"VARCHAR(20) NOT NULL", "race", "class"},
			{"VARCHAR(30) NOT NULL", "alignment", "background"},
			{"INT(3) NOT NULL", "level", "base_str", "base_dex", "base_con", "base_int", "base_wis", "base_cha", "base_hp", "proficiency"},
			{"BOOLEAN NOT NULL", "prof_acro", "prof_ahan", "prof_arca", "prof_athl", "prof_decp", "prof_hist", "prof_insi", "prof_itmd", 
				"prof_invs", "prof_medi", "prof_natr", "prof_perp", "prof_perf", "prof_pers", "prof_reli", "prof_slei", "prof_slth", 
				"prof_surv", "svgthr_str", "svgthr_dex", "svgthr_con", "svgthr_int", "svgthr_wis", "svgthr_cha"},
			{"VARCHAR(15000)", "other_profs_and_languages", "features_and_traits"}
	};
	
	
	public static void main(String[] args) {
		//String[][] sets = {{"INT(3) NOT NULL", "Strength", "Dex", "Endurance"}, {"BOOLEAN NOT NULL", "acrobatics", "animalhandling", "whatever"}, {"id INT PRIMARY KEY AUTO_INCREMENT"}};
		
		System.out.println(test1("characters", table1Fields));
		
		
	}
	
	
	public static String test1(String tableName, String[]... sets) {
		String result = "";
		String opener = "";
		
		for(int i = 0; i < sets.length; i++) {
			
			String[] currentSet = sets[i];
			
			//If the set only contains a single item!
			if(currentSet.length < 2) {
				result += opener + currentSet[0];
				opener = ", ";
			} else { // If the set contains multiple items
				//The first item is the properties for each item after it!
				for(int j = 1; j < currentSet.length; j++) {					
					String currentItem = currentSet[j];
					result += opener + currentItem + " " + currentSet[0];
					opener = ", ";
				}
			}
		}
		
		result = "CREATE TABLE " + tableName + "(" + result + ");";
		return result;
		
	}
}
