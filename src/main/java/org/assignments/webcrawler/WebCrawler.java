package org.assignments.webcrawler;

import java.net.URL;
import java.util.Optional;
import java.util.Scanner;

import org.assignments.webcrawler.constants.OutputMessages;
import org.assignments.webcrawler.service.CrawlerService;
import org.assignments.webcrawler.service.impl.CrawlerServiceImpl;
import org.assignments.webcrawler.sitemap.impl.SitemapBuilderImpl;
import org.assignments.webcrawler.utilities.InputUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main Application
 *
 */
public class WebCrawler {

    /**
     * @param args - This Main method does not need arguments at instantiation
     */
    public static void main(String[] args ) {
        final Logger LOG = LoggerFactory.getLogger(WebCrawler.class);
        try (Scanner input = new Scanner(System.in)) {
            System.out.println(OutputMessages.PROMPT);
            CrawlerService service;
            while (input.hasNextLine()) {
                String inputString = input.nextLine();
                
                if (!inputString.isEmpty() && inputString.equals(OutputMessages.EXIT))
                    System.exit(0);
                
                if (InputUtilities.validateInput(inputString)) {
                    Optional<URL> url = InputUtilities.getURLObject(inputString);
                    if (url.isPresent()) {
                        service = new CrawlerServiceImpl(new SitemapBuilderImpl(url.get()));
                        service.buildSiteMap();
                    }
                    //LOG.debug(OutputMessages.FINISHED_PROCESSING);
                    System.out.println(OutputMessages.FINISHED_PROCESSING);
                } else 
                    System.out.println(OutputMessages.INVALID_INPUT);
                
                System.out.println(OutputMessages.PROMPT);
            }
            
        } catch (Exception e) {
            //One time only print no need to create StringBuilder.
            String newLine = System.getProperty("line.separator");
            LOG.error(String.format("Unhandled exception occurred. %s %s %s %s", 
                            newLine,
                            "Please report this problem to your system maintainers. \r",
                            newLine, 
                            "System Exiting..."), e);
            
        }
    }  


}
