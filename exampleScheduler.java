import java.util.*;
import TADK.*;

class exampleScheduler extends TimerTask {
    private String name;
    BusController bc;
    DeviceData[] messages = new DeviceData[6];
    static long msgCounter=0;
    static int msgIndex=0;
    public exampleScheduler(String name,BusController bc) {
        this.name = name;
        this.bc=bc;
        messages[0] = new DeviceData(13,1,DeviceConstants.TADK_RT_TO_BC,31);  // Rt address 1 sub address 1 (TX) 31 words
        messages[1] = new DeviceData(13,2,DeviceConstants.TADK_RT_TO_BC,31); // Rt address 1 sub address 2 (TX) 31 words
        messages[2] = new DeviceData(13,3,DeviceConstants.TADK_RT_TO_BC,20); // Rt address 1 sub address 3 (TX) 20 words 
        messages[3] = new DeviceData(13,1,DeviceConstants.TADK_BC_TO_RT,new short[]{1,2,3,4,5}); // Rt address 1 sub address 1 (RX) 5 words 
        messages[4] = new DeviceData(13,2,DeviceConstants.TADK_BC_TO_RT,new short[]{1,2,3,4,5,6,7,8,9,10}); // Rt address 1 sub address 1 (RX) 10 words 
        messages[5] = new DeviceData(13,3,DeviceConstants.TADK_BC_TO_RT,new short[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31}); // Rt address 1 sub address 1 (RX) 31 words 
    }
    public void run() {

        switch(name)
        {
            case "50ms":
                try {
                    DeviceData res=null;
                    for(int i=0;i<6;i++){
                        res = bc.queryRt(
                            messages[i]
                        );
                         if(res.response<0)
                             System.out.println("failed command, error code: "+res.response);
                        if(res.response>0){
                            msgCounter++;
                            System.out.println("msgCounter: "+msgCounter);
                        }
                    }  
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