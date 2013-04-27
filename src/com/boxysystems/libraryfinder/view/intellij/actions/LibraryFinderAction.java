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

import com.boxysystems.jgoogleanalytics.FocusPoint;
import com.boxysystems.libraryfinder.controller.IntelliJLibraryFinder;
import com.boxysystems.libraryfinder.model.Constants;
import com.boxysystems.libraryfinder.model.LibraryFinderQuery;
import com.boxysystems.libraryfinder.view.intellij.IntelliJLibraryFinderView;
import com.boxysystems.libraryfinder.view.intellij.LibraryFinderPlugin;
import com.boxysystems.libraryfinder.view.intellij.ui.FindLibraryDialog;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.progress.PerformInBackgroundOption;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Dec 19, 2005
 * Time: 9:57:12 AM
 */
public class LibraryFinderAction extends AnAction {
  private LibraryFinderQuery query = null;
  private IntelliJLibraryFinderView view = null;
  private FocusPoint findLibraryFocusPoint = new FocusPoint(Constants.FIND_LIBRARY_ACTION);

  public static synchronized LibraryFinderAction getInstance() {
    return (LibraryFinderAction) ActionManager.getInstance().getAction("LibraryFinderPlugin.Actions.LibraryFinderAction");
  }

  public void update(AnActionEvent anActionEvent) {
    Presentation presentation = anActionEvent.getPresentation();
    Project project = (Project) anActionEvent.getDataContext().getData(DataConstants.PROJECT);
    if (project != null) {
      presentation.setEnabled(true);
      presentation.setVisible(true);
    } else {
      presentation.setEnabled(false);
      presentation.setVisible(false);
    }
  }

  public void actionPerformed(AnActionEvent anActionEvent) {
    Project project = (Project) anActionEvent.getDataContext().getData(DataConstants.PROJECT);
    performAction(project, null, null, false);
  }

  public void performAction(final Project project, String fileNamePattern, String libSearchPath, boolean isRegex) {
    FindLibraryDialog dialog;
    if (fileNamePattern != null && libSearchPath != null) {
      dialog = new FindLibraryDialog(project, fileNamePattern, libSearchPath, isRegex);
    } else {
      dialog = new FindLibraryDialog(project);
      dialog.setEditorSelectedTextToFileName();
    }
    LibraryFinderPlugin.instance(project).track(findLibraryFocusPoint);
    dialog.show();
    if (dialog.isOK()) {
      query = dialog.getSearchQuery();
      query.setVerboseMode(true);
      executeAction(project);
    }
  }

  public void executeAction(final Project project) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        final ProgressManager progressManager = ProgressManager.getInstance();
        if (view == null) {
          view = LibraryFinderPlugin.instance(project).getView(query);
        } else {
          view.initQuery(query);
        }

        IntelliJLibraryFinder intelliJLibraryFinder = new IntelliJLibraryFinder(view);
        LibraryFinderSuccessOrCancelledRunnable successOrCancelledRunnable = new LibraryFinderSuccessOrCancelledRunnable(view);
        progressManager.runProcessWithProgressAsynchronously(project, Constants.FINDING_LIBRARY_MESSAGE, intelliJLibraryFinder, successOrCancelledRunnable, successOrCancelledRunnable, PerformInBackgroundOption.DEAF);
      }
    });
  }

  private class LibraryFinderSuccessOrCancelledRunnable implements Runnable {
    private IntelliJLibraryFinderView view;

    private LibraryFinderSuccessOrCancelledRunnable(IntelliJLibraryFinderView view) {
      this.view = view;
    }

    public void run() {
      if (!view.isRendered()) {
        view.render();
      }
    }
  }
}
