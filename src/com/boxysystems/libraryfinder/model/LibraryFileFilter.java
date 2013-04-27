package com.boxysystems.libraryfinder.model;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Oct 8, 2007
 * Time: 11:51:10 PM
 */
public class LibraryFileFilter implements FileFilter {

    public boolean accept(File pathname) {
        return pathname.isDirectory() || isJar(pathname) || isZip(pathname);
    }

    private boolean isJar(File file) {
        return (file.getName().toLowerCase().endsWith(Constants.JAR_FILE_EXT));
    }

    private boolean isZip(File file) {
        return (file.getName().toLowerCase().endsWith(Constants.ZIP_FILE_EXT));
    }
}
