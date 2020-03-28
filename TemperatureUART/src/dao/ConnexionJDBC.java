package dao;
import java.sql.Connection;

public interface ConnexionJDBC {
	
	
	public void openConnection() ;
	
	public Connection getConnection();
	
	public void closeConnection();

}
