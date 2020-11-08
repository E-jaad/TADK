package TADK;

public class DeviceConstants {
    public static final int TADK_COMMAND_GET = 0;
    public static final int TADK_COMMAND_SET = 1;
    public static final int TADK_COMMAND_CONFIG = 2;
    public static final int TADK_COMMAND_RTRT = 3;

    public static final int TADK_BUS_CONTROLLER=2;
    public static final int TADK_REMOTE_TERMINAL=3;
    public static final int TADK_BUS_MONITOR=4;
    public static final int TADK_RESPONSE_GEN_ERROR = -100;
    public static final int TADK_RESPONSE_GEN_SUCCESS = 100;
    public static final int TADK_RESPONSE_ISBC_NO_UPDATE = -6;
    public static final int TADK_RESPONSE_FAILED_MODE_RT_BC = -5;
    public static final int TADK_RESPONSE_CONFIG_FAILED = -4;
    public static final int TADK_RESPONSE_FAILED_RX_BOCAT = -3;
    public static final int TADK_RESPONSE_FAILED_TX_RT_BC = -2;
    public static final int TADK_RESPONSE_FAILED_RX_BC_RT = -1;
    public static final int TADK_RESPONSE_AUTO_UPDATE = 0;
    public static final int TADK_RESPONSE_SUCCESSFULL_RX_BC_RT = 1;
    public static final int TADK_RESPONSE_SUCCESSFULL_TX_RT_BC = 2;
    public static final int TADK_RESPONSE_SUCCESSFULL_RX_BOCAT = 3;
    public static final int TADK_RESPONSE_CONFIG_SUCCESS = 4;
    public static final int TADK_RESPONSE_SUCCESSFULL_MODE_RT_BC = 5;

    public static final int TADK_ACTION_VECT        = 0;
    public static final int TADK_ACTION_MODE        = 1;	
    public static final int TADK_ACTION_SET_BC      = 2;
    public static final int TADK_ACTION_SET_RT      = 3;
    public static final int TADK_ACTION_SET_BM      = 4;
    public static final int TADK_ACTION_SETTRAP	    = 5;
    public static final int TADK_ACTION_UNSETTRAP   = 6;
    public static final int TADK_ACTION_BM_ENABLE   = 7;
    public static final int TADK_ACTION_BM_DISABLE  = 8;
    public static final int TADK_ACTION_BRODCAST    = 10;
    public static final int TADK_ACTION_SELF_TEST	= 11;

    public static final int TADK_ACTION_RT_HOST_INI	= 12;
    public static final int TADK_ACTION_RT_HOST_INIT_COMPLETE	=13;
    public static final int TADK_ACTION_SET_LEGAL_ADDRESS		=14;
    public static final int TADK_ACTION_SET_ILLEGAL_ADDRESS     =15;

    public static final int TADK_ACTION_MAP_DIOS				=16;
    public static final int TADK_ACTION_UNMAP_DIOS				=17;
    public static final int TADK_ACTION_SET_DIOS				=18;
    public static final int TADK_ACTION_UNSET_DIOS				=19;
}
