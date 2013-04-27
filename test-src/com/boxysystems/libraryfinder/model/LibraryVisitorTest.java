package com.boxysystems.libraryfinder.model;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Unit Test for Library Visitor
 *
 * @author : Siddique Hameed
 * @since : May 23, 2007
 */

public class LibraryVisitorTest extends AbstractLibraryFinderTest {


    public void testVisitNonExistingDirectory() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery("jakdjaks", "akajsd");
        LibraryVisitor visitor = new LibraryVisitor(query);
        try {
            visitor.visitLibraries();
            failMe();
        } catch (LibraryFinderException e) {
            assertEquals(Constants.PATH_NOT_FOUND_MESSAGE, e.getMessage());
        }
    }

    public void testVisitFileInsteadOfDirectory() throws LibraryFinderException {
        try {
            File[] files = new File(".").listFiles();
            int i = 0;
            for (; i < files.length; i++) {
                if (!files[i].isDirectory()) {
                    break;
                }
            }
            if (i + 1 == files.length) {
                fail("Couldn't find file from the current directory");
            }

            LibraryFinderQuery query = new LibraryFinderQuery(files[i].getPath(),"abcd");
            LibraryVisitor visitor = new LibraryVisitor(query);
            visitor.visitLibraries();
            failMe();
        } catch (LibraryFinderException e) {
            assertEquals(Constants.PATH_NOT_VALID_MESSAGE, e.getMessage());
        }
    }

    public void testSearchDirectoryForClassFile() throws LibraryFinderException, IOException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org.apache.log4j.Logger.class");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> library = visitor.getResults();
        assertEquals(1, library.size());
        LibraryFinderResult actualResult = library.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }

    public void testSearchDirectoryForClassFile_InputWithWhitespace() throws LibraryFinderException, IOException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath()+" ", " org.apache.log4j.Logger.class ");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> library = visitor.getResults();
        assertEquals(1, library.size());
        LibraryFinderResult actualResult = library.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForClassFile_CaseInsensitiveByDefault() throws LibraryFinderException, IOException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org.apache.log4j.logger.class");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> library = visitor.getResults();
        assertEquals(1, library.size());
        LibraryFinderResult actualResult = library.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForClassFile_CaseSensitive() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org.apache.log4j.logger");
        query.setCaseSensitive(true);
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> library = visitor.getResults();
        assertEquals(0, library.size());
    }

    public void testFindLibraryForClassFile_WithPackageImportAtTheEnd() throws LibraryFinderException, IOException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org.apache.log4j.*");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }


    public void testFindLibraryForClassFile_WithPackageImportAtTheMiddle() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org.*.log4j.*");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForClassFile_StarWildcard() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org.apache.log4j.*.class");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForClassFile_StarWildcardForExtension() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org.apache.log4j.Logger*");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForClassFile_QuestionMarkWildcard() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org.apache.log4j.Logge?.class");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForClassFile_StarAndQuestionMark() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org.apache.log4?.*.class");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }


    public void testFindLibraryForClassFileWithoutSuffix() throws LibraryFinderException {
        LibraryVisitor visitor = new LibraryVisitor(new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org.apache.log4j.Logger"));
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForClassFile_WithForwardSlashes() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org/apache/log4j/Logger.class");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForClassFiles_Regex() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org/apache/log4j/Logger.class|org/apache/log4j/lf5/lf5/properties");
        query.setRegex(true);
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForClassFile_WithForwardSlashes_WithoutSuffix() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org/apache/log4j/Logger");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForClassFile_WithForwardSlashes_WithoutSuffix_StarWildcard() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org/apache/*/Logger");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForClassFile_WithBackwardSlashes() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org\\apache\\log4j\\Logger.class");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForClassFile_WithBackwardSlashes_StarWildcard() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org\\*\\log4j\\Logger.class");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForClassFile_WithBackwardSlashes_WithoutSuffix() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org\\apache\\log4j\\Logger");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForPropertiesFile_WithPeriodReturnsNothing() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org.apache.log4j.lf5.lf5.properties");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(0, results.size());
    }

    public void testFindLibraryForPropertiesFile_StarWildcard() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org/apache/log4j/lf5/*.properties");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }


    public void testFindLibraryForPropertiesFile_QuestionMarkWildcard() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "???/apache/log4j/lf5/*.properties");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForPropertiesFile_WithForwardSlashes() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org/apache/log4j/lf5/lf5.properties");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForPropertiesFile_WithForwardSlashes_StarWildcard() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org/apache/log4j/lf5/lf5.*");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForDtdFile() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testLog4jJar.getParentFile().getPath(), "org/apache/log4j/xml/log4j.dtd");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(log4jLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForTwoLevelClassFile() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testAbbotJar.getParentFile().getPath(), "abbot.componentfinder");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(abbotJarLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForTwoLevelClassFile_CaseInsensitive() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testAbbotJar.getParentFile().getPath(), "abbot.condition");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(abbotJarLibraryFinderResult, actualResult);
    }

    public void testFindLibraryForTwoLevelResourceFile() throws LibraryFinderException {
        LibraryFinderQuery query = new LibraryFinderQuery(testAbbotJar.getParentFile().getPath(), "abbot/abbot.xsd");
        LibraryVisitor visitor = new LibraryVisitor(query);
        visitor.visitLibraries();
        Set<LibraryFinderResult> results = visitor.getResults();
        assertEquals(1, results.size());
        LibraryFinderResult actualResult = results.iterator().next();
        assertEquals(abbotJarLibraryFinderResult, actualResult);
    }

}
