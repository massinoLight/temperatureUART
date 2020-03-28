package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Temperature;


public class ConnectBDD implements ConnexionJDBC {

	public String url = "jdbc:mysql://localhost/temperatureArduino";
    public  String user = "root";
    public String passwd = ""; 
    public Connection connexion;
	
    //on ouvre un connection a notre base de données
	@Override
	public void openConnection() {
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		  connexion = DriverManager.getConnection(url, user, passwd);  
		    System.out.println("Connexion effectuée !");         
		  
		  } catch (Exception e) {
		    e.printStackTrace();
		  } 
		
	}

	//on retourn la connection courente 
	@Override
	public Connection getConnection() {
		openConnection();
		return this.connexion;
	}

	//fermeture de la connection avec gestion des exeptions 
	@Override
	public void closeConnection() {
		try {
			this.connexion.close();
			System.out.println("connection férmé");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void insert(double valeur) {
        String sql = "INSERT INTO `TEMPERATURE`(`VALEUR`, `TEMPS`) VALUES (?,NOW())";
 
        try (Connection conn = this.connexion;
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, valeur);                      
            pstmt.executeUpdate();
            System.out.println("valeur inséré à la BDD");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
 
	
	
	public  ArrayList<Temperature> getValeurs() throws ClassNotFoundException, SQLException {
		Connection conn = this.connexion;
		Statement stm;
	    stm = conn.createStatement();
	    String sql = "SELECT * FROM TEMPERATURE WHERE 1";
	    ResultSet rst;
	    rst = stm.executeQuery(sql);
	    ArrayList<Temperature> customerList = new ArrayList<>();
	    while (rst.next()) {
	    	Temperature customer = new Temperature(rst.getInt("ID"), rst.getDouble("VALEUR"), rst.getTimestamp("TEMPS"));
	        customerList.add(customer);
	    }
	    return customerList;
	}
	
	public static void main(String[] args) throws Exception {
		 ArrayList<Temperature> lestemperatures = new ArrayList<>();
		ConnectBDD connect=new ConnectBDD();
		connect.openConnection();
		lestemperatures=connect.getValeurs();
		// Affichage à l'aide d'une boucle forEach
	       for(Temperature elem: lestemperatures)
	       {
	       	 System.out.println (elem.getId()+" "+elem.getValeur()+" "+elem.getMoment());
	       }
		connect.closeConnection();
	}
	
	
}
