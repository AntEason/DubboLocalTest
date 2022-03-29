package com.eason.dubbo.ui.registerCenter;

import com.eason.dubbo.common.ProtocolEnums;
import com.eason.dubbo.model.RegisterCenterCache;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.table.TableModelEditor;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Locale;

public class AppSettingsComponent {

    private JComponent jComponent;

    private TableModelEditor<RegisterCenterCache> registerCenterCacheTableModelEditor;


    /**
     * 表格
     */
    private static final ColumnInfo[] COLUMNS = {
            new TableModelEditor.EditableColumnInfo<RegisterCenterCache, String>("Name") {
                @Override
                public String valueOf(RegisterCenterCache registerCenterCache) {
                    return registerCenterCache.getName();
                }

                @Override
                public void setValue(RegisterCenterCache registerCenterCache, String value) {
                    registerCenterCache.setName(value);
                }

                @Override
                public boolean isCellEditable(RegisterCenterCache registerCenterCache) {
                    return false;
                }
            }, new TableModelEditor.EditableColumnInfo<RegisterCenterCache, String>("Protocol") {
        @Override
        public String valueOf(RegisterCenterCache registerCenterCache) {
            if (registerCenterCache.getProtocol() == null) {
                return "";
            }
            return registerCenterCache.getProtocol().name();
        }

        @Override
        public void setValue(RegisterCenterCache registerCenterCache, String value) {
            registerCenterCache.setProtocol(ProtocolEnums.getByName(value.toLowerCase(Locale.ROOT)));
        }

        @Override
        public boolean isCellEditable(RegisterCenterCache registerCenterCache) {
            return false;
        }
    }, new TableModelEditor.EditableColumnInfo<RegisterCenterCache, String>("Ip") {
        @Override
        public String valueOf(RegisterCenterCache registerCenterCache) {
            return registerCenterCache.getIp();
        }

        @Override
        public void setValue(RegisterCenterCache registerCenterCache, String value) {
            registerCenterCache.setIp(value);
        }

        @Override
        public boolean isCellEditable(RegisterCenterCache registerCenterCache) {
            return false;
        }
    }, new TableModelEditor.EditableColumnInfo<RegisterCenterCache, String>("Port") {
        @Override
        public String valueOf(RegisterCenterCache registerCenterCache) {
            return registerCenterCache.getPort();
        }

        @Override
        public void setValue(RegisterCenterCache registerCenterCache, String value) {
            registerCenterCache.setPort(value);
        }

        @Override
        public boolean isCellEditable(RegisterCenterCache registerCenterCache) {
            return false;
        }
    }, new TableModelEditor.EditableColumnInfo<RegisterCenterCache, String>("Group") {
        @Override
        public String valueOf(RegisterCenterCache registerCenterCache) {
            return registerCenterCache.getGroup();
        }

        @Override
        public void setValue(RegisterCenterCache registerCenterCache, String value) {
            registerCenterCache.setGroup(value);
        }

        @Override
        public boolean isCellEditable(RegisterCenterCache registerCenterCache) {
            return false;
        }
    }, new TableModelEditor.EditableColumnInfo<RegisterCenterCache, String>("Version") {
        @Override
        public String valueOf(RegisterCenterCache registerCenterCache) {
            return registerCenterCache.getVersion();
        }

        @Override
        public void setValue(RegisterCenterCache registerCenterCache, String value) {
            registerCenterCache.setVersion(value);
        }

        @Override
        public boolean isCellEditable(RegisterCenterCache registerCenterCache) {
            return false;
        }
    }};


    public AppSettingsComponent() {
        jComponent = new JPanel();
        RegisterCenterEditor myDialogItemEditor = new RegisterCenterEditor(this);

        registerCenterCacheTableModelEditor = new TableModelEditor<>(COLUMNS,
                myDialogItemEditor,
                "No dubbo configured");
        jComponent.setLayout(new BorderLayout());
        jComponent.add(registerCenterCacheTableModelEditor.createComponent(), BorderLayout.CENTER);
    }

    /**
     * 配置是否有变更
     *
     * @return
     */
    public boolean isModified() {
        return this.registerCenterCacheTableModelEditor.isModified();
    }


    /**
     * 返回配置数据集
     *
     * @return
     */
    public List<RegisterCenterCache> getSettings() {
        return this.registerCenterCacheTableModelEditor.apply();
    }

    /**
     * 传入配置数据集用于重置配置
     *
     * @param list
     */
    public void reset(List<RegisterCenterCache> list) {
        this.registerCenterCacheTableModelEditor.reset(list);
    }

    public JComponent getPanel() {
        return jComponent;
    }
}
