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
 * Class representing value/query object used as input to the library finder
 *
 * @author Siddique Hameed
 * @since Feb 3, 2007
 */
public class LibraryFinderQuery {
    private String searchPath;
    private String fileNamePattern;
    private boolean regex;
    private boolean caseSensitive;
    private boolean verboseMode;
    private String excludedFolders;


    public LibraryFinderQuery(String searchPath, String fileNamePattern) {
        this.searchPath = searchPath.trim();
        this.fileNamePattern = fileNamePattern.trim();
    }

    public String getSearchPath() {
        return searchPath;
    }

    public String getFileNamePattern() {
        return fileNamePattern;
    }

    public void setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    public boolean isRegex() {
        return regex;
    }

    public void setRegex(boolean regex) {
        this.regex = regex;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }


    public boolean isVerboseMode() {
        return verboseMode;
    }

    public void setVerboseMode(boolean verboseMode) {
        this.verboseMode = verboseMode;
    }


    public String getExcludedFolders() {
        return excludedFolders;
    }

    public void setExcludedFolders(String excludedFolders) {
        this.excludedFolders = excludedFolders;
    }
}
