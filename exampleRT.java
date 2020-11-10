import java.io.IOException;
import java.net.UnknownHostException;
import TADK.*;
public class exampleRT {

    public final static String ip = "192.168.0.102";
    public final static int port = 9761;    
    public final static int rtAddress = 1;
    public static void main(String args[]) throws UnknownHostException, IOException, FailedToConfigure
	{ 
        short[] data1 = new short[]{1,2,3,4,5};
        short[] data2 = new short[]{1,2,3,4,5,6,7,8,9,10};
        short[] data3 = new short[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
        //Create an object of RT and connect to TADK through tcp on the give ip and port and set RT address as well
        RemoteTerminal rt = new RemoteTerminal(ip,port,rtAddress);
        
        rt.writeSubAddress(1, data1);         
        rt.writeSubAddress(2, data2);
        rt.writeSubAddress(3, data3);

        Thread rtRecieveThread = new Thread(()->{
            System.out.println("Recieve Thread Started");
            while(true){
                DeviceData recData;
				try {
                    recData = rt.readDeviceData();
                    System.out.println("Recieved [" + recData.subAddress+ "] :"+recData.response);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }, "RTReciever");

        rtRecieveThread.start();
    }
}