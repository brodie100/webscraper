package org.assignments.webcrawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author BMC
 *
 */
@SuppressWarnings({"static-method", "javadoc"})
public class WebCrawlerTest extends AbstractTest {

    private static final InputStream ORIGINAL_INPUT = System.in;
    private static final PrintStream ORIGINAL_OUTPUT = System.out;
    private PrintStream outputStream;
    /**
     * @throws Exception - Catches JUnit setup if problems creating 
     *                               files
     */ 
    @Before
    public void setUp() throws Exception {
        final FileInputStream fileIn = 
                        new FileInputStream(new File("src/test/resources/testSite.in"));
        this.outputStream = 
                        new PrintStream(
                                        new FileOutputStream(
                                                        new File("src/test/resources/testSite.out"), 
                                                        false));
        System.setIn(fileIn);
        System.setOut(this.outputStream);
    }
    
    
    /**
     * Set System.in/out back to normal
     */
    @After
    public void tearDown() {
        System.setIn(ORIGINAL_INPUT);
        System.setOut(ORIGINAL_OUTPUT);
    }


    @Test
    public final void testWebCrawlerMainClass() {
        WebCrawler.main(null);
        assert(this.outputStream.checkError() != true);
    }

}
