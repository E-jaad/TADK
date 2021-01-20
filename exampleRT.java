import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;

import TADK.*;
public class exampleRT {
    /*
    *   IP address of TADK
    */
    public final static String ip = "192.168.0.102";
    /*
    *   TCP port number of TADK
    */
    public final static int port = 9761;    
    /*
    *   RT Address to be configured
    */
    public final static int rtAddress = 1;
    
    /** 
     * @param args[] None
     * @throws UnknownHostException
     * @throws IOException
     * @throws FailedToConfigure
     */
    public static void main(String args[]) throws UnknownHostException, IOException, FailedToConfigure
	{ 
        //A Queue for TCP responses received from TADK
        ConcurrentLinkedQueue q = new ConcurrentLinkedQueue<DeviceData>();
        short[] data1 = new short[]{1,2,3,4,5};
        short[] data2 = new short[]{1,2,3,4,5,6,7,8,9,10};
        short[] data3 = new short[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
        //Create an object of RT and connect to TADK through tcp on the give ip and port and set RT address as well
        RemoteTerminal rt = new RemoteTerminal(ip,port,rtAddress);
        exampleTCPRespProcess T1Process=new exampleTCPRespProcess(q);
        //This thread will check the tcp responses and the data can be used for further processing or printing
        Thread t1 = new Thread(T1Process);
        t1.setPriority(Thread.MIN_PRIORITY);
        t1.start();
        //update data at subaddress 1.
        rt.writeSubAddress(1, data1);         
        rt.writeSubAddress(2, data2);
        rt.writeSubAddress(3, data3);
        //This thread is used to continously read from TCP only, please run separate thread for any other task.
        Thread rtRecieveThread = new Thread(()->{
            System.out.println("Recieve Thread Started");
            while(true){
                DeviceData recData;
				try {
                    //read from TCP
                    recData = rt.readDeviceData();
                    //push the packet to queue
                    q.add(recData);
                    //System.out.println("Recieved [" + recData.subAddress+ "] :"+recData.response);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }, "RTReciever");
        rtRecieveThread.start();
    }
}