package TADK;
import java.net.*; 
import java.io.*; 

/**
 * This is the bus controller class which sets up
 * the device as a bus controller which can accpet
 * commands from the host machine and send them to 
 * other remote terminals on the bus
 */
public class BusController {
    private DeviceConnection connection;
    public BusController(String ip, int port) throws UnknownHostException, IOException, FailedToConfigure, FailedToDecode {
        this.connection = new DeviceConnection(ip, port, 0x0, 2);
        this.connection.connect();
        this.configure();
    }
    private DeviceData configure() throws FailedToConfigure, FailedToDecode, UnknownHostException, IOException{
        this.connection.sendDevice(
            new DeviceData(),
            DeviceConstants.TADK_COMMAND_CONFIG,
            DeviceConstants.TADK_BUS_CONTROLLER
        );
        DeviceData ret = this.connection.readDevice();
        if(ret == null || ret.response == DeviceConstants.TADK_RESPONSE_CONFIG_FAILED){
            throw new FailedToConfigure("Bus controller");
        }
        return ret;
    }
    public DeviceData queryRt(DeviceData data) throws UnknownHostException, IOException {
        connection.sendDevice(
            data,
            data.txRx  == 1 ? DeviceConstants.TADK_COMMAND_GET : DeviceConstants.TADK_COMMAND_SET,
            DeviceConstants.TADK_ACTION_VECT
        );
        return connection.readDevice();
    }
    public DeviceData queryBcRt(int rtAddress, int subAddress, short[] data) throws UnknownHostException, IOException {
        connection.sendDevice(
            new DeviceData(rtAddress, subAddress, 1, data),
            DeviceConstants.TADK_COMMAND_GET,
            DeviceConstants.TADK_ACTION_VECT
        );
        return connection.readDevice();
    }
    public DeviceData queryRtBc(int rtAddress, int subAddress, short[] data) throws UnknownHostException, IOException {
        connection.sendDevice(
            new DeviceData(rtAddress, subAddress, 0, data),
            DeviceConstants.TADK_COMMAND_SET,
            DeviceConstants.TADK_ACTION_VECT
        );
        return connection.readDevice();
    }
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
    public DeviceData queryModeCode(int rtAddress, int modecode, short[] data) throws UnknownHostException,IOException {
        connection.sendDevice(
            new DeviceData(rtAddress, modecode, 0, data),
            DeviceConstants.TADK_COMMAND_SET,
            DeviceConstants.TADK_ACTION_MODE
        );
        return connection.readDevice();
    }
    public DeviceData broadcast(DeviceData data) throws UnknownHostException,IOException {
        connection.sendDevice(
            data,
            DeviceConstants.TADK_COMMAND_SET,
            DeviceConstants.TADK_ACTION_BRODCAST
        );
        return connection.readDevice();
    }
}