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
    public static DeviceData decode(byte[] rawData) throws FailedToDecode {
        
        int sync = (int)((rawData[1]&0xff)<<8 | (rawData[0]&0xff));
        if(sync != 0xaaaa){ 
            throw new FailedToDecode("in correct sync = "+sync);
        }
        //int messageIndex = rawData[2] + rawData[3] * 256;
        short response =    (short)((rawData[5]&0xff)<<8 | (rawData[4]&0xff));
        int channel =       (short)((rawData[7]&0xff)<<8 | (rawData[6]&0xff));
        int rta =           (short)((rawData[9]&0xff)<<8 | (rawData[8]&0xff));
        int txrx =          (short)((rawData[11]&0xff)<<8 | (rawData[10]&0xff));
        int subadd =        (short)((rawData[13]&0xff)<<8 | (rawData[12]&0xff));
        int count =         (short)((rawData[15]&0xff)<<8 | (rawData[14]&0xff));
        int status =        (short)((rawData[17]&0xff)<<8 | (rawData[16]&0xff));
        short[] data = new short[count];
        for (int i = 0, j = 0; i < count; i++,j+=2) {
            data[i] =       (short)((rawData[19+j]&0xff)<<8 | (rawData[18+j]&0xff));
        }
        return new DeviceData(rta, subadd, txrx, data, status, channel,response);
    }
}

