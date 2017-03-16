package org.assignments.webcrawler.service.impl;

import java.net.URL;

import org.assignments.webcrawler.constants.LinkType;
import org.assignments.webcrawler.domain.Page;
import org.assignments.webcrawler.service.PageParserService;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author BMC
 */
public final class PageParserServiceImpl implements PageParserService {

    private static final Logger LOG = LoggerFactory.getLogger(PageParserServiceImpl.class);
    private static final String UNPROCESSIBLE_LINK = 
                    "Fell outside the bounds of the defined logic for identifying links.  URL: %s";
    
    private Page page;
    private String host;
    
    
    @SuppressWarnings("unused")
    private PageParserServiceImpl() {
        // Stop instantiation with no URL
    }
    
    
    /**
     * @param page - the page object that will be used to collect the parsed links
     * @param url - URL of current Website 
     */
    public PageParserServiceImpl(Page page, URL url) {
        // Stop instantiation with no URL
        this.page = page;
        this.host = url.getHost();
    }
    
    
    /*
     * See Interface PageParserService
     */
    @Override
    public Page parsePage(final Document document) {
        for (LinkType linkType : LinkType.values()) {
            processSpecificElement(document.select(linkType.toString()), linkType);
            
        }
        return this.page;
    }
    
    
    private Page processSpecificElement(Elements links, LinkType linkType) {
        links.stream().forEach(link -> {
            //make all URLs absolute, updating relative URLs.
            String fullUrl = link.absUrl(linkType.getUrlAttribute()); 
            if (!addLink(fullUrl, linkType) ) { 
                // Continue processing after logging a record. 
                LOG.error(String.format(UNPROCESSIBLE_LINK, fullUrl));
            }
        });
        
        return this.page;
    }
    
    
    private boolean addLink(String link, LinkType linkType) {
        boolean result = false;
        switch(linkType.getLinkGroup()) {
            case LINK:
                if (addInternalLink(link) || 
                                addExternalLink(link) || 
                                addResourceLink(link)) { 
                    result = true;
                } 
                break;
            case STATIC_RESOURCE:
                if (addExternalLink(link) || addResourceLink(link)) { 
                    result = true;
                } 
                break;
            default:
                break;
            
        }
        return result;
    }
    
    /*
     * Returns True if the item was added to the Set.  It does not signify that
     * whether the item was already in the Set or not.
     * Expects an Absolute URL
     */
    private boolean addInternalLink(String link) {
        if (!link.isEmpty() && link.contains(this.host)) {
            this.page.getInternalLinks().add(link);
            return true;
        } 
        return false;
    }
    
    /*
     * Returns True if the item was added to the Set.  It does not signify that
     * whether the item was already in the Set or not.
     * Expects an Absolute URL
     */
    private boolean addExternalLink(String link) {
        if (!link.isEmpty() && !link.contains(this.host)) {
            this.page.getExternalLinks().add(link);
            return true;
        } 
        return false;
    }
    
    /*
     * Returns True if the item was added to the Set.  It does not signify that
     * whether the item was already in the Set or not.
     * Assumes the item to be added has been predetermined to be a resource link
     * Expects an Absolute URL
     */
    private boolean addResourceLink(String link) {
        if (!link.isEmpty() && link.contains(this.host)) {
            this.page.getResourceLinks().add(link);
            return true;
        } 
        return false;
    }

}
