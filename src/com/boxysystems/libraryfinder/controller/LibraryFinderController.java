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


package com.boxysystems.libraryfinder.controller;

import com.boxysystems.libraryfinder.model.LibraryFinderException;
import com.boxysystems.libraryfinder.model.LibraryFinderQuery;
import com.boxysystems.libraryfinder.model.LibraryVisitor;
import com.boxysystems.libraryfinder.view.LibraryFinderView;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Jan 30, 2007
 * Time: 1:17:14 PM
 */
public abstract class LibraryFinderController {

    protected LibraryFinderView view;

    protected LibraryFinderController(LibraryFinderView view) {
        this.view = view;
    }


    public void performAction() {
        LibraryVisitor visitor;
        try {
            LibraryFinderQuery query = view.getQuery();
            if (query.isVerboseMode()) {
                visitor = new LibraryVisitor(query, view);
                view.logPattern(visitor.getPattern());
            } else {
                visitor = new LibraryVisitor(query);
            }
            visitor.visitLibraries();
            view.setResults(visitor.getResults());
        } catch (LibraryFinderException e) {
            view.setException(e);
        }
        finally{
            view.render();
        }
    }

    public LibraryFinderView getView() {
        return view;
    }
}
