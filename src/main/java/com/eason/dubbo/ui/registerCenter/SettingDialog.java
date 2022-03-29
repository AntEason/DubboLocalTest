package com.eason.dubbo.ui.registerCenter;

import com.eason.dubbo.common.ProtocolEnums;
import com.eason.dubbo.model.RegisterCenterCache;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;

public class SettingDialog {

    private JComboBox<String> comboBox;
    private JBTextField nameField;
    private JBTextField ipField;
    private JBTextField portField;
    private JBTextField versionField;
    private JBTextField groupField;

    private JPanel panel;

    public SettingDialog(RegisterCenterCache cache) {
        this.comboBox = new ComboBox<>();
        ProtocolEnums[] values = ProtocolEnums.values();
        for (ProtocolEnums value : values) {
            this.comboBox.addItem(value.name());
        }
        if (cache.getProtocol() != null && StringUtils.isNotBlank(cache.getProtocol().name())) {
            this.comboBox.setSelectedItem(cache.getProtocol().name());
        } else {
            this.comboBox.setSelectedItem(ProtocolEnums.nacos.name());
        }
        this.nameField = new JBTextField(cache.getName());
        this.ipField = new JBTextField(cache.getIp());
        this.portField = new JBTextField(cache.getPort());
        this.versionField = new JBTextField(cache.getVersion());
        this.groupField = new JBTextField(cache.getGroup());

        this.panel = FormBuilder.createFormBuilder()
                .addLabeledComponent("Name", nameField)
                .addLabeledComponent("Type", comboBox)
                .addLabeledComponent("IP", ipField)
                .addLabeledComponent("Port", portField)
                .addLabeledComponent("Version", versionField)
                .addLabeledComponent("Group", groupField)
                .getPanel();
    }

    public JPanel getPanel() {
        return panel;
    }

    public RegisterCenterCache getRegisterCenterCache() {
        String selectedItem = (String) this.comboBox.getSelectedItem();
        String port = this.portField.getText();
        String version = this.versionField.getText();
        String group = this.groupField.getText();
        String ip = this.ipField.getText();
        String nameField = this.nameField.getText();
        //校验重复
        RegisterCenterCache configurableDubboSettings = new RegisterCenterCache();
        configurableDubboSettings.setName(nameField);
        configurableDubboSettings.setProtocol(ProtocolEnums.getByName(selectedItem));
        configurableDubboSettings.setIp(ip);
        configurableDubboSettings.setPort(port);
        configurableDubboSettings.setVersion(version);
        configurableDubboSettings.setGroup(group);
        return configurableDubboSettings;
    }

}
