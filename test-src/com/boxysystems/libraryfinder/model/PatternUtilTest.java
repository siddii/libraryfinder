package com.boxysystems.libraryfinder.model;


/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Mar 29, 2006
 * Time: 10:28:49 AM
 */
public class PatternUtilTest extends AbstractLibraryFinderTest {

    public void testClassNameWithPeriod() throws Exception, LibraryFinderException {
        String classNamePattern = PatternUtil.generateFileNamePattern("org.apache.util.ClassLoader");
        assertEquals("org/apache/util/ClassLoader.class", classNamePattern);

        classNamePattern = PatternUtil.generateFileNamePattern("org.apache.util.classloader");
        assertEquals("org/apache/util/classloader.class", classNamePattern);
    }

    public void testClassNameWithPeriodAndPackageImport() throws Exception, LibraryFinderException {
        String classNamePattern = PatternUtil.generateFileNamePattern("org.apache.util.*");
        assertEquals("org/apache/util/.*.class", classNamePattern);
    }

    public void testClassNameWithPeriodUsingWildcard() throws Exception, LibraryFinderException {
        String classNamePattern = PatternUtil.generateFileNamePattern("org*classloader.class");
        assertEquals("org.*classloader.class", classNamePattern);

        classNamePattern = PatternUtil.generateFileNamePattern("org*classloader");
        assertEquals("org.*classloader.class", classNamePattern);
    }

    public void testClassNameWithPeriod_WithExtension() throws Exception, LibraryFinderException {
        String classNamePattern = PatternUtil.generateFileNamePattern("org.apache.util.ClassLoader.class");
        assertEquals("org/apache/util/ClassLoader.class", classNamePattern);
    }

    public void testClassNameWithSlashes() throws Exception, LibraryFinderException {
        String classNamePattern = PatternUtil.generateFileNamePattern("org/apache/util/ClassLoader");
        assertEquals("org/apache/util/ClassLoader.class", classNamePattern);

        classNamePattern = PatternUtil.generateFileNamePattern("org/apache/util/classloader");
        assertEquals("org/apache/util/classloader.class", classNamePattern);
    }

    public void testClassNameWithSlashes_WithExtension() throws Exception, LibraryFinderException {

        String classNamePattern = PatternUtil.generateFileNamePattern("org/apache/util/ClassLoader.class");
        assertEquals("org/apache/util/ClassLoader.class", classNamePattern);

        classNamePattern = PatternUtil.generateFileNamePattern("org/apache/util/classloader.class");
        assertEquals("org/apache/util/classloader.class", classNamePattern);
    }

    public void testSimpleMatching_WithBackwardSlash() {
        String classNamePattern = PatternUtil.generateFileNamePattern("org\\apache\\util\\ClassLoader.class");
        assertEquals("org/apache/util/ClassLoader.class", classNamePattern);

        classNamePattern = PatternUtil.generateFileNamePattern("org\\apache\\util\\classLoader.class");
        assertEquals("org/apache/util/classLoader.class", classNamePattern);
    }

    public void testSimpleMatching_WithBackwardSlash_WithWildcard() {
        String classNamePattern = PatternUtil.generateFileNamePattern("org\\*\\ClassLoader.class");
        assertEquals("org/.*/ClassLoader.class", classNamePattern);

        classNamePattern = PatternUtil.generateFileNamePattern("org\\*\\classLoader.class");
        assertEquals("org/.*/classLoader.class", classNamePattern);
    }


    public void testSearchForPropertiesFileWithForwardSlashes() throws Exception, LibraryFinderException {
        String fileNamePattern = PatternUtil.generateFileNamePattern("org/apache/log4j/lf5/config/defaultconfig.properties");
        assertEquals("org/apache/log4j/lf5/config/defaultconfig.properties", fileNamePattern);
    }

    public void testSearchForDtdFileWithForwardSlashes() throws Exception, LibraryFinderException {
        String fileNamePattern = PatternUtil.generateFileNamePattern("org/apache/log4j/xml/log4j.dtd");
        assertEquals("org/apache/log4j/xml/log4j.dtd", fileNamePattern);
    }

    public void testWildcardToRegex_WithStar() throws Exception {
        String text = "This is some text";
        String regex = PatternUtil.wildcardToRegex("This*text");
        assertTrue(text.matches(regex));
        regex = PatternUtil.wildcardToRegex("This*t*t");
        assertTrue(text.matches(regex));
    }

    public void testWildcardToRegex_WithQuestionMark() throws Exception {
        String text = "Siddique";
        String regex = PatternUtil.wildcardToRegex("S?ddique");
        assertTrue(text.matches(regex));
        regex = PatternUtil.wildcardToRegex("??dd?qu?");
        assertTrue(text.matches(regex));
    }

    public void testWildcardToRegex_WithStarAndQuestionMark() throws Exception {
        String text = "Siddique Hameed";
        String regex = PatternUtil.wildcardToRegex("S?dd*eed");
        assertTrue(text.matches(regex));
        regex = PatternUtil.wildcardToRegex("*ddique*amee?");
        assertTrue(text.matches(regex));
    }


    public void testWildcardToRegex_NotMatches() throws Exception {
        String text = "This is some text";
        String regex = PatternUtil.wildcardToRegex("Shis*text");
        assertFalse(text.matches(regex));
    }

    public void testWildcardToRegexReturnsSameIfNoWildcard() throws Exception {
        String regex = PatternUtil.wildcardToRegex("This text");
        assertEquals("This text", regex);
    }
}
