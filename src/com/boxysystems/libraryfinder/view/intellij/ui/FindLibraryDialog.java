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
import com.boxysystems.libraryfinder.model.LibraryFinderQuery;
import com.boxysystems.libraryfinder.view.intellij.LibraryFinderHistory;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.ui.TextFieldWithHistory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class FindLibraryDialog extends com.intellij.openapi.ui.DialogWrapper {
    private JPanel contentPane;
    private TextFieldWithHistory txtFileNamePattern;
    private TextFieldWithHistory txtDirectoryToSearch;
    private JButton btnFileChooser;
    private JCheckBox chkRegex;
    private JCheckBox chkCaseSensitive;
    private Project project;
    private Component[] componentOrder = {txtFileNamePattern,
            chkRegex, chkCaseSensitive, txtDirectoryToSearch, btnFileChooser};
    private MyFocusTraversalPolicy traversalPolicy = new MyFocusTraversalPolicy(componentOrder);

    public FindLibraryDialog(Project project) {
        super(project, true);
        this.project = project;
        init();
        setTitle(Constants.FIND_LIBRARY_TITLE);
        btnFileChooser.addActionListener(new FileChooserListener(project, txtDirectoryToSearch));
        setOKActionEnabled(false);
        addListeners(txtFileNamePattern);
        addListeners(txtDirectoryToSearch);
        pack();
        setHistoryValuesToDropDowns();
        this.getContentPane().setFocusTraversalPolicyProvider(true);
        this.getContentPane().setFocusTraversalPolicy(traversalPolicy);
    }

    public FindLibraryDialog(Project project, String fileNamePattern, String directoryToSearch, boolean isRegex) {
        this(project);
        txtFileNamePattern.setText(fileNamePattern);
        txtDirectoryToSearch.setText(directoryToSearch);
        chkRegex.setSelected(isRegex);
    }

    private void addListeners(TextFieldWithHistory txtField) {
        txtField.addDocumentListener(new OKButtonEnableListener());
    }

    public JComponent getPreferredFocusedComponent() {
        return txtFileNamePattern;
    }

    public void setEditorSelectedTextToFileName() {
        final FileEditorManager editorManager = FileEditorManager.getInstance(project);
        final Editor editor = editorManager.getSelectedTextEditor();

        if (editor != null && hasSelectedText(editor)) {
            String selectedText = editor.getSelectionModel().getSelectedText();
            if (selectedText != null) {
                txtFileNamePattern.setText(selectedText.trim());
                txtFileNamePattern.selectText();
            }
        }
    }

    private boolean hasSelectedText(Editor editor) {
        return editor != null && isValidText(editor.getSelectionModel().getSelectedText());
    }

    private boolean isValidText(String str) {
        return (str != null) && str.trim().length() > 0;
    }

    public String getSearchPath() {
        return txtDirectoryToSearch.getText();
    }

    public String getPattern() {
        return txtFileNamePattern.getText();
    }


    public boolean isRegex() {
        return chkRegex.isSelected();
    }

    public boolean isCaseSensitive() {
        return chkCaseSensitive.isSelected();
    }

    protected JComponent createCenterPanel() {
        return contentPane;
    }

    private class OKButtonEnableListener implements DocumentListener, ItemListener {

        private void enableOrDisableOKButton() {
            if (isValidText(txtFileNamePattern.getText()) && (isValidText(txtDirectoryToSearch.getText()))) {
                setOKActionEnabled(true);
            } else {
                setOKActionEnabled(false);
            }
        }

        public void insertUpdate(DocumentEvent documentEvent) {
            enableOrDisableOKButton();
        }

        public void removeUpdate(DocumentEvent documentEvent) {
            enableOrDisableOKButton();
        }

        public void changedUpdate(DocumentEvent documentEvent) {
            enableOrDisableOKButton();
        }

        public void itemStateChanged(ItemEvent itemEvent) {
            enableOrDisableOKButton();
        }
    }

    private void setHistoryValuesToDropDowns() {
        LibraryFinderHistory historyComponent = LibraryFinderHistory.getInstance();
        List<String> fileNames = historyComponent.getResources();
        txtFileNamePattern.setHistory(fileNames);
        List<String> dirs = historyComponent.getDirs();
        txtDirectoryToSearch.setHistory(dirs);
    }

    public LibraryFinderQuery getSearchQuery() {
        LibraryFinderQuery query = new LibraryFinderQuery(txtDirectoryToSearch.getText(), txtFileNamePattern.getText());
        query.setRegex(chkRegex.isSelected());
        query.setCaseSensitive(chkCaseSensitive.isSelected());
        query.setVerboseMode(true);
        return query;
    }

    private class MyFocusTraversalPolicy
            extends FocusTraversalPolicy {
        Vector<Component> order;

        public MyFocusTraversalPolicy(Component componentOrder[]) {
            this.order = new Vector<Component>(Arrays.asList(componentOrder));
            this.order.addAll(order);
        }

        public Component getComponentAfter(Container focusCycleRoot,
                                           Component aComponent) {
            int idx = (order.indexOf(aComponent) + 1) % order.size();
            return order.get(idx);
        }

        public Component getComponentBefore(Container focusCycleRoot,
                                            Component aComponent) {
            int idx = order.indexOf(aComponent) - 1;
            if (idx < 0) {
                idx = order.size() - 1;
            }
            return order.get(idx);
        }

        public Component getDefaultComponent(Container focusCycleRoot) {
            return order.get(0);
        }

        public Component getLastComponent(Container focusCycleRoot) {
            return order.lastElement();
        }

        public Component getFirstComponent(Container focusCycleRoot) {
            return order.get(0);
        }
    }
}
