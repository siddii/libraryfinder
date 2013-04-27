package com.boxysystems.libraryfinder.model;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Oct 8, 2007
 * Time: 11:55:33 PM
 */
public class LibraryFileFilterTest extends AbstractLibraryFinderTest{

    public void testAcceptJar() throws Exception{
        File jarFile = new File("abc.jar");
        LibraryFileFilter filter = new LibraryFileFilter();
        assertTrue(filter.accept(jarFile));
    }

    public void testAcceptZip() throws Exception{
        File zipFile = new File("abc.zip");
        LibraryFileFilter filter = new LibraryFileFilter();
        assertTrue(filter.accept(zipFile));
    }

    public void testAcceptFolders() throws Exception{
        File folder = FileUtil.createTempFile(new File("."),"test-folder");
        folder.mkdir();
        LibraryFileFilter filter = new LibraryFileFilter();
        assertTrue(filter.accept(folder));
    }

    public void testDontAcceptNonLibraryFiles() throws Exception{
        File nonLibraryFile = new File("abc.zip.tmp");
        LibraryFileFilter filter = new LibraryFileFilter();
        assertFalse(filter.accept(nonLibraryFile));
    }
}
