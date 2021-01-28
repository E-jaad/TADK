package TADK;
import java.net.*; 
import java.io.*; 
/**
 * @author E-JAAD
 * 
 */
/**
 * This is the Remote Terminal Class, which sets up the TADK as a Remote Terminal,
 * accepts the commands to get/set data words on RT, and also map/unmap Digital inputs and outputs
 * to MIL-STD-1553 data words. 
 */

public class RemoteTerminal {
    private DeviceConnection connection;
    private int rtAddress;
    /**
     * this construct will make an object of RT, connects to TADK and configure TADK as RT with given RT address
     * @param ip    IP address of TADK 
     * @param port  TCP port of TADK
     * @param rtAddress RT address to be configured
     * @throws UnknownHostException
     * @throws IOException
     * @throws FailedToConfigure
     */
    public RemoteTerminal(String ip, int port, int rtAddress) throws UnknownHostException, IOException,
            FailedToConfigure {
        this.connection = new DeviceConnection(ip, port, rtAddress, 3);
        this.connection.connect();
        this.rtAddress = rtAddress;
        this.configure(rtAddress);
    }
    
    /** 
     * this method is called to configure TADK as RT
     * @return DeviceData
     * @throws FailedToConfigure
     * @throws IOException
     */
    private DeviceData configure(int rtAddress) throws FailedToConfigure, IOException {
        this.connection.sendDevice(
            new DeviceData(rtAddress,0,0,0),
            DeviceConstants.TADK_COMMAND_CONFIG,
            DeviceConstants.TADK_ACTION_SET_RT
        );
        DeviceData ret = this.connection.readDevice();
        if(ret.response != DeviceConstants.TADK_RESPONSE_CONFIG_SUCCESS){
            throw new FailedToConfigure("Remote Terminal");
        }
        return ret;
    }
    /** 
     * This method is used to set data on subaddresses of RT
     * @param data  Data object
     * @throws IOException
     */
    public void sendDeviceData(DeviceData data) throws IOException {
        this.connection.sendDevice(
            data,
            DeviceConstants.TADK_COMMAND_SET,
            DeviceConstants.TADK_ACTION_VECT
        );
    }
    
    /** 
     * This method is used to write data on subaddresses of RT
     * @param subAddress    Subaddress of RT
     * @param data  Data words to be written on given subaddress
     * @throws IOException
     */
    public void writeSubAddress(int subAddress, short[] data) throws IOException {
        this.connection.sendDevice(
            new DeviceData(this.rtAddress, subAddress, 1, data),
            DeviceConstants.TADK_COMMAND_SET,
            DeviceConstants.TADK_ACTION_VECT
        );
    }
    
    /** 
     * This methods reads data from TADK
     * @return DeviceData
     * @throws IOException
     */
    public DeviceData readDeviceData() throws IOException {
        return this.connection.readDevice();
    }
    
    /** 
     * This method is used to set/unset the digital outputs
     * @param DOnumber  (0-3) The DO number
     * @param direction (true/false) Set/Unset
     * @throws IOException
     */
    public void setUnsetDO(short DOnumber, Boolean direction) throws IOException {
        int action = direction 
            ? DeviceConstants.TADK_ACTION_SET_DIOS 
            : DeviceConstants.TADK_ACTION_UNSET_DIOS;
        short[] data = new short[]{DOnumber};
        this.connection.sendDevice(
            new DeviceData(this.rtAddress, 0, 0, data), 
            DeviceConstants.TADK_COMMAND_CONFIG, 
            action
        );
    }
    
    /** 
     * This method is used to map Digital inputs and outputs to MIL-STD-1553 data on RTs
     * @param type  (0/1)   Digital Output/ Digital Input
     * @param number    (0-3) DO/DI Number
     * @param sa    (1-31) Subaddrss the DO/DI to be mapped to
     * @param wordNumber    (0-31)Word number of the given Subadress
     * @param bitNumber (0-15) Bit number of the give word the DO/DI to mapped to
     * @throws IOException
     */
    public void mapDIOstoData(short type,short number,short sa,short wordNumber,short bitNumber)throws IOException {
        short[] data = new short[]{type, number, sa, wordNumber, bitNumber };
        this.connection.sendDevice(
            new DeviceData(this.rtAddress, 0, 0, data), 
            DeviceConstants.TADK_COMMAND_CONFIG, 
            DeviceConstants.TADK_ACTION_MAP_DIOS
        );
    }
    
    /** 
     * This method is used to unmap DO/DI from MIL-STD-1553 data if it was previously mapped
     * @param type  (0/1) DO/DI
     * @param number    (0-3)Number of DO/DI
     * @param sa    (1-31) Subaddress of the RT, The given DO/DI is to be unmapped from
     * @param wordNumber (0-31) Word Number of the given subaddress
     * @param bitNumber (0-15) Bit Number of the given word number 
     * @throws IOException
     */
    public void unmapDIOstoData(short type,short number,short sa,short wordNumber,short bitNumber) throws IOException {
        short[] data = new short[]{type,number, sa, wordNumber, bitNumber };
        this.connection.sendDevice(
            new DeviceData(this.rtAddress, 0, 0, data), 
            DeviceConstants.TADK_COMMAND_CONFIG, 
            DeviceConstants.TADK_ACTION_UNMAP_DIOS
        );
    }
}
