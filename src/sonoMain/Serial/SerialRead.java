package sonoMain.Serial;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

public class SerialRead implements SerialPortEventListener {

    SerialPort serialPort;
    public static float umidade;
    public static float temperatura;
    public static boolean estado;//verifica se irá ligar ou não o programa
    public static boolean estadoThread;

    /**
     * The port we're normally going to use.
     */
    private static final String PORT_NAMES[] = {"//dev/tty.usbserial-A9007UX1", // Mac OS X
        "/dev/ttyACM0", // Linux
        "COM3", // Windows
};
    private BufferedReader input;
    private OutputStream output;
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;

    /**
     * *
     * Construtor inicia os valores lidas pelo serial
     */
    public SerialRead() {
        this.umidade = (float) 0.0;
        this.temperatura = (float) 0.0;
        this.estado = false;
        this.estadoThread = true;
    }

    public void initialize() {
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        //First, Find an instance of serial port as set in PORT_NAMES.
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
            System.out.println("Could not find COM port.");
            return;
        }

        try {
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            output = serialPort.getOutputStream();

            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    public synchronized void serialEvent(SerialPortEvent oEvent) {
        String vetor[] = new String[2];
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine = null;
                if (input.ready()) {
                    inputLine = input.readLine().trim();
                    if (input.ready()) {
                        inputLine = input.readLine().trim();
                        //iniciar o programa
                        if (inputLine.equals("iniciar")) {
                            estado = true;
                        } else if (inputLine.equals("desligar")) {
                            estado = false;
                        } else {
                            vetor = inputLine.split("%");
                            umidade = Float.parseFloat(vetor[0]);
                            temperatura = Float.parseFloat(vetor[1]);
                        }
                    }

                }
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
        // Ignore all the other eventTypes, but you should consider the other ones.
    }
    public static void serialStart() throws Exception {
        SerialRead main = new SerialRead();
        main.initialize();
        Thread t = new Thread() {
        
        @Override
        public void run() {
                while(estadoThread = true)
                {}
            }
        };
        t.start();
    }
}
