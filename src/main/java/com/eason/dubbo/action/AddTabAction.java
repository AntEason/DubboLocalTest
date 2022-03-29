package com.eason.dubbo.action;

import com.eason.dubbo.ui.dubboRpc.TabBar;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public class AddTabAction extends AnAction {

    private TabBar tabBar;

    public AddTabAction(TabBar tabBar) {
        super("Add Tab", "Adds a tab", AllIcons.General.Add);
        this.tabBar = tabBar;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        this.tabBar.addTab();
    }
}
