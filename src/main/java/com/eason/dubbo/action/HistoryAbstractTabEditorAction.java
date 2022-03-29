package com.eason.dubbo.action;

import com.eason.dubbo.common.CacheType;
import com.eason.dubbo.ui.dubboRpc.TreePanel;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class HistoryAbstractTabEditorAction extends AnAction {

    private TreePanel component;

    public HistoryAbstractTabEditorAction(TreePanel component) {
        super("History", "Switch to History", AllIcons.Vcs.History);
        this.component = component;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        this.component.refresh(CacheType.HISTORY);
    }
}
