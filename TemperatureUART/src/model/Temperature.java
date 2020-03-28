package model;


import java.sql.Timestamp;

public class Temperature {
	private int id;
	private double valeur;
	private Timestamp moment;
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public double getValeur() {
		return valeur;
	}


	public void setValeur(double valeur) {
		this.valeur = valeur;
	}


	public Timestamp getMoment() {
		return moment;
	}


	public void setMoment(Timestamp moment) {
		this.moment = moment;
	}


	public Temperature(int i,double val,Timestamp mom){
		this.id=i;
		this.valeur=val;
		this.moment=mom;
		
	}

}
