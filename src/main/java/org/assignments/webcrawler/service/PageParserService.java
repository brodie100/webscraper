/**
 * 
 */
package org.assignments.webcrawler.service;

import org.assignments.webcrawler.domain.Page;
import org.jsoup.nodes.Document;

/**
 * @author BMC
 *
 */
public interface PageParserService {

    /**
     * @param document - The the web page to be processed as used by JSoup
     * @return Page - The fully parsed page with links in Internal/External/Resource groups
     */
    public Page parsePage(final Document document);
}
