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

import com.boxysystems.libraryfinder.view.intellij.LibraryFinderPlugin;
import com.boxysystems.libraryfinder.view.intellij.ui.Icons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Apr 5, 2006
 * Time: 11:36:24 PM
 */
public class CloseWindowAction extends AnAction {

    public CloseWindowAction() {
        super("Close",
                "Close the window",
                Icons.CLOSE_ICON);
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = (Project) event.getDataContext().getData(DataConstants.PROJECT);
        LibraryFinderPlugin plugin = LibraryFinderPlugin.instance(project);
        ToolWindow toolWindow = plugin.getToolWindow();
        if (toolWindow != null) {
            toolWindow.hide(null);
            toolWindow.setAvailable(false, null);
        }
    }
}
