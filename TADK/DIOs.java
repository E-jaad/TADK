package TADK;

import java.io.IOException;

public class DIOs {
    RemoteTerminal rt;
    public DIOs(RemoteTerminal rt) {
        this.rt = rt;
    }
    public void setUnsetDO(short DOnumber, Boolean direction) throws IOException {
        short[] data = new short[]{DOnumber};
        if(direction)     
            this.rt.writeData(DeviceConstants.TADK_COMMAND_CONFIG,DeviceConstants.TADK_ACTION_SET_DIOS,data);
        else{
            this.rt.writeData(DeviceConstants.TADK_COMMAND_CONFIG,DeviceConstants.TADK_ACTION_UNSET_DIOS,data);
        }
    }
    public void mapDIOstoData(short type,short number,short sa,short wordNumber,short bitNumber)throws IOException {
        short[] data = new short[]{type, number, sa, wordNumber, bitNumber };
        this.rt.writeData(DeviceConstants.TADK_COMMAND_CONFIG,DeviceConstants.TADK_ACTION_MAP_DIOS,data);
    }
    public void unmapDIOstoData(short type,short number,short sa,short wordNumber,short bitNumber) throws IOException {
        short[] data = new short[]{type,number, sa, wordNumber, bitNumber };
        this.rt.writeData(DeviceConstants.TADK_COMMAND_CONFIG,DeviceConstants.TADK_ACTION_UNMAP_DIOS,data);
    }
}
