package TADK;
import java.net.*; 
import java.io.*; 


public class RemoteTerminal {
    private DeviceConnection connection;
    public RemoteTerminal(String ip, int port, int rtAddress) throws UnknownHostException, IOException {
        this.connection = new DeviceConnection(ip, port, rtAddress, 3);
        this.connection.connect();
    }
    public DeviceData configure() throws FailedToConfigure, UnknownHostException, IOException {
        this.connection.sendDevice(
            new DeviceData(),
            DeviceConstants.TADK_COMMAND_CONFIG,
            DeviceConstants.TADK_REMOTE_TERMINAL
        );
        DeviceData ret = this.connection.readDevice();
        if(ret.response == DeviceConstants.TADK_RESPONSE_CONFIG_FAILED){
            throw new FailedToConfigure("Remote Terminal");
        }
        return ret;
    }
    public void sendRtData(DeviceData data) throws UnknownHostException, IOException {
        connection.sendDevice(
            data,
            DeviceConstants.TADK_COMMAND_SET,
            DeviceConstants.TADK_ACTION_VECT
        );
    }
    public DeviceData recieveRtData() throws UnknownHostException, IOException {
        return null;
    }
}

