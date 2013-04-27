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
import com.boxysystems.libraryfinder.view.intellij.LibraryFinderPlugin;
import com.boxysystems.libraryfinder.view.intellij.actions.CloseWindowAction;
import com.boxysystems.libraryfinder.view.intellij.actions.OpenHelpAction;
import com.boxysystems.libraryfinder.view.intellij.actions.RerunAction;
import com.boxysystems.libraryfinder.view.LibraryFinderView;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * ResultsPanel where the results are drawn in JTree
 *
 * @author Siddique Hameed
 * @version 1.0
 * @since Dec 12, 2005
 */

public class ResultsPanel extends JPanel implements Runnable, TreeWillExpandListener {

    private JTree resultsTree;

    public ResultsPanel(final LibraryFinderPlugin plugin) {
        setLayout(new BorderLayout());
        setBackground(Color.gray);

        final DefaultActionGroup toolbarGroup = new DefaultActionGroup();
        toolbarGroup.add(new RerunAction());
        toolbarGroup.add(new CloseWindowAction());
        toolbarGroup.add(new OpenHelpAction());
        final ActionManager actionManager = ActionManager.getInstance();
        final ActionToolbar toolbar = actionManager.createActionToolbar(Constants.PLUGIN_ID, toolbarGroup, false);

        add(toolbar.getComponent(), BorderLayout.WEST);

        resultsTree = new JTree();
        resultsTree.addTreeWillExpandListener(this);
        resultsTree.setCellRenderer(new ResultTreeCellRenderer());

        ToolTipManager.sharedInstance().registerComponent(resultsTree);
        JScrollPane scrollPane = new JScrollPane(resultsTree);
        add(scrollPane, BorderLayout.CENTER);

        resultsTree.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
                    if (resultsTree != null) {
                        ResultTreeNode selectedNode = (ResultTreeNode) resultsTree.getLastSelectedPathComponent();
                        if (selectedNode != null && selectedNode.isLeaf() && !selectedNode.isError() && !selectedNode.toString().equals(LibraryFinderView.RESULT_NOT_FOUND_MSG)) {
                            ResultsTreePopupMenu popupMenu = new ResultsTreePopupMenu(resultsTree, plugin);
                            popupMenu.show(resultsTree, e.getPoint().x, e.getPoint().y);
                        }
                    }
                }
            }
        });
    }

    public void setCurrentNode(ResultTreeNode tree) {
        TreeModel model = new DefaultTreeModel(tree);
        this.resultsTree.setModel(model);
    }

    public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
        throw new ExpandVetoException(event);
    }

    public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
        throw new ExpandVetoException(event);
    }

    public void run() {
        //Need to be runnable to display in tool window
    }
}