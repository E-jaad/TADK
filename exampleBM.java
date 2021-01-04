import java.io.IOException;
import java.net.UnknownHostException;
import TADK.*;
/**
 * This is an example of Bus Monitor. the following actions are performed in this example.
 * 1. Connect to TADK through TCP
 * 2. Configure TADK as BM.
 * 3. Set trap on BM
 * 4. Enable BM 
 * 5. strat thread for reading the messages
 */
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
        System.out.println("Connected to " + ip + ":" + port);
        //Before setting traps, we have to disable BM if previously enabled.
        bm.disableBM();
        //Set trap for messages with RT:13, SA:1, TXRX: BC->RT, Word Count: 7
        bm.setTrap(13, 1, DeviceConstants.TADK_BC_TO_RT, 7);
        //Enable BM to start reading
        bm.enableBM();
        Thread rtRecieveThread = new Thread(()->{
            System.out.println("Recieve Thread Started");
            while(true){
                DeviceData recData;
				try {
                    recData = bm.readDeviceData();
                    System.out.println("Recieved [" + recData.subAddress+ "] :"+recData.response);
                    System.out.println("Data[0]: " + recData.data[0]);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }, "RTReciever");
        rtRecieveThread.start();
    }
}
