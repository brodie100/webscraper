## Synopsis

This is an exercise to create a Sample Checkout application that takes in a function name 
and a number of items (Currently set to 4 as they are typed) which it then processes against 
a static list of discounts to work out the total cost of the basket 

### The actual problem according to the assignment
Task
Your task is to write a simple web crawler in a language of your choice.
* The crawler should be limited to one domain. 
* Given a starting URL it should visit all pages within the domain.
* It should not follow the links to external sites such as Google or Twitter.

The Output 
+ A simple site map, showing links to 
  - other pages under the same domain
  - links to static content such as images
  - Links to external URLs

Task Requirements
* Time Limit of a couple of hours
* Ensure that what you do implement is production quality code
* Briefly describe any tradeoffs you make through comments and / or in a README file
* Include the steps needed to build and run your solution

***
## Considerations


Web Crawlers (Java Only):

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
MIT license and ggood support on StackOverflow and still regular releases.
CSS Selector method as opposed to some DSL.

### Improvements

Links are stored in memory so memory limitations would affect the stability of this application 
if it was played against a very large site.


 

### Running the application - Two Modes

Running the application 

