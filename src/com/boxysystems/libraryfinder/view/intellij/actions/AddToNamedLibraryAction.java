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

import com.boxysystems.libraryfinder.view.intellij.PathUtil;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Oct 2, 2006
 * Time: 1:38:54 PM
 */
public abstract class AddToNamedLibraryAction extends AbstractAction {
    private Library namedLibrary;
    private List<String> libraryPaths;
    private int namedLibraryConstant;
    protected final static int PROJECT_LIBRARY = 1;
    protected final static int GLOBAL_LIBRARY = 2;

    public AddToNamedLibraryAction(Library namedLibrary, List<String> libraryPaths, int namedLibraryConstant) {
        super(namedLibrary.getName());
        this.namedLibrary = namedLibrary;
        this.libraryPaths = libraryPaths;
        this.namedLibraryConstant = namedLibraryConstant;
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (namedLibrary != null && libraryPaths != null && libraryPaths.size() > 0) {
            Application application = ApplicationManager.getApplication();
            application.runWriteAction(new Runnable() {
                public void run() {
                    Library.ModifiableModel modifiableModel = namedLibrary.getModifiableModel();
                    for (String libraryPath : libraryPaths) {
                        addLibrary(modifiableModel, libraryPath);
                    }
                    modifiableModel.commit();
                }

                private void addLibrary(Library.ModifiableModel modifiableModel, String libraryPath) {
                    String escapedJarURL = PathUtil.escapeLibraryURL(libraryPath);
                    if (!isLibraryAlreadyExist(modifiableModel, escapedJarURL)) {
                        modifiableModel.addRoot(escapedJarURL, OrderRootType.CLASSES);
                    } else {
                        showWarningDialog(libraryPath);
                    }
                }

                private boolean isLibraryAlreadyExist(Library.ModifiableModel modifiableModel, String escapedJarURL) {
                    String[] urls = modifiableModel.getUrls(OrderRootType.CLASSES);
                    for (String url : urls) {
                        if (url.equalsIgnoreCase(escapedJarURL)) {
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }

    private void showWarningDialog(String libraryPath) {
        switch (namedLibraryConstant) {
            case PROJECT_LIBRARY:
                Messages.showWarningDialog("'" + libraryPath + "' already exists in '" + namedLibrary.getName() + "' Project Library !", "Warning");
                break;
            case GLOBAL_LIBRARY:
                Messages.showWarningDialog("'" + libraryPath + "' already exists in '" + namedLibrary.getName() + "' Global Library !", "Warning");
                break;
        }

    }
}
