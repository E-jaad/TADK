
import java.io.IOException;
import java.net.UnknownHostException;
import TADK.*;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
/**
 * This is an example BC which sends and recives data with RT address 1
 */
public class exampleBC {
    /*
    * IP address of TADK
    */
    public final static String ip = "192.168.1.102";
    /*
    *   TCP Port Number of TADK
    */
    public final static int port = 9761;
    /** 
     * @param args[] none
     * @throws UnknownHostException
     * @throws IOException
     * @throws FailedToConfigure
     */
    public static void main(String args[]) throws UnknownHostException, IOException, FailedToConfigure {                                                                                                       // (RX) 20 words
        try {
            ConcurrentLinkedQueue q = new ConcurrentLinkedQueue<DeviceData>();
            
            // Connect to TCP Server TADK
            BusController bc = new BusController(ip, port);
            System.out.println("Connected to " + ip + ":" + port);
            exampleScheduler t1 = new exampleScheduler("50ms",bc,q);
            Timer t = new Timer();
            t.schedule(t1,60,50); //  executes for every 50 miliseconds
            exampleTCPRespProcess T1Process=new exampleTCPRespProcess(q);
            Thread t2 = new Thread(T1Process);
            t2.setPriority(Thread.MIN_PRIORITY);
            t2.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}