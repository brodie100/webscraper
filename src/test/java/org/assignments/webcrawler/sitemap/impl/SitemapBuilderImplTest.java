/**
 * 
 */
package org.assignments.webcrawler.sitemap.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.assignments.webcrawler.AbstractTest;
import org.assignments.webcrawler.domain.Page;
import org.assignments.webcrawler.exceptions.SitemapIndexBuilderException;
import org.assignments.webcrawler.sitemap.SitemapBuilder;
import org.junit.Test;

/**
 * @author BMC
 *
 */
@SuppressWarnings("javadoc")
public class SitemapBuilderImplTest extends AbstractTest {

    private SitemapBuilder builder = new SitemapBuilderImpl(MY_DOMAIN_URL);
    
    @Test
    public final void testBuildSitemap() throws SitemapIndexBuilderException {
        this.page = new Page(buildLinks(MY_DOMAIN, 10), 
                      //buildLinks("http://www.outsidesite.com", 5), 
                        Collections.emptySet(),
                        buildResourceLinks(MY_DOMAIN, 7));
        Map<String, Page> pages = new HashMap<>();
        pages.put(MY_DOMAIN, this.page);
        
        IntStream.rangeClosed(0, 9).forEach(count -> { 
            String name = MY_DOMAIN.concat("/").concat(String.valueOf(count));
            pages.put(name, new Page(buildLinks(MY_DOMAIN, 10), 
                            //buildLinks("http://www.outsidesite.com", 5), 
                            Collections.emptySet(),
                            buildResourceLinks(MY_DOMAIN, 7)));
        
        });
        this.builder.buildSitemap(pages);
        // TODO: Verify the file output
    }
    
    @Test
    public final void testBuildSitemapWithBadURLsIsSuccessful() throws SitemapIndexBuilderException {
        this.page = new Page(buildBadLinks(10), 
                        Collections.emptySet(),
                        buildResourceLinks(MY_DOMAIN, 7));
        Map<String, Page> pages = new HashMap<>();
        pages.put(MY_DOMAIN, this.page);
        this.builder.buildSitemap(pages);
        // TODO: Verify the file output
    }

}
