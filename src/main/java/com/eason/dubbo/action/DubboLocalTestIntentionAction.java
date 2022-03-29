package com.eason.dubbo.action;

import com.eason.dubbo.utils.Constants;
import com.eason.dubbo.utils.PluginUtils;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

public class DubboLocalTestIntentionAction extends PsiElementBaseIntentionAction {
    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) throws IncorrectOperationException {
        if (project.isDisposed()) {
            return;
        }

        PluginUtils.openToolWindow(project, psiElement, editor);
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) {
        return PluginUtils.isAvailable(project, editor, psiElement);
    }

    @Override
    public @NotNull
    @IntentionFamilyName String getFamilyName() {
        return Constants.PLUGIN_NAME;
    }
}
