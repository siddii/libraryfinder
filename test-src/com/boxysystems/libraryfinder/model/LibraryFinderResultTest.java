package com.boxysystems.libraryfinder.model;

import com.intellij.openapi.util.io.FileUtil;

import java.io.File;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Unit Test for LibraryFinderResult
 *
 * @author : Siddique Hameed
 * @since : May 23, 2007
 */

public class LibraryFinderResultTest extends AbstractLibraryFinderTest {

    public void testConstructor() throws Exception {
        LibraryFinderResult result = new LibraryFinderResult(testLog4jJar.getAbsolutePath(), testLog4jJar.length());
        assertNotNull(result);
        assertEquals(378778, result.getFileSize());
        assertEquals(testLog4jJar.getAbsolutePath(), result.getLibraryPath());
    }

    public void testEquals() throws Exception {
        LibraryFinderResult result1 = new LibraryFinderResult(testLog4jJar.getAbsolutePath(), testLog4jJar.length());
        LibraryFinderResult result2 = new LibraryFinderResult(testLog4jJar.getAbsolutePath(), testLog4jJar.length());
        assertEquals(result1, result2);
        assertTrue(result1.equals(result2));
    }

    public void testEquals_NotTrue() throws Exception {
        LibraryFinderResult result1 = new LibraryFinderResult(testLog4jJar.getAbsolutePath(), testLog4jJar.length());
        assertNotSame(result1, new Object());
        assertFalse(result1.equals(new Object()));
    }

    public void testSorting_Simple() throws Exception {

        File a = null, b = null, c = null;
        try {
            a = new File("a.jar");
            FileUtil.copy(testLog4jJar, a);
            b = new File("b.zip");
            FileUtil.copy(testLog4jJar, b);
            c = new File("c.jar");
            FileUtil.copy(testLog4jJar, c);

            LibraryFinderResult result1 = new LibraryFinderResult(a.getAbsolutePath(), a.length());
            LibraryFinderResult result2 = new LibraryFinderResult(b.getAbsolutePath(), b.length());
            LibraryFinderResult result3 = new LibraryFinderResult(c.getAbsolutePath(), c.length());

            Set<LibraryFinderResult> treeSet = new TreeSet<LibraryFinderResult>();
            treeSet.add(result1);
            treeSet.add(result2);
            treeSet.add(result3);

            Iterator<LibraryFinderResult> iterator = treeSet.iterator();
            LibraryFinderResult libraryFinderResult1 = iterator.next();
            assertEquals(result1, libraryFinderResult1);
            LibraryFinderResult libraryFinderResult2 = iterator.next();
            assertEquals(result2, libraryFinderResult2);
            LibraryFinderResult libraryFinderResult3 = iterator.next();
            assertEquals(result3, libraryFinderResult3);
        } finally {
            cleanupFiles(a, b, c);
        }
    }

    public void testSorting_MixedCase() throws Exception {
        File a = null, b = null, c = null;
        try {
            a = new File("a.jar");
            FileUtil.copy(testLog4jJar, a);
            b = new File("B.jar");
            FileUtil.copy(testLog4jJar, b);
            c = new File("c.jar");
            FileUtil.copy(testLog4jJar, c);


            LibraryFinderResult result1 = new LibraryFinderResult(a.getAbsolutePath(), a.length());
            LibraryFinderResult result2 = new LibraryFinderResult(b.getAbsolutePath(), b.length());
            LibraryFinderResult result3 = new LibraryFinderResult(c.getAbsolutePath(), c.length());

            Set<LibraryFinderResult> treeSet = new TreeSet<LibraryFinderResult>();
            treeSet.add(result1);
            treeSet.add(result2);
            treeSet.add(result3);

            Iterator<LibraryFinderResult> iterator = treeSet.iterator();
            LibraryFinderResult libraryFinderResult1 = iterator.next();
            assertEquals(result1, libraryFinderResult1);
            LibraryFinderResult libraryFinderResult2 = iterator.next();
            assertEquals(result2, libraryFinderResult2);
            LibraryFinderResult libraryFinderResult3 = iterator.next();
            assertEquals(result3, libraryFinderResult3);
        } finally {
            cleanupFiles(a, b, c);

        }
    }

    public void testSorting_LittleComplex() throws Exception {
        File a = null, b = null, c = null;
        try {
            a = new File("a1232132.jar");
            FileUtil.copy(testLog4jJar, a);
            b = new File("Bvfdfsddddddd.jar");
            FileUtil.copy(testLog4jJar, b);
            c = new File("C32333333.jar");
            FileUtil.copy(testLog4jJar, c);

            LibraryFinderResult result1 = new LibraryFinderResult(a.getAbsolutePath(), a.length());
            LibraryFinderResult result2 = new LibraryFinderResult(b.getAbsolutePath(), b.length());
            LibraryFinderResult result3 = new LibraryFinderResult(c.getAbsolutePath(), c.length());

            Set<LibraryFinderResult> treeSet = new TreeSet<LibraryFinderResult>();
            treeSet.add(result1);
            treeSet.add(result2);
            treeSet.add(result3);

            Iterator<LibraryFinderResult> iterator = treeSet.iterator();
            LibraryFinderResult libraryFinderResult1 = iterator.next();
            assertEquals(result1, libraryFinderResult1);
            LibraryFinderResult libraryFinderResult2 = iterator.next();
            assertEquals(result2, libraryFinderResult2);
            LibraryFinderResult libraryFinderResult3 = iterator.next();
            assertEquals(result3, libraryFinderResult3);
        } finally {
            cleanupFiles(a, b, c);
        }
    }

    private void cleanupFiles(File a, File b, File c) {
        if (a != null) {
            a.deleteOnExit();
        }
        if (b != null) {
            b.deleteOnExit();
        }
        if (c != null) {
            c.deleteOnExit();
        }
    }
}
