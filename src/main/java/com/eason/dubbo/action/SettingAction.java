package com.eason.dubbo.action;

import com.eason.dubbo.utils.Constants;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import org.jetbrains.annotations.NotNull;

public class SettingAction extends AnAction {
    public SettingAction() {
        super("Default Settings", "Open the setting view", AllIcons.General.Settings);
    }
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ShowSettingsUtil.getInstance().showSettingsDialog(e.getProject(), Constants.PLUGIN_NAME);
    }
}
