/**
 * 
 */
package org.assignments.webcrawler.constants;

/**
 * @author BMC
 * This is used to determine the Link Type to avoid processing if we already know it is a Resource
 */
@SuppressWarnings("javadoc")
public enum LinkGroupingType {
    LINK,
    STATIC_RESOURCE;
}
