package com.eason.dubbo.config;

import com.eason.dubbo.db.RegisterCenterConfig;
import com.eason.dubbo.model.RegisterCenterCache;
import com.eason.dubbo.ui.registerCenter.AppSettingsComponent;
import com.eason.dubbo.utils.Constants;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Date;
import java.util.List;

public class AppSettingsConfigurable implements Configurable {

    private AppSettingsComponent appSettingsComponent;


    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return Constants.PLUGIN_NAME;
    }

    @Override
    public @Nullable
    JComponent createComponent() {
        appSettingsComponent = new AppSettingsComponent();
        return appSettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        return appSettingsComponent.isModified();
    }

    @Override
    public void apply() {
        RegisterCenterConfig config = RegisterCenterConfig.getInstance();
        List<RegisterCenterCache> registerCenterCaches = appSettingsComponent.getSettings();
        registerCenterCaches.forEach(x -> x.setDate(new Date()));
        config.addAll(registerCenterCaches);
    }


    @Override
    public void reset() {
        RegisterCenterConfig config = RegisterCenterConfig.getInstance();
        List<RegisterCenterCache> registerCenterCaches = config.getCache();
        appSettingsComponent.reset(registerCenterCaches);
    }
}
