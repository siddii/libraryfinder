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

package com.boxysystems.libraryfinder.view.intellij.intention;

import com.boxysystems.jgoogleanalytics.FocusPoint;
import com.boxysystems.libraryfinder.model.Constants;
import com.boxysystems.libraryfinder.model.PatternUtil;
import com.boxysystems.libraryfinder.model.StringUtil;
import com.boxysystems.libraryfinder.view.intellij.LibraryFinderPlugin;
import com.boxysystems.libraryfinder.view.intellij.PathUtil;
import com.boxysystems.libraryfinder.view.intellij.actions.LibraryFinderAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiImportStatementBase;
import com.intellij.psi.PsiJavaFile;
import com.intellij.util.IncorrectOperationException;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Jan 15, 2007
 * Time: 10:44:57 AM
 */
public class FindLibrariesIntentionAction extends LibraryFinderIntentionAction {
  private static Logger logger = Logger.getLogger(FindLibrariesIntentionAction.class);

  private FocusPoint focusPoint = new FocusPoint(Constants.FIND_LIBRARIES_INTENTION_ACTION);

  @NotNull
  public String getText() {
    return "Find Libraries for all unresolved imports";
  }

  @NotNull
  public String getFamilyName() {
    return "Find Libraries";
  }

  public boolean isAvailable(Project project, Editor editor, PsiFile psiFile) {
    try {
      if (psiFile instanceof PsiJavaFile) {
        List<PsiImportStatementBase> unresolvedImports = getAllUnresolvedImports((PsiJavaFile) psiFile);
        if (unresolvedImports != null && unresolvedImports.size() > 1) {
          int lineNo = editor.getCaretModel().getLogicalPosition().line;
          int startOffset = editor.getDocument().getLineStartOffset(lineNo);
          int endOffset = editor.getDocument().getLineEndOffset(lineNo);
          String importStatement = StringUtil.getImportClassFromLine(getEditorLineString(editor, startOffset, endOffset));
          if (importStatement != null) {
            return true;
          }
        }
      }
    } catch (Exception e) {
      //ignore in case of any runtime errors
      logger.error("Error during intention check " + e);
    }
    return false;
  }

  private String getEditorLineString(Editor editor, int startOffset, int endOffset) {
    return editor.getDocument().getCharsSequence().subSequence(startOffset, endOffset).toString();
  }

  public void invoke(Project project, Editor editor, PsiFile psiFile) throws IncorrectOperationException {
    List<PsiImportStatementBase> unresolvedImports = getAllUnresolvedImports((PsiJavaFile) psiFile);
    if (unresolvedImports != null && unresolvedImports.size() > 0) {
      LibraryFinderPlugin.instance(project).track(focusPoint);
      String fileNamePattern = getImportClassNames(unresolvedImports);
      LibraryFinderAction action = LibraryFinderAction.getInstance();
      action.performAction(project, fileNamePattern, PathUtil.getProjectRootFolder(project), true);
    }
  }

  private String getImportClassNames(List<PsiImportStatementBase> unresolvedImports) {
    StringBuffer importClassNames = new StringBuffer();
    for (PsiImportStatementBase psiImportStatementBase : unresolvedImports) {
      if (psiImportStatementBase != null && psiImportStatementBase.getImportReference() != null && psiImportStatementBase.getImportReference().getQualifiedName() != null) {
        importClassNames.append(PatternUtil.generateFileNamePattern(psiImportStatementBase.getImportReference().getQualifiedName()));
        importClassNames.append(Constants.REGEX_OR_SEPARATOR);
      }
    }
    return importClassNames.toString();
  }
}
