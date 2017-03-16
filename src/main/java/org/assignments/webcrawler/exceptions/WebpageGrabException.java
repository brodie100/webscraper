package org.assignments.webcrawler.exceptions;

/**
 * @author BMC
 *
 */
public class WebpageGrabException extends Exception {
    private static final long serialVersionUID = -630285056106585384L;
    private static final String reason = "Failed To get Webpage: %s";
    
    
    @SuppressWarnings("unused")
    private WebpageGrabException() {
        // Must specify URL
    }
    
    /**
     * @param targetURL - String URL attempted
     */
    public WebpageGrabException(String targetURL) {
        super(String.format(reason, targetURL));
    }
    
    /**
     * @param targetURL - String URL attempted
     * @param exception - The actual exception caught
     */
    public WebpageGrabException(String targetURL, Throwable exception) {
        super(String.format(reason, targetURL), exception);
    }

}
