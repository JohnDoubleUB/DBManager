package main;

public interface DBMInterface {	
	
	public boolean accessDB();
	public boolean closeDB();
	
	public boolean update(String sql);
	public boolean query(String sql);
	public boolean closeQuery();
}
