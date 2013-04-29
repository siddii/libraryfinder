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

import com.boxysystems.libraryfinder.model.Constants;
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
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Jan 8, 2007
 * Time: 2:15:46 PM
 */
public class FindLibraryIntentionAction extends LibraryFinderIntentionAction {
  private static Logger logger = Logger.getLogger(FindLibraryIntentionAction.class);


  @NotNull
  public String getFamilyName() {
    return Constants.FIND_LIBRARY_TITLE;
  }

  @NotNull
  public String getText() {
    return "Find Library for this import";
  }

  public boolean isAvailable(Project project, Editor editor, PsiFile file) {
    return getUnresolvedImport(editor, file) != null;
  }

  @Nullable
  private String getUnresolvedImport(Editor editor, PsiFile file) {
    try {
      if (file instanceof PsiJavaFile) {
        List<PsiImportStatementBase> unresolvedImports = getAllUnresolvedImports((PsiJavaFile) file);
        if (unresolvedImports != null && unresolvedImports.size() > 0) {
          int lineNo = editor.getCaretModel().getLogicalPosition().line;
          int startOffset = editor.getDocument().getLineStartOffset(lineNo);
          int endOffset = editor.getDocument().getLineEndOffset(lineNo);
          String importStatement = StringUtil.getImportClassFromLine(editor.getDocument().getCharsSequence().subSequence(startOffset, endOffset).toString());
          if (importStatement != null) {
            for (PsiImportStatementBase psiImportStatementBase : unresolvedImports) {
              if ((psiImportStatementBase != null) && (psiImportStatementBase.getImportReference() != null) &&
                (psiImportStatementBase.getImportReference().getQualifiedName() != null) &&
                importStatement.contains(psiImportStatementBase.getImportReference().getQualifiedName())) {
                return importStatement;
              }
            }
          }
        }
      }
    } catch (Exception e) {
      logger.error("Error during intention check " + e);
    }
    return null;
  }

  public void invoke(Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
    String fileName = getUnresolvedImport(editor, file);
    if (fileName != null) {

      LibraryFinderAction action = LibraryFinderAction.getInstance();
      action.performAction(project, fileName, PathUtil.getProjectRootFolder(project), false);
    }
  }
}
