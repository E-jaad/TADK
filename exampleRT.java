import java.io.IOException;
import java.net.UnknownHostException;
import TADK.*;
public class exampleRT {
    public static void main(String args[]) throws UnknownHostException, IOException, FailedToConfigure
	{ 
        short[] data = new short[32];
        //Create an object of RT and connect to TADK through tcp on the give ip and port and set RT address as well
        RemoteTerminal rt = new RemoteTerminal("127.0.0.1",4815,13);
        //configure TADK as Remote Terminal
        rt.configure();
        //Set 32 words in Transmit dataset of RT 13 at SA1
        rt.sendRtData(
            new DeviceData(13,2,1,data)
        );
    }
}
