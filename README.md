# Synopsis

This is a simple java application that scrapes all links of a page and documents them in a file.
It also has features to build a Sitemap XML file but this is not finished.

It uses JSoup to help with the Webcrawling and Google's old sitemapgen4j which has been forked
by dfabulich and actively updated on GitHub.com.


 
 

## The actual problem according to the assignment
### Task
Your task is to write a simple web crawler in a language of your choice.
* The crawler should be limited to one domain. 
* Given a starting URL it should visit all pages within the domain.
* It should not follow the links to external sites such as Google or Twitter.

### The Output
A simple site map, showing links to:
  - other pages under the same domain
  - links to static content such as images
  - Links to external URLs

### Task Requirements
* Time Limit of a couple of hours (This extended due to complications with Mocking JSoup and Sitemap4J issues)
* Ensure that what you do implement is production quality code
* Briefly describe any tradeoffs you make through comments and / or in a README file
* Include the steps needed to build and run your solution

***
# Considerations
To crawl the website domain it would have been possible to use an HTTP connection client and manage 
the responses and parse out the data myself.  To do this would have been more time consuming so 
the decision was made to use some well established libraries that already provide this functionality.


There were two aspects to this, webcrawling and official Sitemap generation:

## Web Crawlers (Java Only):

- BUbiNG          
New open-source project looks interesting, if scale and higher performance was a consideration for 
NFR's, this may have been worth investigating further.
- Apache Nutch
Not Chosen for it's amount of dependencies and seems heavy weight for this sort of exercise
- JSpider
Not supported well and no community.  Las update 2013 and seems to have defects
- Jaunt
Licensing issues may arise as it's not fully free, free licenses expire quickly
- Heritrix
Apache license and Stable since Jan 2014 but not work continuing on it.  Well regarded and used.

Chosen one:
- JSoup
MIT license and good support on StackOverflow and still regular releases.
CSS Selector method as opposed to some DSL.

### Sitemap Generator (Java Only)
- jsitemapgenerator
Based of Google's version also but not actively maintained.  
License BSD 3 Clause not great.



Chosen one:
- sitemapgen4j (dfabulich)
Based on the original Google code but still actively supported.  Referenced from the archived code 
repository on Googles codebase.
Extendible to generate Google specific sitemaps also.
Officially understood by Yahoo, Google and Bing.

### Improvements

Links are stored in memory so memory limitations would affect the stability of this application 
if it was played against a very large site.  Breaking this out into small batches of iterations 
would help with managing memory uses and writing the file as progress is made.

Logging should be improved.

Testing should be greatly improved.  Naming of test cases should follow a standardised pattern

### Running the application

To run on the command line:

~~~~
> cd <java file location>
> java WebCrawler
~~~~

You will then be prompted for a URL to crawl.
Enter this URL and press return.

To exit the program type `exit` and hit return.

### Test Coverage 
Jacoco was used.  It would need further configuration to put quality gates in place.

Test coverage is c.78% for all classes.  This should be fine tuned and also many more edge cases are
needed.  Time ran out in completing all testing.

Some tests were created to output the sitemap to `output/` Sitemap URLs are importance weighted at 
0.8 for actual webpages and 0.3 for Static assets.  Output of external URL's are not allowed in 
the XML sitemap. 

Some tests output the Textual version of the sitemap to `src/test/resources`.
All output files are overwritten with each test execution to avoid clutter.