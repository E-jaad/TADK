package TADK;
import java.net.*; 
import java.io.*;

public 
class DeviceConnection {
    private Socket socket;
    private BufferedReader inStream;
    private DataOutputStream outStream = null;
    public String ip;
    public int port;
    public int rtAddress;
    public int terminalType;
    public boolean isConnected=false;
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
    public DeviceData readDevice() {
        char[] tcpBuffer = new char[82];
        try{
            final int bytesread = inStream.read(tcpBuffer, 0, 82);
            if (bytesread == 82 && tcpBuffer[0] == 0xaa) {
                return DeviceData.decode(tcpBuffer);
            }
        }
        catch(Exception e2){
            cleanUp();
        }
        return null;
    }
    public void sendDevice(DeviceData data, int command, int action) throws IOException {
        outStream.write(data.encode((byte)command, (byte)action),0,82);
        outStream.flush();
    } 
}