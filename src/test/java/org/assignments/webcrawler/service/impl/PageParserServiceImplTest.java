package org.assignments.webcrawler.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.assignments.webcrawler.AbstractTest;
import org.assignments.webcrawler.constants.LinkType;
import org.assignments.webcrawler.domain.Page;
import org.assignments.webcrawler.service.PageParserService;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
/**
 * @author BMC
 *
 */
public class PageParserServiceImplTest extends AbstractTest {
   
    private final PageParserService service = new PageParserServiceImpl(this.page, MY_DOMAIN_URL);

    
    /**
     * Call setup in Abstract class
     */
    @Before
    public void setUp() {
        super.setUpTests();
        
        when(this.document.select(any(String.class))).thenReturn(new Elements());
        when(this.document.select(eq(LinkType.HYPER.toString()))).thenReturn(this.hyperlinkElements);
        when(this.document.select(eq(LinkType.SCRIPT.toString()))).thenReturn(this.resourceElements);
    }


    /**
     * Test method for 
     * {@link org.assignments.webcrawler.service.impl.PageParserServiceImpl#PageParserServiceImpl(org.assignments.webcrawler.domain.Page, java.net.URL)}.
     */
    @Test
    public final void testParsePage() {
        Page parsedPage = this.service.parsePage(this.document);
        assertEquals(MY_DOMAIN, parsedPage.getInternalLinks().iterator().next());
        assertEquals(NOT_MY_DOMAIN, parsedPage.getExternalLinks().iterator().next());
        assertEquals(PNG_URL, parsedPage.getResourceLinks().iterator().next());
    }


    /**
     * Test method for 
     * {@link org.assignments.webcrawler.service.impl.PageParserServiceImpl#parsePage(org.jsoup.nodes.Document)}.
     */
    @Test
    public final void testParsePageEmpty() {
        Document documentEmpty = mock(Document.class);
        when(documentEmpty.select(any(String.class))).thenReturn(new Elements());
        Page parsedPage = this.service.parsePage(documentEmpty);
        assertTrue(parsedPage.getInternalLinks().isEmpty());
        assertTrue(parsedPage.getExternalLinks().isEmpty());
        assertTrue(parsedPage.getInternalLinks().isEmpty());
    }

}
