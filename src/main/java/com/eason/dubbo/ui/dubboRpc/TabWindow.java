package com.eason.dubbo.ui.dubboRpc;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class TabWindow extends JPanel {


    private String id;

    private DubboPanel dubboPanel;

    public TabWindow(@NotNull Project project, @NotNull String id, @NotNull TreePanel leftTree) {
        dubboPanel = new DubboPanel(project, leftTree);
        this.setLayout(new BorderLayout());
        this.add(dubboPanel, BorderLayout.CENTER, 0);
        this.id = id;
    }

    public final String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DubboPanel getDubboPanel() {
        return dubboPanel;
    }

    public void setDubboPanel(DubboPanel dubboPanel) {
        this.dubboPanel = dubboPanel;
    }
}
