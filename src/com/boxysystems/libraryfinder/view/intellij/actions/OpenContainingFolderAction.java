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

import com.boxysystems.libraryfinder.view.intellij.ui.FileExplorer;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.psi.PsiDocumentManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Opens up containing folder for the found library fle in any OS.
 *
 * @author : Siddique Hameed
 * @since : 0.1
 */

public class OpenContainingFolderAction extends AbstractAction {
    private String libraryPath;
    private Project project;

    public OpenContainingFolderAction(String libraryPath, Project project) {
        super("Open Containing Folder");
        this.libraryPath = libraryPath;
        this.project = project;
    }

    public void actionPerformed(ActionEvent e) {
        if (libraryPath != null) {
            PsiDocumentManager.getInstance(project).commitAllDocuments();
            File libraryfile = new File(libraryPath);
            File containingFolder = libraryfile.getParentFile();
            String url = VfsUtil.pathToUrl(containingFolder.getPath());
            FileExplorer.browseDirectory(project, url);
        }
    }
}
