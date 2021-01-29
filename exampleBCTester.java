
import java.io.IOException;
import java.net.UnknownHostException;
import TADK.*;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This is an example BC which sends and recives data
 * This example send 30 commands(Subaddress 1-30) for BC->RT messages with 31 Data WORDS, 
 * 30 commands(subaddress 1-30) fro RT->BC messages with 31 Data Words
 * Different Modecodes
 * 
 */
public class exampleBCTester {
    /*
    * IP address of TADK
    */
    public final static String ip = "192.168.1.102";
    /*
    *   TCP Port Number of TADK
    */
    public final static int port = 9761;
    /**
     * Log file location
     */
    static String logFile = "BCLogFile.log";

    static long msgCounter=0;
    static long errCounter=0;
    static int averageWindow=100;
    static double totalTime=0;
    static Logger logger = Logger.getLogger(exampleBCTester.class.getName());
    // Random data to be sent, 31 words for each 31 subaddresses
    public static short[] testData = new short[] { 10, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
            20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 1 };
    private static boolean[][] testPassResponse = new boolean[2][32];
    private static boolean[][] testPassStatus = new boolean[2][32];
    private static boolean[][] testPassData = new boolean[2][32];

    private static void responseProcess(DeviceData recData) {
        switch (recData.response) {
            case DeviceConstants.TADK_RESPONSE_SUCCESSFULL_RX_BC_RT:
                // BC->RT command executed successfully.
                testPassResponse[DeviceConstants.TADK_BC_TO_RT][recData.subAddress] = true;
                if (recData.status != 0) {
                    logger.warning("Status: " + recData.status); // Check RT Status
                    testPassStatus[DeviceConstants.TADK_BC_TO_RT][recData.subAddress] = false;
                } else {
                    testPassStatus[DeviceConstants.TADK_BC_TO_RT][recData.subAddress] = true;
                    testPassData[DeviceConstants.TADK_BC_TO_RT][recData.subAddress] = true;
                }
                break;
            case DeviceConstants.TADK_RESPONSE_SUCCESSFULL_TX_RT_BC:
                // RT->BC command executed successfully.
                testPassResponse[DeviceConstants.TADK_RT_TO_BC][recData.subAddress] = true;
                if (recData.status != 0) {
                    logger.warning("Status: " + recData.status); // Check RT Status
                    testPassStatus[DeviceConstants.TADK_RT_TO_BC][recData.subAddress] = false;
                } else {
                    testPassStatus[DeviceConstants.TADK_RT_TO_BC][recData.subAddress] = true;
                    for (int i = 0; i < recData.data.length; i++) {
                        if (recData.data[i] != testData[i]) {
                            testPassData[DeviceConstants.TADK_RT_TO_BC][recData.subAddress] = false;
                            logger.severe("Incorrect data, SA: " + recData.subAddress + "data expected at index: "
                                    + i + ": " + testData[i] + ", but received: " + recData.data[i]);
                        }
                    }
                }
                break;
            case DeviceConstants.TADK_RESPONSE_SUCCESSFULL_RX_BOCAT:
                // Broadcast command executed successfully.
                break;
            case DeviceConstants.TADK_RESPONSE_CONFIG_SUCCESS:
                // TADK configured successfully.
                break;
            case DeviceConstants.TADK_RESPONSE_SUCCESSFULL_MODE_RT_BC:
                // Modecode command executed successfully.
                break;
            case DeviceConstants.TADK_RESPONSE_FAILED_RX_BC_RT:
                testPassResponse[DeviceConstants.TADK_BC_TO_RT][recData.subAddress] = false;
                logger.severe(" BC-RT Command at subaddress: " + (recData.subAddress) + " failed");
                // BC->RT command timedout. RT Failed to respond. Check if RT is turned
                // on,configure to the right address, and Bus is rightly connected.
                break;
            case DeviceConstants.TADK_RESPONSE_FAILED_TX_RT_BC:
                testPassResponse[DeviceConstants.TADK_RT_TO_BC][recData.subAddress] = false;
                logger.severe(" RT-BC Command at subaddress: " + (recData.subAddress) + " failed");
                // RT->BC command timedout. RT Failed to respond. Check if RT is turned
                // on,configure to the right address, and Bus is rightly connected.
                break;
            case DeviceConstants.TADK_RESPONSE_CONFIG_FAILED:
                logger.severe(" Configuration failed");
                // Configuration command failed, check if the configuration message was
                // constructed right.
                break;
            case DeviceConstants.TADK_RESPONSE_FAILED_MODE_RT_BC:
                logger.severe(" ModeCode Command  failed");
                // Modecode command timedout. RT Failed to respond. Check if RT is turned
                // on,configure to the right address, and Bus is rightly connected.
                break;
            case DeviceConstants.TADK_RESPONSE_GEN_ERROR:
                // check if the message was constructed right.
                break;
            default:
                break;
        }
    }
    /**
     * @param args[] none
     * @throws UnknownHostException
     * @throws IOException
     * @throws FailedToConfigure
     */
    public static void main(String args[]) throws UnknownHostException, IOException, FailedToConfigure { 
        int count = 0;
        int testingRTAddress = 13;
        DeviceData[] messages = new DeviceData[62];
        final int[] modeCodes = new int[] {
            DeviceConstants.SYNCHRONIZE_WITHOUT_DATA,
            DeviceConstants.TRANSMIT_STATUS_WORD,
            DeviceConstants.INITIATE_SELF_TEST,
            DeviceConstants.TRANSMIT_VECTOR_WORD,
            DeviceConstants.SYNCHRONIZE,
            DeviceConstants.TRANSMIT_LAST_COMMAND,
            DeviceConstants.TRANSMIT_BIT_WORD        
        };
        FileHandler fh = null;
        try {
            // This block configure the logger with handler and formatter
            fh = new FileHandler(logFile);
            logger.addHandler(fh);
            System.setProperty("java.util.logging.SimpleFormatter.format",
            "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %4$-7s [%3$s] %5$s %6$s%n");
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);   
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        //construct the messages to be sent
        for(int i=0;i<30;i++)
            messages[i] = new DeviceData(testingRTAddress,i+1,DeviceConstants.TADK_BC_TO_RT,testData);
        for(int i=30;i<60;i++)
            messages[i] = new DeviceData(testingRTAddress,i-29,DeviceConstants.TADK_RT_TO_BC,31);
        try{
            //Connect to TCP Server TADK
            BusController bc = new BusController(ip, port);
            logger.info(" Connected to "+ip+":"+port);
		
            while(true) {
                if(count==averageWindow)
                {
                    count=0;
                    logger.info(" Message Counter: "+msgCounter+", Error Counter: "+errCounter+", Average time: "+ totalTime/averageWindow +" us");
                    totalTime=0;
					
                    for(int sa=1;sa<31;sa++)
                    {
                        if(testPassResponse[DeviceConstants.TADK_BC_TO_RT][sa]==false)
                            logger.warning(" BC-RT Command at subaddress: "+(sa)+" failed");
                        if(testPassResponse[DeviceConstants.TADK_RT_TO_BC][sa]==false)
                            logger.warning(" RT->BC Command at subaddress: "+(sa)+" failed");
                        if(testPassStatus[DeviceConstants.TADK_BC_TO_RT][sa]==false)
                            logger.warning(" BC-RT Command at subaddress: "+(sa)+" failed");
                        if(testPassStatus[DeviceConstants.TADK_RT_TO_BC][sa]==false)
                            logger.warning(" RT->BC Command at subaddress: "+(sa)+" failed");
                    }
                }
				
				//////////////////TODO Change is in here//////////////////
				//update data every 50 milliseconds
				if(count%5 == 0)
                {
					for(int k = 0; k < testData.length; k++) {
						if(testData[k] >= 1000) {
							testData[k] = 1;
						}
						testData[k]++;
					}
				}
				//////////////////TODO End Change is in here//////////////////	
				
                long start = System.nanoTime();
                //30 (BC->RT) messages and 30(RT->BC) messages
                for(int i=0;i<30;i++)
                {
                    DeviceData recData = bc.queryRt(
                        messages[i]
                    );  
                    responseProcess(recData);
                    if(recData.response>=0)
                        msgCounter++;
                    else    
                        errCounter++;
                    Thread.sleep(1);
                }
                for(int i=30;i<60;i++)
                {
                    DeviceData recData = bc.queryRt(
                        messages[i]
                    );  
                    responseProcess(recData);
                    if(recData.response>=0)
                        msgCounter++;
                    else    
                        errCounter++;
                }
                //modecodes
                for(int i=0;i<modeCodes.length;i++)
                {
                    DeviceData recData = bc.queryModeCode(
                        testingRTAddress,
                        modeCodes[i]
                    );    
                    responseProcess(recData);
                    if(recData.response>=0) msgCounter++;
                    else    errCounter++;
                }   
                //BroadCast command 
                DeviceData recData =bc.broadcast(new DeviceData(testingRTAddress,1,DeviceConstants.TADK_BC_TO_RT,testData));            
                responseProcess(recData);
                if(recData.response>=0) msgCounter++;
                else    errCounter++;
                long end = System.nanoTime();
                long timeSpent = (end - start) / 1000;
                totalTime+=timeSpent;
             //   System.out.println("timeSpent : "+timeSpent);
                count++;
            }
        } catch(Exception e){
            e.printStackTrace(); 

			while(true) {try{Thread.sleep(1);}catch(Exception e2){}}
        }
    }

}
