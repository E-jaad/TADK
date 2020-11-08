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
    public DeviceConnection(String ip, int port, int rtAddress, int terminalType) {
        this.ip = ip;
        this.port = port;
        this.rtAddress = rtAddress;
        this.terminalType = terminalType;
        this.socket = null;
        this.inStream = null;
        this.outStream = null;
    }
    public void connect() throws UnknownHostException, IOException {
        this.socket = new Socket(this.ip, this.port);    
        this.inStream = new BufferedReader(new InputStreamReader(this.socket.getInputStream(),"Cp1252"));
        this.outStream = new DataOutputStream(socket.getOutputStream());
    }
    public DeviceData readDevice() throws IOException {
        char[] tcpBuffer = new char[82];
        final int bytesread = inStream.read(tcpBuffer, 0, 82);
        if (bytesread == 82 && tcpBuffer[0] == 0xaa) {
            return DeviceData.decode(tcpBuffer);
        }
        return null; 
    }
    public void sendDevice(DeviceData data, int command, int action) throws IOException {
        outStream.write(data.encode((byte)command, (byte)action),0,82);
        outStream.flush();
    } 
}