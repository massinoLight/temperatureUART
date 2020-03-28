package vues;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;


public class AffichageTempsReel {

	
	MaFenetreSwing mafenetre;
	  public static SwingWrapper<XYChart> sw;
	  public static XYChart chart;

	  

	  public void go() {

	    // Create Chart
	    chart = QuickChart.getChart("Variation de la temperature selon le temps", "Axe Temp", "Valeur température", "randomWalk", new double[] { 0 }, new double[] { 0 });
	    chart.getStyler().setLegendVisible(false);
	    chart.getStyler().setXAxisTicksVisible(false);

	    // Show it
	    sw = new SwingWrapper<XYChart>(chart);
	    sw.displayChart();

	    mafenetre = new MaFenetreSwing();
	    mafenetre.execute();
	  }

	  
	  public static void main(String[] args) throws Exception {

		    AffichageTempsReel affichageTR = new AffichageTempsReel();
		    affichageTR.go();
		  }
	
		
}
