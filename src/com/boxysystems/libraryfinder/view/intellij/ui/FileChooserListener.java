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

package com.boxysystems.libraryfinder.view.intellij.ui;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.TextFieldWithHistory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Oct 3, 2006
 * Time: 4:16:35 PM
 */
public class FileChooserListener implements ActionListener {
    private Project project;
    private TextFieldWithHistory dirHistory;
    private final FileChooserDescriptor descriptor = new FileChooserDescriptor(false, true, false, false, false, false);

    public FileChooserListener(Project project, TextFieldWithHistory dirHistory) {
        this.project = project;
        this.dirHistory = dirHistory;
    }

    public void actionPerformed(ActionEvent actionEvent) {

        VirtualFile baseDir = getBaseDir();

        VirtualFile[] files;

        files = FileChooser.chooseFiles(descriptor, project, baseDir);
        dirHistory.setText(files[0].getPresentableUrl());
        dirHistory.selectText();
    }

    private VirtualFile getBaseDir() {
        VirtualFile baseDir = null;
        try {
            if ((dirHistory.getText() != null) && dirHistory.getText().length() > 0) {
                File file = new File(dirHistory.getText());
                if (file.exists()) {
                    baseDir = VfsUtil.findFileByURL(file.toURL());
                }
            }
        } catch (MalformedURLException e) {
            // don't care
        }
        return baseDir;
    }
}
