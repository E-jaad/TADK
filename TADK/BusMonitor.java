package TADK;
import java.net.*; 
import java.io.*; 
public class BusMonitor {
    private DeviceConnection connection;
    public BusMonitor(String ip, int port) throws UnknownHostException, IOException, FailedToConfigure, FailedToDecode {
        this.connection = new DeviceConnection(ip, port, 0x0, 4);
        this.connection.connect();
        this.configure();
    }
    private DeviceData configure() throws FailedToConfigure, FailedToDecode, UnknownHostException, IOException{
        this.connection.sendDevice(
            new DeviceData(),
            DeviceConstants.TADK_COMMAND_CONFIG,
            DeviceConstants.TADK_ACTION_SET_BM
        );
        DeviceData ret = this.connection.readDevice();
        if(ret == null || ret.response == DeviceConstants.TADK_RESPONSE_CONFIG_FAILED){
            throw new FailedToConfigure("Bus Monitor");
        }
        return ret;
    }
    public DeviceData setTrap(int rtAddress, int subAddress,int txRx, int wordCount) throws UnknownHostException, IOException {
        connection.sendDevice(
            new DeviceData(rtAddress, subAddress, txRx, wordCount),
            DeviceConstants.TADK_COMMAND_CONFIG,
            DeviceConstants.TADK_ACTION_SETTRAP
        );
        return connection.readDevice();
    }
    public DeviceData unsetTrap(int rtAddress, int subAddress,int txRx, int wordCount) throws UnknownHostException, IOException {
        connection.sendDevice(
            new DeviceData(rtAddress, subAddress, txRx, wordCount),
            DeviceConstants.TADK_COMMAND_CONFIG,
            DeviceConstants.TADK_ACTION_UNSETTRAP
        );
        return connection.readDevice();
    }
    public DeviceData enableBM() throws UnknownHostException, IOException {
        connection.sendDevice(
            new DeviceData(),
            DeviceConstants.TADK_COMMAND_CONFIG,
            DeviceConstants.TADK_ACTION_BM_ENABLE
        );
        return connection.readDevice();
    }
    public DeviceData disableBM() throws UnknownHostException, IOException {
        connection.sendDevice(
            new DeviceData(),
            DeviceConstants.TADK_COMMAND_CONFIG,
            DeviceConstants.TADK_ACTION_BM_DISABLE
        );
        return connection.readDevice();
    }
    public DeviceData readDeviceData() throws IOException {
        return this.connection.readDevice();
    }
}
