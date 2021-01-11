package TADK;
import java.net.*; 
import java.io.*; 
/**
 *
 * @author E-JAAD
 */
/**
 * This is the bus controller class which sets up
 * the device as a bus controller which can accept
 * commands from the host machine and send them to 
 * other remote terminals on the bus
 */
public class BusController {
    private DeviceConnection connection;
    /**
     * This will connect you to TADK on given IP and Port and will configure the TADK as BC
     * @param ip    IP address of TADK
     * @param port  TCP Port of TADK
     * @throws UnknownHostException
     * @throws IOException
     * @throws FailedToConfigure
     * @throws FailedToDecode
     */
    public BusController(String ip, int port) throws UnknownHostException, IOException, FailedToConfigure, FailedToDecode {
        this.connection = new DeviceConnection(ip, port, 0x0, 2);
        this.connection.connect();
        this.configure();
    }
    
    /** 
     * this method is used to configure the TADK as BC
     * @return DeviceData returns the response of TADK
     * @throws FailedToConfigure
     * @throws FailedToDecode
     * @throws UnknownHostException
     * @throws IOException
     */
    private DeviceData configure() throws FailedToConfigure, FailedToDecode, UnknownHostException, IOException{
        this.connection.sendDevice(
            new DeviceData(),
            DeviceConstants.TADK_COMMAND_CONFIG,
            DeviceConstants.TADK_ACTION_SET_BC
        );
        DeviceData ret = this.connection.readDevice();
        if(ret == null || ret.response == DeviceConstants.TADK_RESPONSE_CONFIG_FAILED){
            throw new FailedToConfigure("Bus controller");
        }
        return ret;
    }
    
    /** 
     * This method is used to send a BC-RT or RT-BC message
     * @param data  The data object to be sent
     * @return DeviceData   The TADK response is returned
     * @throws UnknownHostException
     * @throws IOException
     */
    public DeviceData queryRt(DeviceData data) throws UnknownHostException, IOException {
        connection.sendDevice(
            data,
            data.txRx  == 1 ? DeviceConstants.TADK_COMMAND_GET : DeviceConstants.TADK_COMMAND_SET,
            DeviceConstants.TADK_ACTION_VECT
        );
        return connection.readDevice();
    }
    
    /** 
     * This method is used to send a BC->RT command, and reads the response from TADK as well.
     * @param rtAddress RT address
     * @param subAddress    Subadress of the RT, the data is written to be
     * @param data  (1-32)MIL-STD-1553 Data words, the word count depends on the size of this array.
     * @return DeviceData Response packet read from TADK is returned.
     * @throws UnknownHostException
     * @throws IOException
     */
    public DeviceData queryBcRt(int rtAddress, int subAddress, short[] data) throws UnknownHostException, IOException {
        connection.sendDevice(
            new DeviceData(rtAddress, subAddress, 1, data),
            DeviceConstants.TADK_COMMAND_GET,
            DeviceConstants.TADK_ACTION_VECT
        );
        return connection.readDevice();
    }
    
    /** 
     * This method is used to send an RT->BC command, and read the response
     * @param rtAddress RT address 
     * @param subAddress    Subaddress of the RT, the data is read to be
     * @param data
     * @return DeviceData
     * @throws UnknownHostException
     * @throws IOException
     */
    public DeviceData queryRtBc(int rtAddress, int subAddress, short[] data) throws UnknownHostException, IOException {
        connection.sendDevice(
            new DeviceData(rtAddress, subAddress, 0, data),
            DeviceConstants.TADK_COMMAND_SET,
            DeviceConstants.TADK_ACTION_VECT
        );
        return connection.readDevice();
    }
    
    /** 
     * This method is used to send RT->RT commands
     * @param txRtAddress   RT address which will sends data
     * @param txSubAddress  Subadress of transmitting RT
     * @param rxRtAddress   address of Receiving RT
     * @param rxSubAddress  SubAddress of Receiving RT
     * @return DeviceData   
     * @throws UnknownHostException
     * @throws IOException
     */
    public DeviceData queryRtRt(int txRtAddress, int txSubAddress, int rxRtAddress, int rxSubAddress) throws UnknownHostException,IOException {
        short[] data = new short[32];
        connection.sendDevice(
            new DeviceData(
                rxRtAddress+txRtAddress*256,
                rxSubAddress+txSubAddress*256, 
                0, 
                data
            ),
            DeviceConstants.TADK_COMMAND_RTRT,
            DeviceConstants.TADK_ACTION_VECT
        );
        return connection.readDevice();
    }
    
    /** 
     * This method is used to send a modecode command, and reads the response as well
     * @param rtAddress address of RT to be sent command
     * @param modecode  ModeCode number as per MIL-STD-1553B protocol
     * @param data  Data Words
     * @return DeviceData
     * @throws UnknownHostException
     * @throws IOException
     */
    public DeviceData queryModeCode(int rtAddress, int modecode) throws UnknownHostException,IOException {
        connection.sendDevice(
            new DeviceData(rtAddress, 0, DeviceConstants.TADK_RT_TO_BC, modecode),
            DeviceConstants.TADK_COMMAND_GET,
            DeviceConstants.TADK_ACTION_MODE
        );
        return connection.readDevice();
    }
    
    /** 
     * this method is used to send broadcast command
     * @param data Data object
     * @return DeviceData Response from TADK
     * @throws UnknownHostException
     * @throws IOException
     */
    public DeviceData broadcast(DeviceData data) throws UnknownHostException,IOException {
        connection.sendDevice(
            data,
            DeviceConstants.TADK_COMMAND_SET,
            DeviceConstants.TADK_ACTION_BRODCAST
        );
        return connection.readDevice();
    }
}