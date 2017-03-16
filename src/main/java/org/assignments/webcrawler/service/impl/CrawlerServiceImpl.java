package org.assignments.webcrawler.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.assignments.webcrawler.domain.Page;
import org.assignments.webcrawler.exceptions.SitemapIndexBuilderException;
import org.assignments.webcrawler.exceptions.WebpageGrabException;
import org.assignments.webcrawler.service.CrawlerService;
import org.assignments.webcrawler.service.PageParserService;
import org.assignments.webcrawler.sitemap.SitemapBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author BMC
 */
public final class CrawlerServiceImpl implements CrawlerService {

    private static final Logger LOG = LoggerFactory.getLogger(CrawlerServiceImpl.class);
    // Map to remove duplicates but maintain all links associate with that page
    private Map<String, Page> visitedLinkMap = new HashMap<>();
    private List<String> linksToVisitList = new ArrayList<>();
    private SitemapBuilder sitemapBuilder;
    private URL url;
    

    @SuppressWarnings("unused")
    private CrawlerServiceImpl() {
        // Stop instantiation with no URL
    }
    
    /**
     * @param builder - Instance of the SitemapBuilder implementation to use
     */
    public CrawlerServiceImpl(SitemapBuilder builder) {
        this.url = builder.getUrl();
        this.sitemapBuilder = builder;
    }
    
    
    @Override
    public boolean buildSiteMap() throws WebpageGrabException {
        Optional<String> nextUrlOptional = Optional.empty();
        
        try (FileOutputStream output = new FileOutputStream(
                            new File("src/test/resources/testSite.out"), 
                            false)) {
            nextUrlOptional = Optional.of(this.url.toString());
            while(nextUrlOptional.isPresent()) {
                Page parsedPage = processLink(nextUrlOptional.get());
                this.sitemapBuilder.writeSimpleOutputToFile(output, parsedPage);
                nextUrlOptional = nextUrl();
                output.flush();
            }
            output.close();
            //this.sitemapBuilder.buildSitemap(this.visitedLinkMap);
        } catch (IOException e) {
            LOG.error("Problem Retrieving Web Page. ", e);
            throw new WebpageGrabException(nextUrlOptional.orElse("N/A"), e);
        } 
//        catch (SitemapIndexBuilderException e) {
//            LOG.error("Problem building the sitemap. ", e);
//            e.printStackTrace();
//        }
        
        return true;
    }
    
    
    protected Page processLink(String target) throws IOException {
        Document document = Jsoup.connect(target).get();
       
        Page parsedPage = new Page();
        PageParserService parser = new PageParserServiceImpl(parsedPage, this.url);
        parser.parsePage(document);
        /* add target to Map at the end to account for failures and allowing reevaluation if it 
         * were to be built in later.
         */
        this.linksToVisitList.addAll(parsedPage.getInternalLinks());
        this.visitedLinkMap.put(target, parsedPage);
        return parsedPage;
    }

    /*
     * Recursively calls itself to retrieve the next URL.
     */
    private Optional<String> nextUrl() {
        Optional<String> nextUrl = this.linksToVisitList.isEmpty() ? 
        		Optional.empty() :
        			Optional.of(this.linksToVisitList.get(0));
        if (nextUrl.isPresent() && (this.visitedLinkMap.get(nextUrl.get()) != null)) {
	        this.linksToVisitList.remove(0); //Not fault tolerant
	        nextUrl = nextUrl();
        }
        return nextUrl;
    }
    

}
