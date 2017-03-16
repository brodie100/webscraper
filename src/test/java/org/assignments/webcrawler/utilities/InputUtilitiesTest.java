/**
 * 
 */
package org.assignments.webcrawler.utilities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.Optional;

import org.junit.Test;

/**
 * @author BMC
 *
 */
@SuppressWarnings({"static-method", "javadoc"})
public class InputUtilitiesTest {


    @Test
    public final void testValidateInput() {
        assertTrue(InputUtilities.validateInput("www.google.co.uk"));
    }
    
    @Test
    public final void testValidateInputMultipleArgs() {
        assertFalse(InputUtilities.validateInput("www.google.co.uk asfdsafadsjhf"));
    }
    
    
    @Test
    public final void testValidateInputHttp() {
        assertTrue(InputUtilities.validateInput("http://www.google.co.uk"));
    }

    
    @Test
    public final void testValidateInputHttps() {
        assertTrue(InputUtilities.validateInput("https://www.google.co.uk"));
    }
    
    @Test
    public final void testValidateInputMalformed() {
        assertFalse(InputUtilities.validateInput("htps://www.google.co.uk"));
    }
    
    @Test
    public final void testValidateInputMalformedHost() {
        assertFalse(InputUtilities.validateInput("https://www.google!$.co.uk"));
    }
    
    @Test
    public final void testValidateInputFTP() {
        assertFalse(InputUtilities.validateInput("ftp://www.google.co.uk"));
    }
    
    @Test
    public final void testValidateInputBlank() {
        assertFalse(InputUtilities.validateInput(""));
    }
    
    @Test
    public final void testGetURLObjectBlank() {
        assertEquals(Optional.<URL>empty(), InputUtilities.getURLObject(""));
    }
    
    @Test
    public final void testGetURLObjectSuccess() {
        assertEquals(URL.class, 
                        InputUtilities.getURLObject("http://www.google.co.uk").get().getClass());
    }
    
    @Test
    public final void testGetURLObjectSuccessWithoutProtocol() {
        assertEquals(URL.class, InputUtilities.getURLObject("www.google.co.uk").get().getClass());
    }
}
