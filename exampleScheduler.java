
import java.util.*;
import TADK.*;

class exampleScheduler extends TimerTask {
    private String name;
    BusController bc;
    DeviceData[] messages = new DeviceData[8];
    static int totalNumberOfCommands=8;
    static int testingRTAddress=15;
    public exampleScheduler(String name,BusController bc) {
        this.name = name;
        this.bc=bc;
        messages[0] = new DeviceData(testingRTAddress,1,DeviceConstants.TADK_BC_TO_RT,new short[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31}); // Rt address 1 sub address 1 (RX) 31 words
        messages[1] = new DeviceData(testingRTAddress,2,DeviceConstants.TADK_BC_TO_RT,new short[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31}); // Rt address 1 sub address 1 (RX) 31 words
        messages[2] = new DeviceData(testingRTAddress,3,DeviceConstants.TADK_BC_TO_RT,new short[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31}); // Rt address 1 sub address 1 (RX) 31 words
        messages[3] = new DeviceData(testingRTAddress,4,DeviceConstants.TADK_BC_TO_RT,new short[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31}); // Rt address 1 sub address 1 (RX) 31 words
        messages[4] = new DeviceData(testingRTAddress,1,DeviceConstants.TADK_RT_TO_BC,31);  // Rt address 1 sub address 1 (TX) 31 words
        messages[5] = new DeviceData(testingRTAddress,2,DeviceConstants.TADK_RT_TO_BC,31); // Rt address 1 sub address 2 (TX) 31 words
        messages[6] = new DeviceData(testingRTAddress,3,DeviceConstants.TADK_RT_TO_BC,31); // Rt address 1 sub address 3 (TX) 31 words  
        messages[7] = new DeviceData(testingRTAddress,4,DeviceConstants.TADK_RT_TO_BC,31); // Rt address 1 sub address 3 (TX) 31 words  
    }
    private static void responseProcess(DeviceData recData)
    {
        switch(recData.response)
        {
            case DeviceConstants.TADK_RESPONSE_SUCCESSFULL_RX_BC_RT:
                //BC->RT command  executed successfully. 
            break;
            case DeviceConstants.TADK_RESPONSE_SUCCESSFULL_TX_RT_BC:
                //RT->BC command  executed successfully.
                //System.out.println("Status: " + recData.status); //Check RT Status
                //Print Data Words
                // System.out.println("Data for SA: "+recData.subAddress);
                // for(int index=0;index<recData.data.length;index++){
                //     System.out.println("DataWord[" + index + "]: "+recData.data[index]);
                // }
            break;
            case DeviceConstants.TADK_RESPONSE_SUCCESSFULL_RX_BOCAT:
            //Broadcast command  executed successfully.
            break;
            case DeviceConstants.TADK_RESPONSE_CONFIG_SUCCESS:
            //TADK configured successfully.
            break;
            case DeviceConstants.TADK_RESPONSE_SUCCESSFULL_MODE_RT_BC:
            //Modecode command executed successfully.
            //Print Data Words
            // for(int index=0;index<recData.data.length;index++){
            //     System.out.println("DataWord[" + index + "]: "+recData.data[index]);
            // }
            break;
            case DeviceConstants.TADK_RESPONSE_FAILED_RX_BC_RT:
                System.out.println("BC-RT Command failed at SA: "+recData.subAddress);
            //BC->RT command timedout. RT Failed to respond. Check if RT is turned on,configure to the right address, and Bus is rightly connected.
            break;
            case DeviceConstants.TADK_RESPONSE_FAILED_TX_RT_BC:
                System.out.println("RT->BC Command failed at SA: "+recData.subAddress);
            //RT->BC command timedout. RT Failed to respond. Check if RT is turned on,configure to the right address, and Bus is rightly connected.
            break;
            case DeviceConstants.TADK_RESPONSE_CONFIG_FAILED:
            // Configuration command failed, check if the configuration message was constructed right.
            break;
            case DeviceConstants.TADK_RESPONSE_FAILED_MODE_RT_BC:
            // Modecode command timedout. RT Failed to respond. Check if RT is turned on,configure to the right address, and Bus is rightly connected.
            break;
            case DeviceConstants.TADK_RESPONSE_GEN_ERROR:
            // check if the message was constructed right.
            break;
            default:
            break;
        }
    }
    public void run() {
        switch(name)
        {
            case "50ms":
                try {
                    long start = System.nanoTime();
                    DeviceData res=null;
                    for(int commandNumber=0;commandNumber<totalNumberOfCommands;commandNumber++)
                    {
                        res = bc.queryRt(
                            messages[commandNumber]
                        );
                        responseProcess(res);
                    }  
                    long end = System.nanoTime();
                    System.out.println("timeSpent for Major Frame(8 Minor frames): "+((double)(end - start) / 1000000)+" mSec");
                } catch (Exception e) {
                    e.printStackTrace();   
                }
            break;
            default:
                System.out.println("[" + new Date() + "] " + name + ": task executed!");
            break;
        }
    }
 }