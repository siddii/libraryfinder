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

import com.boxysystems.libraryfinder.model.Constants;
import com.boxysystems.libraryfinder.model.LibraryFinderException;
import com.boxysystems.libraryfinder.view.intellij.LibraryFinderConfigurationComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * File explorer utility class to open up the containing folder.
 * User: Siddique Hameed
 * Date: Apr 15, 2006
 * Time: 2:52:08 PM
 */
public class FileExplorer {

  private static Logger logger = Logger.getLogger(FileExplorer.class);
  private static final String errHeader = "Error Opening Containing Folder : " + Constants.NEW_LINE;

  public static void browseDirectory(Project project, String url) {
    String command = "";
    try {

      LibraryFinderConfigurationComponent libraryFinderConfigurationComponent = LibraryFinderConfigurationComponent.getInstance();
      String folderScript = libraryFinderConfigurationComponent.getOpenContainingFolderScript();
      if (Constants.OS.startsWith("Mac OS") && folderScript.equals("")) {
        showExplorerForMac(url);
      } else if (folderScript.trim().equals("")) {
        throw new LibraryFinderException("Please provide 'Open Containing Folder script' settings... " + Constants.NEW_LINE +
          " Settings -> IDE Settings - > Library Finder");
      } else {
        logger.debug("folderScript=" + folderScript + ", url=" + url);
        Runtime.getRuntime().exec(folderScript + " " + url);
      }
    } catch (Throwable e) {
      showErrorDialog(e, command, project);
    }
  }

  private static void showErrorDialog(Throwable e, String command, Project project) {
    StringBuffer errorMessage = new StringBuffer(errHeader);
    errorMessage.append(e.getLocalizedMessage());
    if (command.length() > 0) {
      errorMessage.append(Constants.NEW_LINE + " command = '" + command + "'");
    }
    Messages.showErrorDialog(project, errorMessage.toString(), "Error");
  }

  private static void showExplorerForMac(String url) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    Class fileMgr = Class.forName("com.apple.eio.FileManager");
    Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[]{String.class});
    openURL.invoke(null, new Object[]{url});
  }

}
