
package portserie;

/**
 * @author massino
 *
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Enumeration;

import dao.ConnectBDD;


public class SerialTest implements SerialPortEventListener {
	SerialPort serialPort;
        /** les ports a utiliser selon l OS. */
	private static final String PORT_NAMES[] = { 
			"/dev/tty.usbserial-A9007UX1", // Mac OS X
                        "/dev/ttyACM0", // Raspberry Pi
			"/dev/ttyUSB0", // Linux
			"COM3", // Windows
	};
	
	private BufferedReader input;
	private OutputStream output;
	//time out
	private static final int TIME_OUT = 2000;
	//calibrage des baults a 9600
	private static final int DATA_RATE = 9600;

	public void initialize() {
                // utiliser le port pour linux  ttyUSB0
                System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyUSB0");

		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		//rester en ecoute pour trouver le port .
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("port COM introuvavle ou non disponible.");
			return;
		}

		try {
			//ouvrir le port serie 
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// mettre les paramétres pour le port serie 
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 *fonction a appeler lors de la fin de la lecture pour 
	 *liberer le port 
	 *et réatribuer le verrou
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * lecture des données a partir du port serie 
	 * 
	 *ecriture sur la base de données 
	 *
	 *affichage des informations sur la console 
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine=input.readLine();
				double value = Double.valueOf(inputLine);
				/**
				 * connection et insertion dans la base de données 
				 * **/
				ConnectBDD connect=new ConnectBDD();
				connect.openConnection();
				connect.insert(value);
				connect.closeConnection();
				System.out.println(inputLine);
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		
	}
}
