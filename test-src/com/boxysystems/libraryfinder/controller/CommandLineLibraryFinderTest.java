package com.boxysystems.libraryfinder.controller;

import com.boxysystems.libraryfinder.model.AbstractLibraryFinderTest;
import com.boxysystems.libraryfinder.model.Constants;
import com.boxysystems.libraryfinder.model.LibraryFinderException;
import com.boxysystems.libraryfinder.view.CommandLineView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

/**
 * Unit Tests for CommandLineLibraryFinder
 *
 * @author Siddique Hameed
 * @since May 28, 2007
 */
public class CommandLineLibraryFinderTest extends AbstractLibraryFinderTest {

    private PrintStream originalSOUTStream;
    private ByteArrayOutputStream redirectedSystemOutStream;

    protected void setUp() throws Exception {
        super.setUp();
        originalSOUTStream = System.out;
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        if (redirectedSystemOutStream != null) {
            setOriginalOutputStream();
        }
    }

    private void redirectSystemOut() {
        redirectedSystemOutStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(redirectedSystemOutStream, true));
    }

    private String getRedirectedSystemOutString() {
        if (redirectedSystemOutStream != null) {
            return redirectedSystemOutStream.toString();
        }
        return "";
    }

    private void setOriginalOutputStream() {
        System.setOut(originalSOUTStream);
    }

    public void testFindLibraryBasicWithoutArgs() throws Exception {
        String args[] = {};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertEquals(CommandLineLibraryFinder.USAGE_TEXT, getRedirectedSystemOutString().trim());

    }


    public void testFindLibraryBasic() throws Exception {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {testJarParentDir.getAbsolutePath(), "org.apache.log4j.Logger.class"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(testLog4jJar.getAbsolutePath()));

    }

    public void testFindLibraryBasicInVerboseMode() throws Exception {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {"-verbose", testJarParentDir.getAbsolutePath(), "org.apache.log4j.Logger.class"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(Constants.SEARCH_MSG_PREFIX + testJarParentDir.getAbsolutePath()));
        assertTrue(getRedirectedSystemOutString().contains(testLog4jJar.getAbsolutePath()));

    }

    public void testFindLibraryForNonExistingClassFile() throws LibraryFinderException {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {testJarParentDir.getAbsolutePath(), "org.apache.log4j.Logger123.class"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(CommandLineView.RESULT_NOT_FOUND_MSG));
    }

    public void testFindLibraryForClassFile_CaseInsensitiveByDefault() throws LibraryFinderException {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {testJarParentDir.getAbsolutePath(), "org.apache.log4j.logger"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(testLog4jJar.getAbsolutePath()));
    }

    public void testFindLibraryForClassFile_CaseSensitive() throws LibraryFinderException {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {"-caseSensitive", testJarParentDir.getAbsolutePath(), "org.apache.log4j.logger"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(CommandLineView.RESULT_NOT_FOUND_MSG));
    }
    
    public void testFindLibraryForClassFile_WithPackageImportAtTheEnd() throws LibraryFinderException {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {testJarParentDir.getAbsolutePath(), "org.apache.log4j.*"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(testLog4jJar.getAbsolutePath()));
    }

    public void testFindLibraryForClassFile_WithPackageImportAtTheMiddle() throws LibraryFinderException {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {testJarParentDir.getAbsolutePath(), "org.*.log4j.*"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(testLog4jJar.getAbsolutePath()));
    }

    public void testFindLibraryForClassFile_StarWildcard() throws LibraryFinderException {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {testJarParentDir.getAbsolutePath(), "org.apache.log4j.*.class"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(testLog4jJar.getAbsolutePath()));
    }

    public void testFindLibraryForClassFile_StarWildcardForExtension() throws LibraryFinderException {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {testJarParentDir.getAbsolutePath(), "org.apache.log4j.Logger*"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(testLog4jJar.getAbsolutePath()));
    }

    public void testFindLibraryForClassFile_QuestionMarkWildcard() throws LibraryFinderException {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {testJarParentDir.getAbsolutePath(), "org.apache.log4j.Logge?.class"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(testLog4jJar.getAbsolutePath()));
    }

    public void testFindLibraryForClassFile_StarAndQuestionMark() throws LibraryFinderException {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {testJarParentDir.getAbsolutePath(), "org.apache.log4?.*.class"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(testLog4jJar.getAbsolutePath()));
    }

    public void testFindLibraryForClassFile_WithForwardSlashes() throws LibraryFinderException {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {testJarParentDir.getAbsolutePath(), "org/apache/log4j/Logger.class"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(testLog4jJar.getAbsolutePath()));
    }

    public void testFindLibraryForClassFiles_Regex() throws LibraryFinderException {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {"-regex", testJarParentDir.getAbsolutePath(), "org/apache/log4j/Logger.class|org/apache/log4j/lf5/lf5/properties"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(testLog4jJar.getAbsolutePath()));
    }

    public void testFindLibraryForClassFile_WithForwardSlashes_WithoutSuffix() throws LibraryFinderException {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {testJarParentDir.getAbsolutePath(), "org/apache/log4j/Logger"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(testLog4jJar.getAbsolutePath()));
    }

    public void testFindLibraryForClassFile_WithForwardSlashes_WithoutSuffix_StarWildcard() throws LibraryFinderException {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {testJarParentDir.getAbsolutePath(), "org/apache/*/Logger"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(testLog4jJar.getAbsolutePath()));
    }

    public void testFindLibraryForClassFile_WithBackwardSlashes() throws LibraryFinderException {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {testJarParentDir.getAbsolutePath(), "org\\apache\\log4j\\Logger.class"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(testLog4jJar.getAbsolutePath()));
    }

    public void testFindLibraryForClassFile_WithBackwardSlashes_StarWildcard() throws LibraryFinderException {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {testJarParentDir.getAbsolutePath(), "org\\*\\log4j\\Logger.class"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(testLog4jJar.getAbsolutePath()));
    }

    public void testFindLibraryForClassFile_WithBackwardSlashes_WithoutSuffix() throws LibraryFinderException {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {testJarParentDir.getAbsolutePath(), "org\\apache\\log4j\\Logger"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(testLog4jJar.getAbsolutePath()));
    }

    public void testFindLibraryForPropertiesFile_WithPeriodReturnsNothing() throws LibraryFinderException {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {testJarParentDir.getAbsolutePath(), "org.apache.log4j.lf5.lf5.properties"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(CommandLineView.RESULT_NOT_FOUND_MSG));
    }

    public void testFindLibraryForPropertiesFile_StarWildcard() throws LibraryFinderException {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {testJarParentDir.getAbsolutePath(), "org/apache/log4j/lf5/*.properties"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(testLog4jJar.getAbsolutePath()));
    }

    public void testFindLibraryForPropertiesFile_QuestionMarkWildcard() throws LibraryFinderException {
        File testJarParentDir = testLog4jJar.getParentFile();
        String args[] = {testJarParentDir.getAbsolutePath(), "???/apache/log4j/lf5/*.properties"};
        redirectSystemOut();
        CommandLineLibraryFinder.main(args);
        setOriginalOutputStream();
        assertTrue(getRedirectedSystemOutString().contains(testLog4jJar.getAbsolutePath()));
    }
}

