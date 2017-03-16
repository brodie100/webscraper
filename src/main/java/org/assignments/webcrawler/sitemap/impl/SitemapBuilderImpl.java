package org.assignments.webcrawler.sitemap.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.assignments.webcrawler.domain.Page;
import org.assignments.webcrawler.exceptions.SitemapIndexBuilderException;
import org.assignments.webcrawler.sitemap.SitemapBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;

/**
 * @author BMC
 * Should be final class
 */
public class SitemapBuilderImpl implements SitemapBuilder {
    
    private static final String URL_ERROR = "Tried to write a URL that was not well formed.";
    private static final byte [] NEW_LINE = System.lineSeparator().getBytes();
    private static final int  MAX_SITEMAP_URLS = 1000;
    
    private URL url;
    
    String host;
    private static final Logger LOG = LoggerFactory.getLogger(SitemapBuilderImpl.class);
    
    @SuppressWarnings("unused")
    private SitemapBuilderImpl() {
        //No plain instantiation
    }
    
    /**
     * @param url - Base URL for the site map.
     */
    public SitemapBuilderImpl(URL url) {
        this.url = url;
        this.host = url.getHost();
    }
    

    @Override
    public boolean buildSitemap(Map<String, Page> pageMap) throws SitemapIndexBuilderException {              
        WebSitemapGenerator generator = 
                        WebSitemapGenerator
                        .builder(this.url, new File("output"))
                        .fileNamePrefix("sitemap_".concat(this.url.getHost()))
                        .maxUrls(MAX_SITEMAP_URLS)
                        .allowMultipleSitemaps(true)
                        .build();
        
        pageMap.forEach((key, page) -> processPage(generator, page));
        generator.write();
        generator.writeSitemapsWithIndex();
        return true;
    }
    
    private static void processPage(WebSitemapGenerator generator, Page page) {
        buildFileWithPriorities(generator, page.getInternalLinks(), 0.8);
        buildFileWithPriorities(generator, page.getResourceLinks(), 0.3);
        buildFileWithPriorities(generator, page.getExternalLinks(), 0.1);
        
    }
    
    private static void buildFileWithPriorities(WebSitemapGenerator generator, 
                    Set<String> urls, 
                    double priority) {
        
        urls.forEach(linkUrl -> {
            try {
                WebSitemapUrl websiteMapUrl = 
                            new WebSitemapUrl
                            .Options(linkUrl)
                            .priority(Double.valueOf(priority))
                            .build();
                generator.addUrl(websiteMapUrl);
            } catch (MalformedURLException e) {
                //Swallow to continue mapping the rest? (Shouldn't happen this deep down.)
                LOG.error(URL_ERROR, e);
            }
            
        });
    }
    
    @Override
    public boolean writeSimpleOutputToFile(FileOutputStream output, Page page) {
        page.getInternalLinks().stream().forEach(urlString -> writeToOutput(output, urlString, 0));
        page.getResourceLinks().stream().forEach(urlString -> writeToOutput(output, urlString, 1));
        page.getExternalLinks().stream().forEach(urlString -> writeToOutput(output, urlString, 2));
        try {
            output.write(NEW_LINE);
            output.write(NEW_LINE);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return true;
    }
    
    private static void writeToOutput(FileOutputStream output, String text, int indent) {
        String indentString = getIndent(indent);
        try {
            if (!indentString.isEmpty()) 
                output.write(indentString.getBytes());
            
            output.write(text.getBytes());
            output.write(NEW_LINE);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
    
    private static String getIndent(int indent) {
        String indentString;
        switch(indent) {
            case 1:
                indentString = "\t";
                break;
            case 2:
                indentString = "\t\t";
                break;
            default:
                indentString = "";
                break;   
        }
        return indentString;
    }
    

    @Override
    public URL getUrl() {
        return this.url;
    }
}
