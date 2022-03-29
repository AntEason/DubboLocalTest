package com.eason.dubbo.ui.dubboRpc;

import com.alibaba.fastjson.JSON;
import com.eason.dubbo.common.CacheType;
import com.eason.dubbo.db.DubboRpcConfig;
import com.eason.dubbo.db.RegisterCenterConfig;
import com.eason.dubbo.model.DubboCacheInfo;
import com.eason.dubbo.model.RegisterCenterCache;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBPanel;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class DubboPanel extends JBPanel {

    private JComboBox<String> registerCenter;
    private JButton saveButton;
    private JButton saveAsButton;
    private JTextField interfaceName;
    private JTextField methodName;
    private JButton runButton;
    private JPanel editorPane;
    private JPanel mainPanel;
    private JTextField group;
    private JTextField version;

    private final DubboCacheInfo dubboCacheInfo = new DubboCacheInfo();

    /**
     * Json editor req
     */
    private JsonEditor jsonEditorReq;
    /**
     * Json editor resp
     */
    private JsonEditor jsonEditorResp;

    Project project;

    TreePanel leftTree;

    public DubboPanel(@NotNull Project project, @NotNull TreePanel leftTree) {
        this.project = project;
        this.setLayout(new BorderLayout());
        this.add(this.mainPanel, BorderLayout.CENTER);

        //分割线
        JBSplitter mContentSplitter = new JBSplitter();
        mContentSplitter.setProportion(0.5f);
        this.jsonEditorReq = new JsonEditor(project);
        this.jsonEditorReq.setBorder(BorderFactory.createTitledBorder("Params"));
        this.jsonEditorResp = new JsonEditor(project);
        this.jsonEditorResp.setBorder(BorderFactory.createTitledBorder("Response"));
        mContentSplitter.setFirstComponent(jsonEditorReq);
        mContentSplitter.setSecondComponent(jsonEditorResp);
        this.editorPane.add(mContentSplitter, BorderLayout.CENTER);

        //初始化数据
        this.reset();

        //给定一个收藏名称进行收藏
        this.saveAsButton.addActionListener(e -> {
            DubboRpcConfig instance = DubboRpcConfig.getInstance();
            if (StringUtils.isBlank(interfaceName.getText()) && StringUtils.isBlank(methodName.getText())) {
                return;
            }
            this.addData();
            NameDialogue dialogue = new NameDialogue(project);
            dialogue.show();
            if (dialogue.isOK()) {
                String name = dialogue.getText();
                if (StringUtils.isBlank(name)) {
                    name = this.dubboCacheInfo.getMethodName() + "#" + this.dubboCacheInfo.getInterfaceName();
                }
                dubboCacheInfo.setName(name);
                instance.add(dubboCacheInfo, CacheType.COLLECTIONS);
                //刷新左边树结构
                leftTree.refresh();
            }
        });


        //默认使用接口名和方法名作为收藏名进行收藏
        this.saveButton.addActionListener(e -> {
            DubboRpcConfig instance = DubboRpcConfig.getInstance();
            this.addData();
            if (StringUtils.isBlank(interfaceName.getText()) && StringUtils.isBlank(methodName.getText())) {
                return;
            }
            this.addData();
            String name = this.dubboCacheInfo.getMethodName() + "#" + this.dubboCacheInfo.getInterfaceName();
            dubboCacheInfo.setName(name);
            System.out.println("project = " + dubboCacheInfo);
            instance.add(dubboCacheInfo, CacheType.COLLECTIONS);
            //刷新左边树结构
            leftTree.refresh();
        });

        this.runButton.addActionListener(e -> {

        });

        //下拉
        this.registerCenter.addItemListener(e -> {
            String item = (String) e.getItem();
            RegisterCenterCache registerCenterCache = RegisterCenterConfig.getInstance().getCache().stream()
                    .filter(x -> x.getName().equals(item)).findFirst().get();
            version.setText(registerCenterCache.getVersion());
            group.setText(registerCenterCache.getGroup());
        });

    }

    public void addData() {
        dubboCacheInfo.setDate(new Date());
        dubboCacheInfo.setInterfaceName(interfaceName.getText());
        dubboCacheInfo.setMethodName(methodName.getText());
        String registerCenterName = (String) registerCenter.getSelectedItem();
        dubboCacheInfo.setRegisterCenterName(registerCenterName);
        String body = jsonEditorReq.getDocumentText();
        if (StringUtils.isNoneBlank(body)) {
            DubboCacheInfo jsonObject = JSON.parseObject(body, DubboCacheInfo.class);
            dubboCacheInfo.setParam(jsonObject.getParam());
            dubboCacheInfo.setMethodType(jsonObject.getMethodType());
        } else {
            dubboCacheInfo.setParam(new ArrayList<>());
            dubboCacheInfo.setMethodType(new ArrayList<>());
        }
    }


    public void reset() {
        RegisterCenterConfig settings = RegisterCenterConfig.getInstance();
        List<String> dubboConfigs = settings.getCache().stream().map(x -> x.getName()).collect(Collectors.toList());
        this.registerCenter.removeAllItems();
        dubboConfigs.forEach(this.registerCenter::addItem);
    }


    /**
     * 刷新UI
     *
     * @param dubboPanel
     */
    public static void refreshUI(DubboPanel dubboPanel, DubboCacheInfo dubboCacheInfo) {
        dubboPanel.getInterfaceName().setText(dubboCacheInfo.getInterfaceName());
        dubboPanel.getMethodName().setText(dubboCacheInfo.getMethodName());
        JComboBox<String> jComboBox = dubboPanel.getRegisterCenter();
        for (int i = 0; i < jComboBox.getItemCount(); i++) {
            String str = jComboBox.getItemAt(i);
            if (str.equals(dubboCacheInfo.getRegisterCenterName())) {
                jComboBox.setSelectedIndex(i);
            }
        }
        Optional<RegisterCenterCache> optionalRegisterCenterCache = RegisterCenterConfig.getInstance().getCache().stream()
                .filter(x -> x.getName().equals(dubboCacheInfo.getRegisterCenterName())).findFirst();
        if (optionalRegisterCenterCache.isPresent()) {
            RegisterCenterCache registerCenterCache = optionalRegisterCenterCache.get();
            jComboBox.setSelectedItem(dubboCacheInfo.getName());
            JTextField textField4 = dubboPanel.getGroup();
            textField4.setText(registerCenterCache.getGroup());
            JTextField textField5 = dubboPanel.getVersion();
            textField5.setText(registerCenterCache.getVersion());
        }


        Map<String, Object> map = new HashMap<>();
        map.put("param", dubboCacheInfo.getParam());
        map.put("methodType", dubboCacheInfo.getMethodType());


        String json = JSON.toJSONString(map, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);

        WriteCommandAction.runWriteCommandAction(dubboPanel.getProject(), () -> dubboPanel.getJsonEditorReq().getDocument().setText(json));
        dubboPanel.reset();
        dubboPanel.updateUI();
    }


    public JComboBox<String> getRegisterCenter() {
        return registerCenter;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getSaveAsButton() {
        return saveAsButton;
    }

    public JTextField getInterfaceName() {
        return interfaceName;
    }

    public JTextField getMethodName() {
        return methodName;
    }

    public JButton getRunButton() {
        return runButton;
    }

    public JPanel getEditorPane() {
        return editorPane;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTextField getGroup() {
        return group;
    }

    public JTextField getVersion() {
        return version;
    }

    public DubboCacheInfo getDubboCacheInfo() {
        return dubboCacheInfo;
    }

    public JsonEditor getJsonEditorReq() {
        return jsonEditorReq;
    }

    public JsonEditor getJsonEditorResp() {
        return jsonEditorResp;
    }

    public Project getProject() {
        return project;
    }
}
