import java.io.IOException;
import java.net.UnknownHostException;
import TADK.*;
public class exampleBC {

	public static void main(String args[]) throws UnknownHostException, IOException, FailedToConfigure
	{ 
        short[] data = new short[32];
        //Connect to TCP Server TADK
        BusController bc = new BusController("127.0.0.1",4815);
        //configure TADK as Bus Controoler
        bc.configure();
        //Send RT->BC Command on RT:12 SA:1 TxRx:1 Word Count:data.length
        DeviceData response = bc.queryBcRt(
            new DeviceData(12,1,1,data)
        );  
	} 
} 
