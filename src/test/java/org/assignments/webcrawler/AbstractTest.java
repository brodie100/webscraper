package org.assignments.webcrawler;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.assignments.webcrawler.domain.Page;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author BMC
 *
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractTest {
    
    protected static final String MY_DOMAIN = "http://www.mysite.com";
    protected static final String NOT_MY_DOMAIN = "http://www.notmysite.com";
    protected static final String PNG_URL = MY_DOMAIN.concat("/pic.png");
    protected static final String STYLESHEETS_URL = MY_DOMAIN.concat("/styles.css");
    protected static URL MY_DOMAIN_URL;
    @Mock 
    protected Document document;
    protected Attributes srcAttributes = new Attributes() {{
        //Bad practice, cutting corners due to time overrun... :(
       this.put(new Attribute("src", STYLESHEETS_URL));
       this.put(new Attribute("src", PNG_URL)); 
    }};
    protected Attributes hrefExternalAttributes = new Attributes() {{
        this.put(new Attribute("href", NOT_MY_DOMAIN)); 
    }};
    protected Attributes hrefAttributes = new Attributes() {{
        this.put(new Attribute("href", MY_DOMAIN)); 
    }};
    
    protected Element hyperlinkElement;
    protected Element externalElement;
    protected Elements hyperlinkElements;

    protected Element resourceElement;
    protected Elements resourceElements;
    protected Page page = new Page();
    
    {
        try {
            MY_DOMAIN_URL = new URL(MY_DOMAIN);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            //fail calling tests:
            assertEquals("Problem Creating URL for tests.", "");
        }
    }
    
    protected void setUpTests() {
        this.externalElement = 
                        new Element(Tag.valueOf("a"), NOT_MY_DOMAIN, this.hrefExternalAttributes);
        this.hyperlinkElement = new Element(Tag.valueOf("a"), MY_DOMAIN, this.hrefAttributes);
        this.resourceElement = new Element(Tag.valueOf("script"), MY_DOMAIN, this.srcAttributes);
        
        this.hyperlinkElements = new Elements(this.hyperlinkElement, this.externalElement);
        this.resourceElements = new Elements(this.resourceElement);
    }
    
    protected static Set<String> buildLinks(String url, int iterate) {
        Set<String> links = new HashSet<>();
        for(int i = 0; i < iterate; i++) {
            links.add(url.concat("/").concat(String.valueOf(i)));
        }
        return links;
    }
    
    protected static Set<String> buildBadLinks(int iterate) {
        Set<String> links = new HashSet<>();
        for(int i = 0; i < iterate; i++) {
            links.add("htt://".concat("/").concat(String.valueOf(i)));
        }
        return links;
    }
    
    protected static Set<String> buildResourceLinks(String url, int iterate) {
        Set<String> links = new HashSet<>();
        for(int i = 0; i < iterate; i++) {
            links.add(url.concat("/")
                            .concat(String.valueOf(i))
                            .concat(i % 2 == 0 ? ".css" : ".png"));
        }
        return links;
    }
}
