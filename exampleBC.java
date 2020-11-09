import java.io.IOException;
import java.net.UnknownHostException;
import TADK.*;
public class exampleBC {

	public static void main(String args[]) throws UnknownHostException, IOException, FailedToConfigure
	{ 
        short[] data = new short[32];
        //Connect to TCP Server TADK
        BusController bc = new BusController("192.168.0.102",9761);
        //Send RT->BC Command on RT:12 SA:1 TxRx:1 Word Count:data.length
        DeviceData res = bc.queryRt(
            new DeviceData(12,1,1,data)
        );  
        if(res.response==DeviceConstants.TADK_RESPONSE_FAILED_RX_BC_RT)
            System.out.println("BC->RT Command failed");
	} 
} 