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

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiImportStatementBase;
import com.intellij.psi.PsiJavaFile;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Siddique Hameed
 * Date: Jan 12, 2007
 * Time: 11:52:51 AM
 */
public abstract class LibraryFinderIntentionAction implements IntentionAction {


    @Nullable
    protected List<PsiImportStatementBase> getAllUnresolvedImports(PsiJavaFile file) {
        List<PsiImportStatementBase> unresolvedImports = null;
        try {
            unresolvedImports = new ArrayList<PsiImportStatementBase>();
            PsiImportList psiImportList = file.getImportList();
            if (psiImportList != null) {
                PsiImportStatementBase[] importStatements = psiImportList.getAllImportStatements();
                for (PsiImportStatementBase psiImportStatement : importStatements) {
                    if ((psiImportStatement != null) && (psiImportStatement.getImportReference() != null) && (psiImportStatement.getImportReference().resolve() == null)) {
                        unresolvedImports.add(psiImportStatement);
                    }
                }
            }
        } catch (Throwable t) {
            //ignore this
        }
        return unresolvedImports;
    }


    public boolean startInWriteAction() {
        return false;
    }
}
