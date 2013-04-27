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

import com.boxysystems.libraryfinder.model.Constants;
import com.boxysystems.libraryfinder.model.CopiedLibrary;
import com.intellij.openapi.ide.CopyPasteManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Oct 2, 2006
 * Time: 2:22:04 PM
 */
public class CopyClasspathToClipboardAction extends AbstractAction {
    private List<String> libraryPaths;

    public CopyClasspathToClipboardAction(List<String> libraryPaths) {
        super("Copy classpath to clipboard");
        this.libraryPaths = libraryPaths;
    }

    public void actionPerformed(ActionEvent e) {
        CopyPasteManager copyPasteManager = CopyPasteManager.getInstance();

        StringBuilder clipboardContent = new StringBuilder();
        for (String libraryPath1 : libraryPaths) {
            if (clipboardContent.length() > 0) {
                clipboardContent.append(Constants.CLASSPATH_SEPARATOR);
            }
            clipboardContent.append(libraryPath1);
        }

        if (libraryPaths != null) {
            CopiedLibrary library = new CopiedLibrary(clipboardContent.toString());
            copyPasteManager.setContents(library);
        }
    }
}
