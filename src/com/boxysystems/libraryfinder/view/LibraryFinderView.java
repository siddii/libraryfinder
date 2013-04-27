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

package com.boxysystems.libraryfinder.view;

import com.boxysystems.libraryfinder.model.Constants;
import com.boxysystems.libraryfinder.model.LibraryFinderException;
import com.boxysystems.libraryfinder.model.LibraryFinderQuery;
import com.boxysystems.libraryfinder.model.LibraryFinderResult;

import java.util.Observer;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Feb 15, 2007
 * Time: 3:07:36 PM
 */
public abstract class LibraryFinderView implements Observer {

    private boolean rendered = false;

    public static final String RESULT_NOT_FOUND_MSG = "No libraries found for the given search criteria!";

    protected LibraryFinderQuery query;
    private Set<LibraryFinderResult> results;
    private LibraryFinderException exception;


    public LibraryFinderView(LibraryFinderQuery query){
        this.query = query;
    }

    public String getResultHeader() {
        StringBuffer resultHeader = new StringBuffer(Constants.NEW_LINE + "Search result"+(suffixSForMultipleResults())+" for");

        if (query.isRegex()) {
            resultHeader.append(" regex =");
        }

        resultHeader.append(" '").append(query.getFileNamePattern()).append("' in '").append(query.getSearchPath()).append("'");

        if (query.isCaseSensitive()) {
            resultHeader.append(" with case sensitivity turned on");
        }

        if (results != null && results.size() > 0){
            resultHeader.append(" (").append(results.size()).append(" file").append(suffixSForMultipleResults()).append(")");
        }

        return resultHeader.toString();

    }

    private String suffixSForMultipleResults() {
        return results != null && results.size() > 1 ? "s" : "";
    }


    public void setResults(Set<LibraryFinderResult> results) {
        this.results = results;
    }

    public void setException(LibraryFinderException exception) {
        this.exception = exception;
    }

    public void render(){
        if (results != null){
            render(results);
        }
        else{
            render(exception);
        }
    }

    public abstract void render(Set<LibraryFinderResult> results);

    public abstract void render(LibraryFinderException e);

    public void logPattern(Pattern pattern) {
        System.out.println("LibraryFinder - compiled pattern = "+pattern);
    }


    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public boolean isRendered() {
        return rendered;
    }


    public LibraryFinderQuery getQuery() {
        return query;
    }
}
