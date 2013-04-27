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

package com.boxysystems.libraryfinder.view.intellij;

import com.boxysystems.libraryfinder.model.*;
import com.boxysystems.libraryfinder.view.LibraryFinderView;
import com.boxysystems.libraryfinder.view.intellij.ui.ResultTreeNode;
import com.boxysystems.libraryfinder.view.intellij.ui.ResultsPanel;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.util.Observable;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * View for IntelliJ Library Finder plugin
 *
 * @author Siddique Hameed
 * @since May 29, 2007
 */

public class IntelliJLibraryFinderView extends LibraryFinderView {

    private ToolWindow toolWindow;
    private LibraryFinderPlugin plugin;
    private ResultsPanel resultsPanel;

    private static Logger logger = Logger.getLogger(IntelliJLibraryFinderView.class);

    public IntelliJLibraryFinderView(LibraryFinderPlugin plugin, LibraryFinderQuery query) {
        super(query);
        this.plugin = plugin;
    }

    public ToolWindow getToolWindow() {
        return toolWindow;
    }

    public void render(final Set<LibraryFinderResult> results) {
        addInputValuesToHistory(query);
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                initToolWindow();
                ResultTreeNode resultNode = new ResultTreeNode(getResultHeader());
                populateNode(resultNode, results, false);
                resultsPanel.setCurrentNode(resultNode);
                showResultsPanelInToolWindow();
            }
        });
        setRendered(true);
    }

    public void render(final LibraryFinderException e) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                initToolWindow();
                ResultTreeNode resultNode = new ResultTreeNode(getResultHeader());
                ResultTreeNode errorNode = new ResultTreeNode(e.getMessage());
                errorNode.setError(true);
                resultNode.add(errorNode);
                resultsPanel.setCurrentNode(resultNode);
                showResultsPanelInToolWindow();
            }
        });
        setRendered(true);
    }

    private void addInputValuesToHistory(LibraryFinderQuery query) {
        LibraryFinderHistory historyComponent = LibraryFinderHistory.getInstance();
        historyComponent.addDir(query.getSearchPath());
        historyComponent.addResource(query.getFileNamePattern());
    }

    private void showResultsPanelInToolWindow() {
        ToolWindow toolWindow = ToolWindowManager.getInstance(plugin.getProject()).getToolWindow(Constants.PLUGIN_ID);
        toolWindow.setAvailable(true, null);
        if (toolWindow.isActive()) {
            toolWindow.show(resultsPanel);
        } else {
            toolWindow.activate(resultsPanel);
        }
    }

    private void initToolWindow() {
        if (toolWindow == null) {
            ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(plugin.getProject());
            resultsPanel = new ResultsPanel(plugin);
            toolWindow = toolWindowManager.registerToolWindow(Constants.PLUGIN_ID, resultsPanel, ToolWindowAnchor.BOTTOM);
        }
    }


    private void populateNode(ResultTreeNode node, Set results, boolean error) {
        for (Object result : results) {
            ResultTreeNode childNode;
            if (result instanceof LibraryFinderResult) {
                LibraryFinderResult resultValue = (LibraryFinderResult) result;
                childNode = new ResultTreeNode(resultValue);
            } else if (result instanceof String) {
                String resultValue = (String) result;
                childNode = new ResultTreeNode(resultValue);
            } else {
                return;
            }
            childNode.setError(error);
            node.add(childNode);
        }

        if (results.size() == 0) {
            ResultTreeNode childNode = new ResultTreeNode(RESULT_NOT_FOUND_MSG);
            node.add(childNode);
        }
    }


    public void update(Observable observable, Object object) {
        ProgressIndicator progressIndicator = ProgressManager.getInstance().getProgressIndicator();
        if (progressIndicator != null) {
            if (progressIndicator.isCanceled()) {
                progressIndicator.setText(Constants.ACTION_CANCELLED_MESSAGE);
                LibraryVisitor visitor = (LibraryVisitor) observable;
                visitor.setCancelled(progressIndicator.isCanceled());
            } else {
                progressIndicator.setText(object.toString());
            }
        }
    }


    public void logPattern(Pattern pattern) {
        logger.info("LibraryFinder - compiled pattern = " + pattern);
    }

    public void initQuery(LibraryFinderQuery query) {
        this.query = query;
        setResults(null);
        setException(null);
    }
}
