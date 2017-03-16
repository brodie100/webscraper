package org.assignments.webcrawler.exceptions;

import java.net.MalformedURLException;

/**
 * @author BMC
 *
 */
public class SitemapIndexBuilderException extends Exception {

    private static final long serialVersionUID = -282681263432608364L;
    private static final String MESSAGE = 
                    "Failed to create Sitemap Index file.  File location incorrect";
    
    
    @SuppressWarnings("unused")
    private SitemapIndexBuilderException(){
        //Do not use
    }
    
    
    /**
     * Creates a throwable exception with a default message.
     * 
     * @param e - The MAlformedURL exception that happened due to a bad file location URL.
     */
    public SitemapIndexBuilderException(MalformedURLException e) {
        super(MESSAGE, e);
    }

}
