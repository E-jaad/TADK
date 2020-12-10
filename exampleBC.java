import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Timer;

import TADK.*;
/**
 * This is an example BC which sends and recives data with RT address 1
 */
public class exampleBC {
    public final static String ip = "192.168.0.102";
    public final static int port = 9761;
  	public static void main(String args[]) throws UnknownHostException, IOException, FailedToConfigure
	{ 
        try{
            //Connect to TCP Server TADK
            BusController bc = new BusController(ip, port);
            System.out.println("Connected to "+ip+":"+port);  
           scheduler t1 = new scheduler("50ms",bc);
           Timer t = new Timer();
           t.schedule(t1,60,50); //  executes for every 50 miliseconds
        } catch(Exception e){
            e.printStackTrace();          
        }
	} 
} 