package com.boxysystems.libraryfinder.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Oct 8, 2007
 * Time: 11:54:23 PM
 */
public class LibraryFileFilterWithExcludedFolders extends LibraryFileFilter {
    private List<String> excludedFolders = new ArrayList<String>();

    public LibraryFileFilterWithExcludedFolders(String excludedFolders) {
        if (excludedFolders != null) {
            this.excludedFolders = Arrays.asList(excludedFolders.split(";"));
        }
    }

    public boolean accept(File pathname) {
        return super.accept(pathname) && !excludedFolders.contains(pathname.getName());
    }
}
