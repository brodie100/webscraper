package org.assignments.webcrawler.sitemap;

import java.io.FileOutputStream;
import java.net.URL;
import java.util.Map;

import org.assignments.webcrawler.domain.Page;
import org.assignments.webcrawler.exceptions.SitemapIndexBuilderException;

/**
 * @author BMC
 *
 */
public interface SitemapBuilder {
    /**
     * This builds the site map output once all details have been collected.
     * @param pageMap - All page elements with a collection of URLs located on the page
     * @return boolean - Successful run
     * @throws SitemapIndexBuilderException - If Index file cannot be created
     * 
     */
    boolean buildSitemap(Map<String, Page> pageMap) throws SitemapIndexBuilderException;

    /**
     * Used to write out a file of the sitemap in simple text.
     * This was needed so that the output could contain external links.
     * JSoup did not allow external links in an official sitemap.xml document
     * @param output - The output stream used to write the contents to.
     * @param page - the page to add to the file.
     * @param baseUrl - The current URL that is being parsed and documented
     * @return boolean - success 
     */
    boolean writeSimpleOutputToFile(FileOutputStream output, Page page, String baseUrl);
    
    /**
     * @return URL - URL object representing the base URI
     */
    URL getUrl();
}
