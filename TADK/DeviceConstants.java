
package TADK;

/**
 *
 * @author E-JAAD
 */
/**
 * 
 * This class defines the constants for application.
 */
public class DeviceConstants {

    /**
     * The GET command is used to retrieve data from the device. 
     * If TADK is configured as BC, this will generate RT->BC command
     */
    public static final int TADK_COMMAND_GET = 0;
    /**
     * The SET command is used to set transmit data on the device.
     * If TADK is configured as BC, this will generate BC->RT command.
     */
    public static final int TADK_COMMAND_SET = 1;
    /**
     * CONFIG command is used to configure the device and to set traps in BM mode.
     */
    public static final int TADK_COMMAND_CONFIG = 2;
    /**
     * RT to RT command is used for RT to RT transfers. It is only applicable to a device configured as a BC. 
     */
    public static final int TADK_COMMAND_RTRT = 3;
    /**
     * Failed to process command.
     */
    public static final int TADK_RESPONSE_GEN_ERROR = -100;
    /**
    * Timeout:Mode Code command failed.
    */
    public static final int TADK_RESPONSE_FAILED_MODE_RT_BC = -5;
    /**
     * Failed to Configure.
     */
    public static final int TADK_RESPONSE_CONFIG_FAILED = -4;
    /**
     * Broadcast command failed.
     */
    public static final int TADK_RESPONSE_FAILED_RX_BOCAT = -3;
    /**
     * Timeout: RT->BC Command Failed.
     */
    public static final int TADK_RESPONSE_FAILED_TX_RT_BC = -2;
    /**
     * Timeout: BC->RT Command Failed.
     */
    public static final int TADK_RESPONSE_FAILED_RX_BC_RT = -1;
    /**
     * Auto update response from Bus Monitor
     */
    public static final int TADK_RESPONSE_AUTO_UPDATE = 0;
    /**
     * Response from RT was received successfully for BC->RT Command.
     */
    public static final int TADK_RESPONSE_SUCCESSFULL_RX_BC_RT = 1;
    /**
     * Response from RT was received successfully for RT->BC Command.
     */
    public static final int TADK_RESPONSE_SUCCESSFULL_TX_RT_BC = 2;
    /**
     * Broadcast Command was successful.
     */
    public static final int TADK_RESPONSE_SUCCESSFULL_RX_BOCAT = 3;
    /**
     * Successfully Configured.
     */
    public static final int TADK_RESPONSE_CONFIG_SUCCESS = 4;
    /**
     * ModeCode transaction was successful.
     */
    public static final int TADK_RESPONSE_SUCCESSFULL_MODE_RT_BC = 5;
    /**
     *  Transmit/Receive from Rx or Tx Vectors
     */
    public static final int TADK_ACTION_VECT        = 0;
    /**
     * GET/SET Mode Codes
     */
    public static final int TADK_ACTION_MODE        = 1;
    /**
     * Configure device as a Bus Controller
     */
    public static final int TADK_ACTION_SET_BC      = 2;
    /**
     * Configure device as a Remote Terminal
     */
    public static final int TADK_ACTION_SET_RT      = 3;
    /**
     * Configure device as a Bus Monitor
     */
    public static final int TADK_ACTION_SET_BM      = 4;
    /**
     * Set Trap for Bus Monitor.A trap will set a specific message to be recorded by the BM.
     */
    public static final int TADK_ACTION_SETTRAP	    = 5;
    /**
     * Unset trap for Bus Monitor. Un-setting a trap will remove that specific message to be recorded by the BM.
     */
    public static final int TADK_ACTION_UNSETTRAP   = 6;
    /**
     * Enable BM. To Start recording on BM.
     */
    public static final int TADK_ACTION_BM_ENABLE   = 7;
    /**
     * Disable BM. To Stop recoding on BM.
     */
    public static final int TADK_ACTION_BM_DISABLE  = 8;
    /**
     *  To send a broadcast command.
     */
    public static final int TADK_ACTION_BRODCAST    = 10;
    /**
     * Set Busy Bit in status word.
     */
    public static final int TADK_ACTION_RT_HOST_INI	= 12;
    /**
     * Unset Busy Bit in status word.
     */
    public static final int TADK_ACTION_RT_HOST_INIT_COMPLETE	=13;
    /**
     *  Set a specific address as legal.
     */
    public static final int TADK_ACTION_SET_LEGAL_ADDRESS		=14;
    /**
     * Set a specific address as illegal.
     */
    public static final int TADK_ACTION_SET_ILLEGAL_ADDRESS     =15;
    /**
     * To map digital inputs/outputs (DIOS) to a particular sub-address.
     */
    public static final int TADK_ACTION_MAP_DIOS				=16;
    /**
     * To un-map  digital inputs/outputs (DIOS) from a particular sub-address.
     */
    public static final int TADK_ACTION_UNMAP_DIOS				=17;
    /**
     * To set(high) the specific Digital Output.
     */
    public static final int TADK_ACTION_SET_DIOS				=18;
    /**
     * To Unset(Low) the specific digital output.
     */
    public static final int TADK_ACTION_UNSET_DIOS				=19;
    /**
     * Set TxRx Bit to 0 for BC to RT command
     */
    public static final int TADK_BC_TO_RT                       =0;
    /**
     * Set TxRx Bit to 1 for RT to BC command
     */
    public static final int TADK_RT_TO_BC                       =1;

    /**
     * 
     */
    public static final int SYNCHRONIZE_WITHOUT_DATA		=1;
    public static final int TRANSMIT_STATUS_WORD 			=2;
    public static final int INITIATE_SELF_TEST 				=3;
    public static final int TRANSMITTER_SHUTDOWN			=4;
    public static final int OVERRIDE_TRANSMITTER_SHUTDOWN 	=5;
    public static final int RESET_REMOTE_TERMINAL			=8;
    public static final int TRANSMIT_VECTOR_WORD 			=16;
    public static final int SYNCHRONIZE    					=17;
    public static final int TRANSMIT_LAST_COMMAND 			=18; 
    public static final int TRANSMIT_BIT_WORD     			=19; 

}
