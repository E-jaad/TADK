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
    public final static String ip = "192.168.1.104";
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
                    switch(recData.response){
                        case DeviceConstants.TADK_RESPONSE_SUCCESSFULL_RX_BC_RT:
                        //BC->RT command  executed successfully. 
                        //System.out.println("Status: " + recData.status); //Check RT Status
                        //Print Data Words
                        // for(int index=0;index<recData.data.length;index++){
                        //     System.out.println("DataWord[" + index + "]: "+recData.data[index]);
                        // }
                        break;
                        case DeviceConstants.TADK_RESPONSE_SUCCESSFULL_TX_RT_BC:
                        //RT->BC command  executed successfully.
                        break;
                        case DeviceConstants.TADK_RESPONSE_SUCCESSFULL_RX_BOCAT:
                        //Broadcast command  executed successfully.
                        break;
                        case DeviceConstants.TADK_RESPONSE_CONFIG_SUCCESS:
                        //TADK configured successfully.
                        break;
                        case DeviceConstants.TADK_RESPONSE_SUCCESSFULL_MODE_RT_BC:
                        //Modecode command executed successfully.
                        break;
                        case DeviceConstants.TADK_RESPONSE_FAILED_RX_BC_RT:
                        //BC->RT command timedout. RT Failed to respond. Check if RT is turned on,configure to the right address, and Bus is rightly connected.
                        break;
                        case DeviceConstants.TADK_RESPONSE_FAILED_TX_RT_BC:
                        //RT->BC command timedout. RT Failed to respond. Check if RT is turned on,configure to the right address, and Bus is rightly connected.
                        break;
                        case DeviceConstants.TADK_RESPONSE_CONFIG_FAILED:
                        // Configuration command failed, check if the configuration message was constructed right.
                        break;
                        case DeviceConstants.TADK_RESPONSE_FAILED_MODE_RT_BC:
                        // Modecode command timedout. RT Failed to respond. Check if RT is turned on,configure to the right address, and Bus is rightly connected.
                        break;
                        case DeviceConstants.TADK_RESPONSE_GEN_ERROR:
                        // check if the message was constructed right.
                        break;
                        default:
                        break;
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }, "RTReciever");
        rtRecieveThread.start();
    }
}
