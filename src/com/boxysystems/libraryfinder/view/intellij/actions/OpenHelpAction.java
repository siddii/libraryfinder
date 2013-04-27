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
import com.boxysystems.libraryfinder.view.intellij.ui.Icons;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Oct 19, 2006
 * Time: 3:58:13 PM
 */
public class OpenHelpAction extends AnAction {

    public OpenHelpAction() {
        super("Help",
                "Open Help Window",
                Icons.HELP_ICON);
    }

    public void actionPerformed(AnActionEvent anActionEvent) {
        try {
            BrowserUtil.launchBrowser(Constants.PLUGIN_HOME_PAGE);
        } catch (Exception e) {
            Messages.showErrorDialog("Error launching browser! "+Constants.NEW_LINE+"Exception: " + e.getMessage(), "Error");
        }
    }
}
