import java.io.IOException;
import java.net.UnknownHostException;
import TADK.*;

public class exampleBM {
    /*
    *   IP address of TADK
    */
    public final static String ip = "192.168.0.102";
    /*
    *   TCP port number of TADK
    */
    public final static int port = 9761;    
    
    /**
     * @param args[] None
     * @throws UnknownHostException
     * @throws IOException
     * @throws FailedToConfigure
     * @throws FailedToDecode
     */
    public static void main(String args[]) throws UnknownHostException, IOException, FailedToConfigure, FailedToDecode
	{ 
        //Create an object of BM and connect to TADK through tcp on the given ip and port and configure TADK as BM
        BusMonitor bm = new BusMonitor(ip, port);
        bm.setTrap(13, 1, 1, 7);
        bm.enableBM();
        Thread rtRecieveThread = new Thread(()->{
            System.out.println("Recieve Thread Started");
            while(true){
                DeviceData recData;
				try {
                    recData = bm.readDeviceData();
                    System.out.println("Recieved [" + recData.subAddress+ "] :"+recData.response);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }, "RTReciever");
        rtRecieveThread.start();
    }
}
