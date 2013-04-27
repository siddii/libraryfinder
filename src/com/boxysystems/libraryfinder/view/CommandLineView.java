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

import com.boxysystems.libraryfinder.model.LibraryFinderException;
import com.boxysystems.libraryfinder.model.LibraryFinderQuery;
import com.boxysystems.libraryfinder.model.LibraryFinderResult;

import java.util.Observable;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Jan 30, 2007
 * Time: 1:09:24 PM
 */
public class CommandLineView extends LibraryFinderView {

    public static final String RESULT_SEPARATOR = "------------------------------------------------------------------";

    public CommandLineView(LibraryFinderQuery query) {
        super(query);
    }

    public void render(Set<LibraryFinderResult> results) {
        System.out.println(getResultHeader());
        System.out.println(RESULT_SEPARATOR);
        if (results != null && results.size() > 0) {
            for (LibraryFinderResult result : results) {
                System.out.println(result.getLibraryPath());

            }
        } else {
            System.out.println(RESULT_NOT_FOUND_MSG);
        }
        System.out.println(RESULT_SEPARATOR);
        setRendered(true);
    }



    public void update(Observable observable, Object object) {
        System.out.println(object);
    }

    public void render(LibraryFinderException e) {
        System.out.println(getResultHeader());
        System.out.println(RESULT_SEPARATOR);
        System.out.println(e.getMessage());
        System.out.println(RESULT_SEPARATOR);
        setRendered(true);        
    }

}
