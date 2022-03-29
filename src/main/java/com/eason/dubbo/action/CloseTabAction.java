package com.eason.dubbo.action;

import com.eason.dubbo.ui.dubboRpc.TabBar;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import org.jetbrains.annotations.NotNull;

public class CloseTabAction extends AnAction {
    private TabBar tabBar;
    private String tabId;

    public CloseTabAction(TabBar tabBar, String id) {
        super("Close tab", "Closes a tab", AllIcons.Actions.Close);
        this.tabBar = tabBar;
        this.tabId = id;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        e.getProject();
        tabBar.closeTab(tabId);
    }

    public void update(@NotNull AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        presentation.setIcon(AllIcons.Actions.Close);

        //鼠标悬停图标
        Presentation presentation1 = e.getPresentation();
        presentation1.setHoveredIcon(AllIcons.Actions.CloseHovered);
    }
}
