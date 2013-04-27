package com.boxysystems.libraryfinder.model;

import junit.framework.TestCase;
import org.apache.log4j.BasicConfigurator;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Feb 2, 2007
 * Time: 1:56:46 PM
 */
public abstract class AbstractLibraryFinderTest extends TestCase {

    protected LibraryFinderResult log4jLibraryFinderResult = null;
    protected LibraryFinderResult abbotJarLibraryFinderResult = null;
    protected File testLog4jJar = new File("test-src//log4j.jar");
    protected File testAbbotJar = new File("test-src//abbot.jar");


    static {
        BasicConfigurator.configure();
    }


    protected void setUp() throws Exception {
        assertTrue("The test jar file " + testLog4jJar.getAbsolutePath() + " doesn't exist", testLog4jJar.exists());
        log4jLibraryFinderResult = new LibraryFinderResult(testLog4jJar.getAbsolutePath(), testLog4jJar.length());
        assertTrue("The test jar file " + testAbbotJar.getAbsolutePath() + " doesn't exist", testAbbotJar.exists());
        abbotJarLibraryFinderResult = new LibraryFinderResult(testAbbotJar.getAbsolutePath(), testAbbotJar.length());
    }

    protected void failMe() {
        fail("Shouldn't be here");
    }

}

