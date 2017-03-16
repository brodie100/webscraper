/**
 * 
 */
package org.assignments.webcrawler.constants;

import java.util.Optional;


/**
 * @author BMC
 *
 */
@SuppressWarnings("javadoc")
public enum LinkType {

    AREA("area[href]", "href", LinkGroupingType.LINK),
    AUDIO("audio[src]", "src", LinkGroupingType.STATIC_RESOURCE),
    BLOCKQUOTE("blockquote[cite]", "cite", LinkGroupingType.LINK),
    EMBED("embed[src]", "src", LinkGroupingType.STATIC_RESOURCE),
    //FRAME Ommitted if HTML4 is needed then it would be added.
    HYPER("a[href]", "href", LinkGroupingType.LINK),
    IFRAME("iframe[src]", "src", LinkGroupingType.LINK),
    IMG("img[src]", "src", LinkGroupingType.STATIC_RESOURCE),
    INPUT("inupt[src]", "src", LinkGroupingType.STATIC_RESOURCE),
    INS("ins[cite]", "cite", LinkGroupingType.STATIC_RESOURCE),
    LINK("link[href]", "href", LinkGroupingType.STATIC_RESOURCE),
    Q("q[cite]", "href", LinkGroupingType.STATIC_RESOURCE),
    SCRIPT("script[src]", "src", LinkGroupingType.STATIC_RESOURCE),
    SOURCE("source[src]", "src", LinkGroupingType.STATIC_RESOURCE),
    TRACK("track[src]", "src", LinkGroupingType.STATIC_RESOURCE),
    VIDEO("video[src]", "src", LinkGroupingType.STATIC_RESOURCE);
    
    
    private String cssSelector;
    private String urlAttribute;
    private LinkGroupingType linkGroup;
    
    private LinkType(String cssSelector, String urlAttribute, LinkGroupingType linkGroup) {
        this.cssSelector = cssSelector;
        this.urlAttribute = urlAttribute;
        this.linkGroup = linkGroup;
    }
    
    @Override
    public String toString() {
        return this.cssSelector;
    }
    
    /**
     * @param text - text selector of link enum
     * @return boolean - representing if the text passed in relates to a real function
     */
    public static boolean isValidFunction(String text) {
        for (LinkType linkType : LinkType.values())
            if (linkType.cssSelector.equals(text))
                return true;
       return false;
    }
    
    /**
     * @param text - text selector of link enum
     * @return Optional<LinkType> - represents the Link enum relating to the text passed in
     */
    public static Optional<LinkType> returnFunction(String text) {
        for (LinkType function : LinkType.values())
            if (function.cssSelector.equals(text))
                return Optional.of(function);
       return Optional.empty();
    }

    public LinkGroupingType getLinkGroup() {
        return this.linkGroup;
    }
    
    public String getUrlAttribute() {
        return this.urlAttribute;
    }

}
