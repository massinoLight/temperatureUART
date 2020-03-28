
import portserie.SerialTest;
import vues.AffichageTempsReel;


public class Main {

	public static void main(String[] args) throws Exception {

		
			SerialTest main = new SerialTest();
			main.initialize();
			Thread t=new Thread() {
				public void run() {
					
					try {Thread.sleep(1000000);
					}
					catch (InterruptedException ie) {}
				}
			};
			t.start();
			System.out.println("Started");	
	    AffichageTempsReel affichageTR = new AffichageTempsReel();
	    affichageTR.go();
	    
	  }

}

