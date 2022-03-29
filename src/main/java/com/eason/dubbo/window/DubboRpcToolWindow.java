package com.eason.dubbo.window;

import com.eason.dubbo.ui.dubboRpc.DubboRpcPanel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class DubboRpcToolWindow implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        if (project.isDisposed() || toolWindow.isDisposed()) {
            return;
        }

        DubboRpcPanel registerTreePanel = new DubboRpcPanel(project, toolWindow);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(registerTreePanel, null, false);
        toolWindow.getContentManager().addContent(content);
    }
}
