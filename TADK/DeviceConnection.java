package TADK;
import java.net.*; 
import java.io.*;
/**
 *
 * @author E-JAAD
 * 
 */
/**
 * This is DeviceConnection class to handle TCP Connection for TADk. and open streams for input/output buffers.
 * 
 */
public class DeviceConnection {
    private Socket socket;
    private BufferedReader inStream;
    private DataOutputStream outStream = null;

    /**
     *  IP address of TADK.
     */
    public String ip;

    /**
     *  TCP Port number of TADK.
     */
    public int port;

    /**
     * RT address of TADK if configured as RT, 0 for BC and BM .
     */
    public int rtAddress;

    /**
     *  Terminal Type i.e BC(2)/RT(3)/BM(4).
     */
    public int terminalType;

    /**
     *TCP Connection Flag(True/False).
     */
    public boolean isConnected=false;
    /**
     * @param ip IP address of TADK 
     * @param port TCP port of TADk
     * @param rtAddress (1-30)RT address to be configured,0-BC,0-BM
     * @param terminalType  Type of terminal i.e BC-2,RT-3,BM-4, as defined in Device Constants class
     */
    public DeviceConnection(String ip, int port, int rtAddress, int terminalType) {
        this.ip = ip;
        this.port = port;
        this.rtAddress = rtAddress;
        this.terminalType = terminalType;
        this.socket = null;
        this.inStream = null;
        this.outStream = null;
    }
    private  void cleanUp() {
        try {
            if (this.socket != null) {
                this.socket.close();
                this.socket = null;
                this.isConnected=false;
            }
        }
        catch (IOException e) {
            this.socket = null;
        }
        try {
            if (this.inStream != null) {
                this.inStream.close();
                this.inStream = null;
            }
        }
        catch (IOException e) {
            this.inStream = null;
        }
        try{
            if (this.outStream != null) {
                this.outStream.close();
                 this.outStream = null;
             }
        }
        catch (IOException e){
            this.outStream = null;
        }
    }
    
    /** 
     * This method will open a socket and connect to TADK through TCP. 
     * @throws UnknownHostException
     * @throws IOException
     */
    public void connect() throws UnknownHostException, IOException {
        try{
            this.socket = new Socket(this.ip, this.port);    
            this.inStream = new BufferedReader(new InputStreamReader(this.socket.getInputStream(),"Cp1252")); //ISO-8859-1
            this.outStream = new DataOutputStream(socket.getOutputStream());
            this.isConnected=true;
        }
        catch(Exception e2){
            cleanUp();
        }
    }
    
    /** 
     * this method is used to read data from TCP and parse the TCP packet.
     * @return DeviceData   the data read from TADK is returned
     */
    public DeviceData readDevice() {
        char[] tcpBuffer = new char[82];
        try{
            final int bytesread = inStream.read(tcpBuffer, 0, 82);
            if (bytesread == 82) {
                return DeviceData.decode(tcpBuffer);
            }
        }
        catch(Exception e2){
            cleanUp();
        }
        return null;
    }
    
    /** 
     * this method is used to encode the data to the TADK API format, and sends to TADK.
     * @param data A data object of type DeviceData
     * @param command (0-3)Command Type i.e Get-0/Set-1/Config-2/RT->RT-3 
     * @param action    (0-19) as defined in DeviceConstants
     * @throws IOException
     */
    public void sendDevice(DeviceData data, int command, int action) throws IOException {
        outStream.write(data.encode((byte)command, (byte)action),0,82);
        outStream.flush();
    } 
}