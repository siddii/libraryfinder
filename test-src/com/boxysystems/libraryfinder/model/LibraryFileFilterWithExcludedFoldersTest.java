package com.boxysystems.libraryfinder.model;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Oct 9, 2007
 * Time: 12:02:12 AM
 */
public class LibraryFileFilterWithExcludedFoldersTest extends AbstractLibraryFinderTest{

    public void testAcceptNonExcludedFolder() throws Exception{
        LibraryFileFilterWithExcludedFolders filter = new LibraryFileFilterWithExcludedFolders(Constants.DEFAULT_EXCLUDED_FOLDERS);
        File nonExcludedFolder = FileUtil.createTempFile(new File("."),"abcd");
        nonExcludedFolder.mkdir();
        assertTrue(filter.accept(nonExcludedFolder));
    }

    public void testDontAcceptExcludedFolder_CVS() throws Exception{
        LibraryFileFilterWithExcludedFolders filter = new LibraryFileFilterWithExcludedFolders(Constants.DEFAULT_EXCLUDED_FOLDERS);
        File nonExcludedFolder = FileUtil.createTempFile(new File("."),"CVS");
        nonExcludedFolder.mkdir();
        assertFalse(filter.accept(nonExcludedFolder));
    }
    public void testDontAcceptExcludedFolder_dotDS_Store() throws Exception{
        LibraryFileFilterWithExcludedFolders filter = new LibraryFileFilterWithExcludedFolders(Constants.DEFAULT_EXCLUDED_FOLDERS);
        File nonExcludedFolder = FileUtil.createTempFile(new File("."),".DS_Store");
        nonExcludedFolder.mkdir();
        assertFalse(filter.accept(nonExcludedFolder));
    }

    public void testDontAcceptExcludedFolder_vssverdotscc() throws Exception{
        LibraryFileFilterWithExcludedFolders filter = new LibraryFileFilterWithExcludedFolders(Constants.DEFAULT_EXCLUDED_FOLDERS);
        File nonExcludedFolder = FileUtil.createTempFile(new File("."),"vssver.scc");
        nonExcludedFolder.mkdir();
        assertFalse(filter.accept(nonExcludedFolder));
    }
}
