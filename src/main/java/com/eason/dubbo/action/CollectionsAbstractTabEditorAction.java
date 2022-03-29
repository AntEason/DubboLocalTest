package com.eason.dubbo.action;

import com.eason.dubbo.common.CacheType;
import com.eason.dubbo.ui.dubboRpc.TreePanel;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class CollectionsAbstractTabEditorAction extends AnAction {

    private TreePanel component;

    public CollectionsAbstractTabEditorAction(TreePanel component) {
        super("Collections", "Switch to collections", AllIcons.Nodes.Folder);
        this.component = component;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        this.component.refresh(CacheType.COLLECTIONS);
    }
}