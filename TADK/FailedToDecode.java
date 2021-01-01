package TADK;

public class FailedToDecode extends Exception{  
    
    private static final long serialVersionUID = 1L;

    /**
     * Exception thrown if the device raw message
     * fails to decode
     * @param message
     */
    FailedToDecode(String message) {
     super(message);  
    }  
}  