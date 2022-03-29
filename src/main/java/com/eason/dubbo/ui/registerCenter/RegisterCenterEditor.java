package com.eason.dubbo.ui.registerCenter;

import com.eason.dubbo.model.RegisterCenterCache;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.util.Function;
import com.intellij.util.ui.table.TableModelEditor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class RegisterCenterEditor implements TableModelEditor.DialogItemEditor<RegisterCenterCache> {

    private AppSettingsComponent appSettingsComponent;

    public RegisterCenterEditor(AppSettingsComponent appSettingsComponent) {
        this.appSettingsComponent = appSettingsComponent;
    }


    @NotNull
    @Override
    public Class<RegisterCenterCache> getItemClass() {
        return RegisterCenterCache.class;
    }

    @Override
    public RegisterCenterCache clone(@NotNull RegisterCenterCache registerCenterCache, boolean forInPlaceEditing) {
        return registerCenterCache;
    }

    @Override
    public void edit(@NotNull RegisterCenterCache item, @NotNull Function<? super RegisterCenterCache, ? extends RegisterCenterCache> mutator, boolean isAdd) {
        SettingDialog settingDialog = new SettingDialog(item);
        final DialogBuilder dialogBuilder = new DialogBuilder(appSettingsComponent.getPanel())
                .title("RegisterCenter Setting").centerPanel(settingDialog.getPanel());
        //对应工具栏得添加或者编辑按钮事件
        if (dialogBuilder.show() == DialogWrapper.OK_EXIT_CODE) {
            RegisterCenterCache registerCenterCache = settingDialog.getRegisterCenterCache();
            if (StringUtils.isBlank(registerCenterCache.getProtocol().name()) || StringUtils.isBlank(registerCenterCache.getName())) {
                dialogBuilder.show();
                return;
            }
            mutator.fun(registerCenterCache);
        }
    }

    @Override
    public void applyEdited(@NotNull RegisterCenterCache oldItem, @NotNull RegisterCenterCache newItem) {
        System.out.println("===================[applyEdited]=======================");
    }

    @Override
    public boolean isUseDialogToAdd() {
        //设置为弹窗进行添加
        return true;
    }


}
