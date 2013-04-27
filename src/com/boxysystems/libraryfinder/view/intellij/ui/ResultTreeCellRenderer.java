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

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Dec 19, 2005
 * Time: 1:30:19 PM
 */
public class ResultTreeCellRenderer extends DefaultTreeCellRenderer {

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

        if ((node.getUserObject() != null) && (node.getUserObject() instanceof LibraryFinderResult)) {
            LibraryFinderResult libFinderResult = (LibraryFinderResult) node.getUserObject();
            if (leaf && libFinderResult != null) {
                setIcon(Icons.LIBRARY_ICON);
                setToolTipText(libFinderResult.getLibraryPath());
            }
        } else if ((node.getUserObject() != null) && (node.getUserObject() instanceof ResultTreeNode.ResultValue)) {
            ResultTreeNode.ResultValue result = (ResultTreeNode.ResultValue) node.getUserObject();
            if (result.isError()) {
                setIcon(Icons.ERROR_ICON);
            }
        }
        return this;
    }
}
