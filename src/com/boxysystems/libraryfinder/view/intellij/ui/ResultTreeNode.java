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

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Dec 16, 2005
 * Time: 9:52:21 AM
 */
public class ResultTreeNode extends DefaultMutableTreeNode {

  private Collection<ResultTreeNode> children = new LinkedList<ResultTreeNode>();
  private LibraryFinderResult libFinderResult = null;


  public ResultTreeNode(String result) {
    super(new ResultValue(result));
  }

  public ResultTreeNode(LibraryFinderResult result) {
    super(result);
    this.libFinderResult = result;
  }


  public void setError(boolean error) {
    if (getUserObject() instanceof ResultValue) {
      ResultValue resultValue = (ResultValue) getUserObject();
      resultValue.setError(error);
    }
  }

  public boolean isError() {
    if (getUserObject() instanceof ResultValue) {
      ResultValue resultValue = (ResultValue) getUserObject();
      return resultValue.isError();
    }
    return false;
  }

  public boolean isLibrary() {
      return libFinderResult != null;
  }

  public void addChildNode(ResultTreeNode child) {
    children.add(child);
  }

  public Iterator<ResultTreeNode> iterator() {
    return children.iterator();
  }

  public static class ResultValue {
    private String resultString;

    public boolean isError() {
      return error;
    }

    public void setError(boolean error) {
      this.error = error;
    }

    private boolean error;

    public ResultValue(String resultString) {
      this.resultString = resultString;
    }

    public String toString() {
      return resultString;
    }
  }
}

