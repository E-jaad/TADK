import java.util.*;
import TADK.*;
public class exampleTCPRespProcess implements Runnable {
    Queue q;
    public exampleTCPRespProcess(Queue q) {
        this.q=q;
    }
    public void decodeResponse(DeviceData recData)
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
                System.out.print("Data for RT->BC at SA: "+recData.subAddress);
                System.out.print(": { " );
                for(int index=0;index<recData.data.length-1;index++){
                    System.out.print(+recData.data[index]+", ");
                }
                System.out.println(recData.data[recData.data.length-1]+" }");
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
        while(true)
        {
            try {
                if(q.size()>0){
                    // if(q.size()>3000)
                    //     System.out.println("size: "+q.size());
                    DeviceData r=(DeviceData)q.poll();
                    if(r!=null)
                        decodeResponse(r);
                }
                else{
                    Thread.sleep(2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
 }
    