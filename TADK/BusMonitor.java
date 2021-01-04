package TADK;
import java.net.*; 
import java.io.*; 
/**
 * 
 * @author  E-JAAD
 */
/**
 * This is BusMonitor Class used to set up the TADK as Bus Monitor, To set/unset traps on BM, and enable/disable BM.
 */
public class BusMonitor {
    private DeviceConnection connection;
    /**
     * This will construct an object of BM, Connect to TADK and configure it as BM.
     * @param ip
     * @param port
     * @throws UnknownHostException
     * @throws IOException
     * @throws FailedToConfigure
     * @throws FailedToDecode
     */
    public BusMonitor(String ip, int port) throws UnknownHostException, IOException, FailedToConfigure, FailedToDecode {
        this.connection = new DeviceConnection(ip, port, 0x0, 4);
        this.connection.connect();
        this.configure();
    }
    /**
     * This method will configure TADK as Bus Monitor.
     * @return  
     * @throws FailedToConfigure
     * @throws FailedToDecode
     * @throws UnknownHostException
     * @throws IOException
     */
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
    /**
     * This method is used to set a trap, everytime there is a message with following parameters on bus, Bus monitor will report it to the host machine. Disable BM before using this method.
     * @param rtAddress
     * @param subAddress
     * @param txRx
     * @param wordCount
     * @return
     * @throws UnknownHostException
     * @throws IOException
     */
    public DeviceData setTrap(int rtAddress, int subAddress,int txRx, int wordCount) throws UnknownHostException, IOException {
        connection.sendDevice(
            new DeviceData(rtAddress, subAddress, txRx, wordCount),
            DeviceConstants.TADK_COMMAND_CONFIG,
            DeviceConstants.TADK_ACTION_SETTRAP
        );
        return connection.readDevice();
    }
    /**
     * If a trap was previously set on BM, this method is used to unset that.
     * @param rtAddress
     * @param subAddress
     * @param txRx
     * @param wordCount
     * @return
     * @throws UnknownHostException
     * @throws IOException
     */
    public DeviceData unsetTrap(int rtAddress, int subAddress,int txRx, int wordCount) throws UnknownHostException, IOException {
        connection.sendDevice(
            new DeviceData(rtAddress, subAddress, txRx, wordCount),
            DeviceConstants.TADK_COMMAND_CONFIG,
            DeviceConstants.TADK_ACTION_UNSETTRAP
        );
        return connection.readDevice();
    }
    /**
     * This method is used to enable BM. BM will strat recording after being enabled. 
     * @return
     * @throws UnknownHostException
     * @throws IOException
     */
    public DeviceData enableBM() throws UnknownHostException, IOException {
        connection.sendDevice(
            new DeviceData(),
            DeviceConstants.TADK_COMMAND_CONFIG,
            DeviceConstants.TADK_ACTION_BM_ENABLE
        );
        return connection.readDevice();
    }
    /**
     * This method is used to disable BM.
     * @return
     * @throws UnknownHostException
     * @throws IOException
     */
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
