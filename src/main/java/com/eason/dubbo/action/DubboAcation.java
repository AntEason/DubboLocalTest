package com.eason.dubbo.action;

import com.eason.dubbo.utils.PluginUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

import static com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR;

public class DubboAcation extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        Editor editor = event.getData(EDITOR);
        PsiElement element = event.getData(CommonDataKeys.PSI_ELEMENT);
        if (PluginUtils.isAvailable(project, editor, element)) {
            PluginUtils.openToolWindow(project,
                    element,editor);
        }
    }
}
