package TADK;

public class FailedToConfigure extends Exception{  
    
    private static final long serialVersionUID = 1L;

    /**
     * Exception thrown once TAD Device fails to 
     * configure.
     * @param type
     */
    FailedToConfigure(String type) {
     super(type);  
    }  
}  