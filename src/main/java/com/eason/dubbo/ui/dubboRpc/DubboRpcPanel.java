package com.eason.dubbo.ui.dubboRpc;

import com.eason.dubbo.action.AddTabAction;
import com.eason.dubbo.action.CollectionsAbstractTabEditorAction;
import com.eason.dubbo.action.HistoryAbstractTabEditorAction;
import com.eason.dubbo.action.SettingAction;
import com.eason.dubbo.common.CacheType;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.JBSplitter;

import javax.swing.*;

public class DubboRpcPanel extends SimpleToolWindowPanel implements Disposable {

    private TreePanel leftTree;

    private ActionManager actionManager;

    private TabBar tabBar;

    public DubboRpcPanel(Project project, ToolWindow toolWindow) {
        super(false, true);
        this.actionManager = ActionManager.getInstance();

        //分割线
        JBSplitter mContentSplitter = new JBSplitter();
        mContentSplitter.setProportion(0.1f);
        this.leftTree = new TreePanel(CacheType.COLLECTIONS);
        //左树结构,默认为收藏
        this.leftTree.refresh();
        mContentSplitter.setFirstComponent(this.leftTree);
        //tabBar
        this.tabBar = new TabBar(project, this.leftTree);
        mContentSplitter.setSecondComponent(this.tabBar);
        this.setToolbar(createToolbar());
        this.setContent(mContentSplitter);
    }


    private JComponent createToolbar() {
        AddTabAction addTabAction = new AddTabAction(this.tabBar);

        HistoryAbstractTabEditorAction abstractTabEditorAction = new HistoryAbstractTabEditorAction(this.leftTree);
        CollectionsAbstractTabEditorAction collectionsAbstractTabEditorAction = new CollectionsAbstractTabEditorAction(this.leftTree);
        SettingAction settingAction = new SettingAction();
        DefaultActionGroup actionGroup = new DefaultActionGroup();
        actionGroup.add(addTabAction);
        actionGroup.add(collectionsAbstractTabEditorAction);
        actionGroup.add(abstractTabEditorAction);
        actionGroup.add(settingAction);
        ActionToolbar actionToolbar = this.actionManager.createActionToolbar("toolbar", actionGroup, false);
        actionToolbar.setTargetComponent(this.tabBar);
        return actionToolbar.getComponent();
    }


    @Override
    public void dispose() {

    }
}
