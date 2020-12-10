package TADK;
import java.net.*; 
import java.io.*; 


public class RemoteTerminal {
    private DeviceConnection connection;
    private int rtAddress;
    public RemoteTerminal(String ip, int port, int rtAddress) throws UnknownHostException, IOException,
            FailedToConfigure {
        this.connection = new DeviceConnection(ip, port, rtAddress, 3);
        this.connection.connect();
        this.rtAddress = rtAddress;
        this.configure();
    }
    private DeviceData configure() throws FailedToConfigure, IOException {
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
    public void sendDeviceData(DeviceData data) throws IOException {
        this.connection.sendDevice(
            data,
            DeviceConstants.TADK_COMMAND_SET,
            DeviceConstants.TADK_ACTION_VECT
        );
    }
    public void writeSubAddress(int subAddress, short[] data) throws IOException {
        this.connection.sendDevice(
            new DeviceData(this.rtAddress, subAddress, 1, data),
            DeviceConstants.TADK_COMMAND_SET,
            DeviceConstants.TADK_ACTION_VECT
        );
    }
    public void writeData(int cmd,int action,short[] data) throws IOException {
        this.connection.sendDevice(
            new DeviceData(this.rtAddress, 0, 0, data),
            cmd,
            action
        );
    }
    public DeviceData readDeviceData() throws IOException {
        return this.connection.readDevice();
    }
}