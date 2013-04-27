/*
 * Copyright 2007 BoxySystems Inc. <siddique@boxysystems.com>
 *                  http://www.BoxySystems.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.boxysystems.libraryfinder.model;

/**
 * Class representing the result 
 * User: Siddique Hameed
 * Date: Apr 2, 2006
 * Time: 4:18:39 PM
 */

public class LibraryFinderResult implements Comparable {

    private String libraryPath;
    private long fileSize;


    public LibraryFinderResult(String libraryPath, long fileSize) {
        this.libraryPath = libraryPath;
        this.fileSize = fileSize;
    }

    public String getLibraryPath() {
        return libraryPath;
    }

    public long getFileSize() {
        return fileSize;
    }


    public String toString() {
        return getLibraryPath() + " (" + Math.round(getFileSize() / 1024) + " KB)";
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final LibraryFinderResult that = (LibraryFinderResult) o;
        final long fileSize = getFileSize();
        final String libraryPath = getLibraryPath();
        return fileSize == that.getFileSize() && !(libraryPath != null ? !libraryPath.equals(that.getLibraryPath()) : that.getLibraryPath() != null);
    }

    public int compareTo(Object o) {
        if (o instanceof LibraryFinderResult) {
            LibraryFinderResult that = (LibraryFinderResult) o;
            return this.getLibraryPath().toLowerCase().compareTo(that.getLibraryPath().toLowerCase());
        }
        return 0;
    }
}
