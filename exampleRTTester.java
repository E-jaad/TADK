import java.io.IOException;
import java.net.UnknownHostException;

import TADK.*;
public class exampleRTTester {
    /*
    *   IP address of TADK
    */
    public final static String ip = "192.168.1.105";
    /*
    *   TCP port number of TADK
    */
    public final static int port = 9761;    
    /*
    *   RT Address to be configured
    */
    public final static int rtAddress = 15;
    
    /** 
     * @param args[] None
     * @throws UnknownHostException
     * @throws IOException
     * @throws FailedToConfigure
     */
    public static void main(String args[]) throws UnknownHostException, IOException, FailedToConfigure
	{ 
        //Create an object of RT and connect to TADK through tcp on the give ip and port and set RT address as well
        RemoteTerminal rt = new RemoteTerminal(ip,port,rtAddress);
        System.out.println("Connected to " + ip + ":" + port);
        Thread rtRecieveThread = new Thread(()->{
            System.out.println("Recieve Thread Started");
            while(true){
                DeviceData recData;
				try {
                    recData = rt.readDeviceData();
                    System.out.println("Recieved [" + recData.subAddress+ "] :"+recData.response);
                    switch(recData.response){
                        case DeviceConstants.TADK_RESPONSE_SUCCESSFULL_RX_BC_RT:
                            //BC->RT command  executed successfully. 
                            //System.out.println("Status: " + recData.status); //Check RT Status
                            rt.writeSubAddress(recData.subAddress,recData.data);
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
