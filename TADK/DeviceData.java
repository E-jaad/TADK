package TADK;
/**
 *
 * @author E-JAAD
 * 
 */
/**
 * 
 * This class encodes and decodes message packet to/from TADK format.
 */
public class DeviceData {

    /**
     *(1-30)RT Address
     */
    public int rtAddress; 
    /**
     *(1-30)Sub Address
     */
    public int subAddress; 
    /**
     *(0/1) Receive/Transmit
     */
    public int txRx;
    /**
     *Data WORDS max Size-32
     */
    public short[] data; 
    /**
     *Status field of response
     */
    public int status; 
    /**
     *(0/1) Channel A/B used by response
     */
    public int channel; 
    /**
     *EJAAD Status code of Response as defined in API
     */
    public int response; 
    // a dummy constructor for configuaration use

    /**
     *
     */
    public DeviceData() {
        this.rtAddress = 0;
        this.subAddress = 0;
        this.txRx = 0;
        this.data = new short[32];
    }

    /**
     *
     * @param rtAddress
     * @param subAddress
     * @param txRx
     * @param data
     */
    public DeviceData(int rtAddress,int subAddress,int txRx, short[] data) {
        this.rtAddress = rtAddress;
        this.subAddress = subAddress;
        this.txRx = txRx;
        this.data = data;
    }

    /**
     *
     * @param rtAddress
     * @param subAddress
     * @param count
     */
    public DeviceData(int rtAddress,int subAddress,int txRx, int count) {
        this.rtAddress = rtAddress;
        this.subAddress = subAddress;
        this.txRx = txRx;
        this.data = new short[count];
    }

    /**
     *
     * @param rtAddress
     * @param subAddress
     * @param txRx
     * @param data
     * @param status
     * @param channel
     * @param response
     */
    public DeviceData(int rtAddress,int subAddress,int txRx, short[] data, int status, int channel,int response) {
        this.rtAddress = rtAddress;
        this.subAddress = subAddress;
        this.txRx = txRx;
        this.data = data;
        this.status = status;
        this.channel = channel;
        this.response = response;
    }
    
    /** 
     * this method is used to encode the message/command to TADK API format
     * @param command
     * @param action
     * @return byte[]
     */
    public byte[] encode(byte command,byte action) {
        byte[] encoded = new byte[82];
        for (int i = 0; i < 82; ++i) {
            encoded[i] =(byte) 0;
        }
        encoded[0] = (byte)0xaa; //signature
        encoded[1] = (byte)0xaa; //signature 2nd byte
        encoded[2] = (byte)1; //index
        encoded[4] = (byte)command; //command
        encoded[6] = (byte)action; //action 
        encoded[8] = (byte)this.rtAddress; //rt address
        encoded[10]= (byte)this.txRx; //txrx
        encoded[12]= (byte)this.subAddress;//subaddress
        encoded[14]= (byte)this.data.length;//count
        for (int i = 0, j = 0; i < this.data.length; i++,j+=2) {
            encoded[18+j] =(byte) this.data[i];
            encoded[19+j] =(byte) (this.data[i] >> 8);
        }
        return encoded;
    }
    
    /** 
     * This method is used to parse the TADK Response
     * @param rawData The Raw data received from TADK
     * @return DeviceData
     * @throws FailedToDecode
     */
    public static DeviceData decode(char[] rawData) throws FailedToDecode {
        
        int sync = rawData[0] + rawData[1] * 256;
        if(sync != 0xaaaa){ 
            throw new FailedToDecode("in correct sync = "+sync);
        }
        //int messageIndex = rawData[2] + rawData[3] * 256;
        short response = (short)(rawData[4] +rawData[5] *256);
        int channel = rawData[6] + rawData[7] * 256;
        int rta = rawData[8] + rawData[9] * 256;
        int txrx = rawData[10] + rawData[11] * 256;
        int subadd = rawData[12] + rawData[13] * 256;
        int count = rawData[14] + rawData[15] * 256;
        int status = rawData[16] + rawData[17] * 256;
        short[] data = new short[count];
        for (int i = 0, j = 0; i < count; i++,j+=2) {
            data[i] = (short)(rawData[18+j] + rawData[19+j] * 256);
        }
        return new DeviceData(rta, subadd, txrx, data, status, channel,response);
    }
}

