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

package com.boxysystems.libraryfinder.view.intellij.actions;

import com.intellij.openapi.roots.libraries.Library;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Oct 7, 2006
 * Time: 1:30:33 PM
 */
public class AddToProjectLibraryAction extends AddToNamedLibraryAction {
    public AddToProjectLibraryAction(Library namedLibrary, List<String> libraryPaths) {
        super(namedLibrary, libraryPaths, PROJECT_LIBRARY);
    }
}
