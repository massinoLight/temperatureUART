package vues;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.SwingWorker;
import dao.ConnectBDD;
import model.Temperature;

public class MaFenetreSwing extends SwingWorker<Boolean, double[]>  {
	
	LinkedList<Double> fifo = new LinkedList<Double>();
    ArrayList<Temperature> lestemperatures = new ArrayList<>();
		ConnectBDD connect=new ConnectBDD();
				
		/***
		 * la methode a redefinir pour l'execution en arriére plan
		 * ici recupérer les valeurs de la liste qui représente les températures 
		 * recup uniquement des tranches de 500 valeurs et les affichers sur le graphe 
		 * avec la linkedList FIFO
		 * ****/
	@Override
	protected Boolean doInBackground() throws Exception {
		int id=2;
	      while (!isCancelled()) {

	    	  fifo.add(lestemperatures.get(id).getValeur());
	    	  System.out.println(lestemperatures.get(id).getValeur());
	    	  id++;
	        if (fifo.size() > 30) {
	          fifo.removeFirst();
	        }

	        double[] array = new double[fifo.size()];
	        for (int i = 0; i < fifo.size(); i++) {
	          array[i] = fifo.get(i);
	        }
	        
	        //passer les valeurs comme paramétre a la fonction process qui sera redéfinit par la suite 
	        publish(array);

	        try {
	          Thread.sleep(100);
	        } catch (InterruptedException e) {
	          System.out.println("Fenetre fermé.");
	        }

	      }

	      return true;
	}
	
	/***
	 * Constructeur qui va se connecter et recupérer les valeurs de la bdd
	 * il sera appéllé par la classe AffchageTempsReel
	 * 
	 * ****/
	
	public MaFenetreSwing() {
		
		connect.openConnection();
		try {
		lestemperatures=connect.getValeurs();
	} catch (ClassNotFoundException | SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

      fifo.add(lestemperatures.get(1).getValeur());
		
	}
	
	/****
	 * redéfinition de la fonction 
	 * qui prendra en paramétre les valeurs a mettre sur le graphe
	 * 
	 * ***/
	
	
	  @Override
	    protected void process(List<double[]> chunks) {	      
	      double[] mostRecentDataSet = chunks.get(chunks.size() - 1);
	      AffichageTempsReel.chart.updateXYSeries("randomWalk", null, mostRecentDataSet, null);
	      AffichageTempsReel.sw.repaintChart();

	      long start = System.currentTimeMillis();
	      long duration = System.currentTimeMillis() - start;
	      try {
	        Thread.sleep(400 - duration); // 40 ms ==> 25fps
	        // Thread.sleep(400 - duration); // 40 ms ==> 2.5fps
	      } catch (InterruptedException e) {
	      }

	    }
		

}
