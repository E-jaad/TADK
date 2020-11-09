import java.io.IOException;
import java.net.UnknownHostException;
import TADK.*;
public class exampleRT {
    public static void main(String args[]) throws UnknownHostException, IOException, FailedToConfigure
	{ 
        short[] data = new short[32];
        //Create an object of RT and connect to TADK through tcp on the give ip and port and set RT address as well
        RemoteTerminal rt = new RemoteTerminal("192.168.0.102",9761,13);
        //Set 32 words in Transmit dataset of RT 13 at SubAdress 3
        rt.writeSubAddress(3, data);                
    }
}