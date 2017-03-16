package org.assignments.webcrawler.service.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.assignments.webcrawler.AbstractTest;
import org.assignments.webcrawler.constants.LinkType;
import org.assignments.webcrawler.domain.Page;
import org.assignments.webcrawler.exceptions.WebpageGrabException;
import org.assignments.webcrawler.service.CrawlerService;
import org.assignments.webcrawler.sitemap.impl.SitemapBuilderImpl;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author BMC
 *
 */
@PrepareForTest({Jsoup.class})
@RunWith(PowerMockRunner.class)
public class CrawlerServiceImplTest extends AbstractTest {

    @Mock SitemapBuilderImpl sitemapBuilder;
    @Mock Jsoup jsoup;
    @Mock Connection conn;
    @Mock Response response;

    private CrawlerService service;

    /**
     * Junit test case setup 
     * @throws Exception - problem setting up test mocks
     */
    @SuppressWarnings({"unchecked" })
    @Before
    public void setUp() throws Exception {
        super.setUpTests();
        when(this.sitemapBuilder.getUrl()).thenReturn(MY_DOMAIN_URL);
        
        when(Boolean.valueOf(this.sitemapBuilder.writeSimpleOutputToFile(
                        any(FileOutputStream.class), 
                        any(Page.class), 
                        any(String.class))))
        .thenReturn(Boolean.TRUE);
        when(Boolean.valueOf(this.sitemapBuilder.buildSitemap(any(Map.class))))
        .thenReturn(Boolean.TRUE);
        when(this.document.select(any(String.class))).thenReturn(new Elements());
        when(this.document.select(eq(LinkType.HYPER.toString()))).thenReturn(this.hyperlinkElements);
        when(this.document.select(eq(LinkType.SCRIPT.toString()))).thenReturn(this.resourceElements);
        when(this.response.parse()).thenReturn(this.document);
        when(this.conn.execute()).thenReturn(this.response);
        
        
        PowerMockito.mockStatic(Jsoup.class);
        PowerMockito.when(Jsoup.connect(Matchers.anyString())).
            thenReturn(this.conn);
        PowerMockito.when(Jsoup.connect(Matchers.anyString()).get()).
        thenReturn(this.document);

        this.service = new CrawlerServiceImpl(this.sitemapBuilder);
    }

    /**
     * Test method for 
     * {@link org.assignments.webcrawler.service.impl.CrawlerServiceImpl#buildSiteMap()}.
     * 
     * This method only checks that the process completed successfully.  
     * IT does not check that the output is correct.  More time needed to write those tests.
     *   
     * @throws WebpageGrabException - not thrown
     * @throws FileNotFoundException - not thrown
     */
    @Test
    public final void testBuildSiteMap() throws FileNotFoundException, WebpageGrabException {
        assertTrue(this.service.buildSiteMap());
    }
    
    /**
     * @throws WebpageGrabException - Cannot retrieve the Web page
     * @throws IOException - Should not reach the test.
     */
    @Test
    public final void testBuildSiteMapIOExceptionStillCompletes() 
                    throws WebpageGrabException, IOException {
        when(this.conn.execute()).thenThrow(new IOException());
        assertTrue(this.service.buildSiteMap());
    }

}
