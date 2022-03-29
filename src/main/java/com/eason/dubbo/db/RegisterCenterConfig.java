package com.eason.dubbo.db;

import cn.hutool.core.collection.CollectionUtil;
import com.eason.dubbo.common.ProtocolEnums;
import com.eason.dubbo.model.RegisterCenterCache;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

@State(
        name = "RegisterCenterConfig",
        storages = {@Storage("RegisterCenterConfig.xml")}
)
public class RegisterCenterConfig implements PersistentStateComponent<RegisterCenterConfig> {

    private LinkedList<RegisterCenterCache> registerCenterCaches = new LinkedList<>();

    public List<RegisterCenterCache> getCache() {
        if (CollectionUtil.isEmpty(this.registerCenterCaches)) {
            RegisterCenterCache registerCenterCache = new RegisterCenterCache();
            registerCenterCache.setIp("127.0.0.1");
            registerCenterCache.setPort("2181");
            registerCenterCache.setProtocol(ProtocolEnums.zookeeper);
            registerCenterCache.setVersion("3.0.0");
            registerCenterCache.setName("default");
            this.registerCenterCaches.add(registerCenterCache);
        }
        registerCenterCaches.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
        return this.registerCenterCaches;
    }

    public void addAll(List<RegisterCenterCache> registerCenterCaches) {
        this.registerCenterCaches.clear();
        this.registerCenterCaches.addAll(registerCenterCaches);
    }


    public static RegisterCenterConfig getInstance() {
        return ServiceManager.getService(RegisterCenterConfig.class);
    }


    @Override
    public @Nullable
    RegisterCenterConfig getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull RegisterCenterConfig registerCenterConfig) {
        XmlSerializerUtil.copyBean(registerCenterConfig, this);
    }
}
