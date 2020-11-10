import java.io.IOException;
import java.net.UnknownHostException;
import TADK.*;
/**
 * This is an example BC which sends and recives data with RT address 1
 */
public class exampleBC {
    public final static String ip = "192.168.0.102";
    public final static int port = 9761;
	public static void main(String args[]) throws UnknownHostException, IOException, FailedToConfigure
	{ 
        int count = 0;
        DeviceData[] messages = new DeviceData[6];
        messages[0] = new DeviceData(1,1,5);  // Rt address 1 sub address 1 (TX) 5 words 
        messages[1] = new DeviceData(1,2,10); // Rt address 1 sub address 2 (TX) 10 words
        messages[2] = new DeviceData(1,3,20); // Rt address 1 sub address 3 (TX) 20 words 
        messages[3] = new DeviceData(1,1,0,new short[]{1,2,3,4,5}); // Rt address 1 sub address 1 (RX) 5 words 
        messages[4] = new DeviceData(1,2,0,new short[]{1,2,3,4,5,6,7,8,9,10}); // Rt address 1 sub address 1 (RX) 10 words 
        messages[5] = new DeviceData(1,3,0,new short[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20}); // Rt address 1 sub address 1 (RX) 20 words 
        try{
            //Connect to TCP Server TADK
            BusController bc = new BusController(ip, port);
            System.out.println("Connected to "+ip+":"+port);  
            while(true) {
                if(count == 6) count=0;;
                DeviceData res = bc.queryRt(
                    messages[count]
                );  
                if(res.response==DeviceConstants.TADK_RESPONSE_FAILED_RX_BC_RT)
                    System.out.println("BC->RT Command failed");
                else if(res.response==DeviceConstants.TADK_RESPONSE_FAILED_TX_RT_BC)
                    System.out.println("RT->BC Command failed");
                count++;
            }
        } catch(Exception e){
            e.printStackTrace();          
        }
	} 
} 