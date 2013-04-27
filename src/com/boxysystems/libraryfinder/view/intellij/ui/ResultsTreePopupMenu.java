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

import com.boxysystems.libraryfinder.model.LibraryFinderResult;
import com.boxysystems.libraryfinder.view.intellij.LibraryFinderPlugin;
import com.boxysystems.libraryfinder.view.intellij.actions.*;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.roots.libraries.LibraryTablesRegistrar;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

/**
 * Popup menu for the results tree
 *
 * @user: Siddique Hameed
 * @since : Dec 22, 2005
 *        Time: 2:22:14 PM
 */
public class ResultsTreePopupMenu extends JPopupMenu {

    private JTree tree;
    private LibraryFinderPlugin plugin;

    public ResultsTreePopupMenu(JTree tree, LibraryFinderPlugin plugin) {
        this.tree = tree;
        this.plugin = plugin;

        Module[] modules = getModules();

        Module module;
        for (Module module1 : modules) {
            module = module1;
            AddToModuleClasspathAction addToModuleClasspath = new AddToModuleClasspathAction(plugin.getProject(), module, getPaths());
            JMenuItem addToModuleClasspathMenu = new JMenuItem(addToModuleClasspath);
            add(addToModuleClasspathMenu);
        }

        add(new JSeparator());

        addToProjectLibrariesMenu();
        addToGlobalLibrariesMenu();

        if (getPaths().size() == 1) {
            OpenContainingFolderAction openContainingFolderAction = new OpenContainingFolderAction(getPaths().get(0), plugin.getProject());
            JMenuItem openContainingFolderMenu = new JMenuItem(openContainingFolderAction);
            add(openContainingFolderMenu);
        }

        CopyClasspathToClipboardAction copyClasspathToClipboardAction = new CopyClasspathToClipboardAction(getPaths());
        JMenuItem copyPathLocationMenu = new JMenuItem(copyClasspathToClipboardAction);
        add(copyPathLocationMenu);
    }

    private void addToGlobalLibrariesMenu() {
        Library[] globalLibraries = getGlobalLibraries();
        if (globalLibraries.length > 0) {
            JMenu addToGlobalLibraryMenu = new JMenu("Add to Global Library");
            for (Library globalLibrary : globalLibraries) {
                JMenuItem menuItem = new JMenuItem(new AddToGlobalLibraryAction(globalLibrary, getPaths()));
                addToGlobalLibraryMenu.add(menuItem);
            }
            add(addToGlobalLibraryMenu);
            add(new JSeparator());
        }
    }

    private void addToProjectLibrariesMenu() {
        Library[] projectLibraries = getProjectLibraries();
        if (projectLibraries.length > 0) {
            JMenu addToProjectLibraryMenu = new JMenu("Add to Project Library");
            for (Library library : projectLibraries) {
                JMenuItem menuItem = new JMenuItem(new AddToProjectLibraryAction(library, getPaths()));
                addToProjectLibraryMenu.add(menuItem);
            }
            add(addToProjectLibraryMenu);
            add(new JSeparator());
        }
    }

    private Library[] getGlobalLibraries() {
        LibraryTablesRegistrar registrar = LibraryTablesRegistrar.getInstance();
        LibraryTable libraryTable = registrar.getLibraryTable();
        return libraryTable.getLibraries();
    }

    private Library[] getProjectLibraries() {
        LibraryTablesRegistrar registrar = LibraryTablesRegistrar.getInstance();
        LibraryTable libraryTable = registrar.getLibraryTable(plugin.getProject());
        return libraryTable.getLibraries();
    }


    private List<String> getPaths() {

        TreePath[] selectionPaths = tree.getSelectionPaths();

        List<String> libraryPaths = new ArrayList<String>();

        for (TreePath selectionPath : selectionPaths) {
            ResultTreeNode resultNode = (ResultTreeNode) selectionPath.getLastPathComponent();
            if (resultNode.getUserObject() instanceof LibraryFinderResult) {
                LibraryFinderResult result = (LibraryFinderResult) resultNode.getUserObject();
                libraryPaths.add(result.getLibraryPath());
            }
        }
        return libraryPaths;
    }


    private Module[] getModules() {
        ModuleManager moduleManager = ModuleManager.getInstance(plugin.getProject());
        return moduleManager.getModules();
    }

}
