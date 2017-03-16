package org.assignments.webcrawler.service;

import java.io.FileNotFoundException;

import org.assignments.webcrawler.exceptions.WebpageGrabException;

/**
 * @author BMC
 *
 */
public interface CrawlerService {
    
    /**
     * Starts the process for crawling a Web Domain.
     * @return boolean - true if the operation completed successfully
     * @throws WebpageGrabException - Exception indicating that the document could not be retrieved 
     * @throws FileNotFoundException - Encountered if the Sitemap cannot be created
     */
    boolean buildSiteMap() throws WebpageGrabException, FileNotFoundException;
}
