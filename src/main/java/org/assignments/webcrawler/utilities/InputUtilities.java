package org.assignments.webcrawler.utilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author BMC
 *
 */
public final class InputUtilities {
    private static final Logger LOG = LoggerFactory.getLogger(InputUtilities.class);
    private static final String HTTP = "http";
    private static final String[] schemes = {"http","https"};
    private static final UrlValidator urlValidator = new UrlValidator(schemes);
    private static final DomainValidator domainValidator = DomainValidator.getInstance();
    
    
    private InputUtilities() {
        /*
         * Stop Instantiating. Static Methods Only. Deliberately not a singleton.
         */
    }
    
    /**
     * Allows only one input URL/domain
     * @param input - String array from the initialisation of the crawler process
     * @return boolean - user input was valid 
     */
    public static boolean validateInput(String input) {
        if (input.isEmpty())
            return false;

        String [] argsList = input.split("\\s+");
        if (1 != argsList.length)
            return false;
        return validateAndReturnURL(argsList[0]).isPresent();
    }
    
    
    /**
     * @param urlString         - 
     * @return Optional<URL>     - If the URL is valid it will return a URL 
     *                            object in the optional.  Otherwise it is 
     *                            Optional.absent()
     */
    public static Optional<URL> getURLObject(String urlString) {
        return validateAndReturnURL(urlString);
    }
    
    
    /**
     * This method is contentious as it would need updated (or the dependencies updated)
     * when a new domain list is released/put up for sale.
     * 
     * @param url
     * @return boolean - Whether this is a fully valid URL with Domain also verified
     */
    private static Optional<URL> validateAndReturnURL(String urlString) {
        Optional<URL> returnUrl = Optional.empty();
        
            
        if (!urlValidator.isValid(urlString))
            if (urlString.startsWith(HTTP))
                return returnUrl;
            else
                urlString = HTTP.concat("://").concat(urlString);
                if (!urlValidator.isValid(urlString))
                    return returnUrl;
        
        try {
            URL url = new URL(urlString);
            if (domainValidator.isValid(url.getHost()))
                returnUrl = Optional.of(url);
        } catch (MalformedURLException e) {
            LOG.error(e.getMessage(), e);
        }
        return returnUrl;
    }
    
}
