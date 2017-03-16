package org.assignments.webcrawler.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author BMC    
 *
 */
public final class Page {
    private Set<String> internalLinks;
    private Set<String> externalLinks;
    private Set<String> resourceLinks;
    
    
    /**
     * Creates an Instance with empty sets for internal, external and resource links
     */
    public Page() {
        //Default all links to empty Sets.
        this(new HashSet<>(), new HashSet<>(), new HashSet<>());
    }
    
    /**
     * @param internalLinks - Links to pages within the target domain
     * @param resourceLinks - Links to resources within the target domain
     */
    public Page(Set<String> internalLinks, Set<String> resourceLinks) {
        this(internalLinks, resourceLinks, new HashSet<>());
    }
    
    /**
     * @param internalLinks - Links to pages within the target domain
     * @param resourceLinks - Links to resources within the target domain
     * @param externalLinks - Links to pages outside the target domain
     */
    public Page(Set<String> internalLinks, Set<String> resourceLinks, Set<String> externalLinks) {
        // At lease two Sets must be supplied
        this.internalLinks = internalLinks;
        this.externalLinks = externalLinks;
        this.resourceLinks = resourceLinks;
    }
    
    
    /**
     * @return Set<String> - Links to pages within the target domain
     */
    public Set<String> getInternalLinks() {
        return this.internalLinks;
    }
    
    /**
     * @return Set<String> - Links to external pages outside the target domain
     */
    public Set<String> getExternalLinks() {
        return this.externalLinks;
    }
    
    /**
     * @return Set<String> - Links to resources within the target domain
     */
    public Set<String> getResourceLinks() {
        return this.resourceLinks;
    }
    
    
    
}
