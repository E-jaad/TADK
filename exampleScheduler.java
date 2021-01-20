
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import TADK.*;
class exampleScheduler extends TimerTask {
    private String name;
    BusController bc;
    ConcurrentLinkedQueue q;
    DeviceData[] messages = new DeviceData[8];
    static int totalNumberOfCommands=8;
    static int testingRTAddress=13;
    public exampleScheduler(String name,BusController bc,ConcurrentLinkedQueue q) {
        this.name = name;
        this.bc=bc;
        this.q=q;
        messages[0] = new DeviceData(testingRTAddress,1,DeviceConstants.TADK_BC_TO_RT,new short[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31}); // Rt address 1 sub address 1 (RX) 31 words
        messages[1] = new DeviceData(testingRTAddress,2,DeviceConstants.TADK_BC_TO_RT,new short[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31}); // Rt address 1 sub address 1 (RX) 31 words
        messages[2] = new DeviceData(testingRTAddress,3,DeviceConstants.TADK_BC_TO_RT,new short[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31}); // Rt address 1 sub address 1 (RX) 31 words
        messages[3] = new DeviceData(testingRTAddress,4,DeviceConstants.TADK_BC_TO_RT,new short[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31}); // Rt address 1 sub address 1 (RX) 31 words
        messages[4] = new DeviceData(testingRTAddress,1,DeviceConstants.TADK_RT_TO_BC,31);  // Rt address 1 sub address 1 (TX) 31 words
        messages[5] = new DeviceData(testingRTAddress,2,DeviceConstants.TADK_RT_TO_BC,31); // Rt address 1 sub address 2 (TX) 31 words
        messages[6] = new DeviceData(testingRTAddress,3,DeviceConstants.TADK_RT_TO_BC,31); // Rt address 1 sub address 3 (TX) 31 words  
        messages[7] = new DeviceData(testingRTAddress,4,DeviceConstants.TADK_RT_TO_BC,31); // Rt address 1 sub address 3 (TX) 31 words  
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
                        q.add(res);
                    }  
                    long end = System.nanoTime();
                    //System.out.println("timeSpent for Major Frame(8 Minor frames): "+((double)(end - start) / 1000000)+" mSec");
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