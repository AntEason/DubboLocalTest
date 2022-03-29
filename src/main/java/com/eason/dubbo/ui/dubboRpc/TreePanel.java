package com.eason.dubbo.ui.dubboRpc;

import com.eason.dubbo.common.CacheType;
import com.eason.dubbo.db.DubboRpcConfig;
import com.eason.dubbo.model.DubboCacheInfo;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.tabs.TabInfo;
import com.intellij.ui.treeStructure.Tree;
import org.apache.commons.collections.CollectionUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class TreePanel extends JPanel {

    private Tree tree;

    private CacheType cacheType;


    public TreePanel(CacheType cacheType) {
        this.cacheType = cacheType;
        tree = new Tree();
        JBScrollPane jScrollBar = new JBScrollPane(tree);
        this.setLayout(new BorderLayout());
        this.add(jScrollBar, BorderLayout.CENTER);
        this.repaint();
        this.validate();
    }

    public void refresh(CacheType cacheType) {
        this.cacheType = cacheType;
        this.refresh();
    }

    /**
     * 刷新数据并添加事件
     */
    public void refresh() {
        //添加数据模型
        this.setModel();
        //添加监听
        this.setTreeListener();
        //添加鼠标右键事件
        this.addTreeRightButtonAction();
    }

    public void setModel() {
        tree.clearSelection();
        DubboRpcConfig instance = DubboRpcConfig.getInstance();
        List<DubboCacheInfo> dubboCacheInfos = instance.getCache(this.cacheType);
        DefaultMutableTreeNode defaultMutableTreeNode = new DefaultMutableTreeNode(this.cacheType.name().toLowerCase(Locale.ROOT));
        if (CollectionUtils.isNotEmpty(dubboCacheInfos)) {
            dubboCacheInfos.stream().map(x -> new DefaultMutableTreeNode(x.getName(), true)).forEach(defaultMutableTreeNode::add);
        }
        tree.setModel(new DefaultTreeModel(defaultMutableTreeNode, false));
        tree.updateUI();
    }


    /**
     * 添加鼠标左键点击事件
     */
    private void setTreeListener() {
        //添加左测菜单点击后渲染右侧数据
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode lastSelectedPathComponent = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (lastSelectedPathComponent == null) {
                return;
            }
            Object userObject = lastSelectedPathComponent.getUserObject();
            if (userObject instanceof String) {
                String name = (String) userObject;
                DubboRpcConfig dubboRpcConfig = DubboRpcConfig.getInstance();
                Optional<DubboCacheInfo> optionalDubboCacheInfo = dubboRpcConfig.getCache(this.cacheType).stream().filter(x -> x.getName().equals(name)).findFirst();
                if (optionalDubboCacheInfo.isPresent()) {
                    DubboCacheInfo cacheInfo = optionalDubboCacheInfo.get();
                    TabInfo selectedInfo = TabBar.getSelectionTabInfo();
                    TabWindow component = (TabWindow) selectedInfo.getComponent();
                    DubboPanel.refreshUI(component.getDubboPanel(), cacheInfo);
                }
            }
        });
    }

    /**
     * 添加鼠标右键事件
     */
    private void addTreeRightButtonAction() {
        //右键删除操作
        JPopupMenu menu = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Delete");
        menu.add(menuItem);
        if (CacheType.HISTORY.equals(this.cacheType)) {
            JMenuItem menuItemAll = new JMenuItem("Delete all");
            menu.add(menuItemAll);
            menuItemAll.addActionListener(e -> {
                //删除所有
                DubboRpcConfig.getInstance().historyDubboCacheInfos.clear();
                this.refresh(this.cacheType);
            });
        }

        menuItem.addActionListener(e -> {
            DefaultMutableTreeNode lastSelectedPathComponent = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (lastSelectedPathComponent == null) {
                return;
            }
            Object userObject = lastSelectedPathComponent.getUserObject();
            if (userObject instanceof String) {
                String name = (String) userObject;
                DubboRpcConfig dubboRpcConfig = DubboRpcConfig.getInstance();
                Optional<DubboCacheInfo> dubboCacheInfo = dubboRpcConfig.getCache(this.cacheType).stream().filter(x -> x.getName().equals(name)).findFirst();
                if (dubboCacheInfo.isPresent()) {
                    dubboRpcConfig.remove(dubboCacheInfo.get(), this.cacheType);
                }
                this.refresh(this.cacheType);
            }
        });
        //有限显示删除popup
        this.tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX();
                int y = e.getY();
                if (e.getButton() == MouseEvent.BUTTON3) {
                    TreePath pathForLocation = tree.getPathForLocation(x, y);
                    tree.setSelectionPath(pathForLocation);
                    menu.show(tree, x, y);
                }
            }
        });

    }
}
