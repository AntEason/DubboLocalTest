package com.eason.dubbo.db;

import com.eason.dubbo.common.CacheType;
import com.eason.dubbo.model.DubboCacheInfo;
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
        name = "DubboRpcConfig",
        storages = {@Storage("DubboRpcConfig.xml")}
)
public class DubboRpcConfig implements PersistentStateComponent<DubboRpcConfig> {


    /**
     * 存放收藏
     */
    public LinkedList<DubboCacheInfo> dubboCacheInfos = new LinkedList<>();

    /**
     * 存放历史
     */
    public LinkedList<DubboCacheInfo> historyDubboCacheInfos = new LinkedList<>();


    //限制最大历史记录条数
    private static final int MAX_HISTORY_SIZE = 200;


    public List<DubboCacheInfo> getCache(CacheType cacheType) {
        if (CacheType.COLLECTIONS.equals(cacheType)) {
            dubboCacheInfos.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
            return dubboCacheInfos;
        } else {
            historyDubboCacheInfos.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
            return historyDubboCacheInfos;
        }
    }

    public void add(DubboCacheInfo cacheInfo, CacheType cacheType) {
        if (dubboCacheInfos.size() >= MAX_HISTORY_SIZE) {
            this.historyDubboCacheInfos.addFirst(this.dubboCacheInfos.getLast());
            this.dubboCacheInfos.addFirst(cacheInfo);
            this.dubboCacheInfos.removeLast();
        } else {
            this.dubboCacheInfos.remove(cacheInfo);
            this.dubboCacheInfos.addFirst(cacheInfo);
        }
    }

    /**
     * 移除缓存
     */
    public void remove(DubboCacheInfo cacheInfo, CacheType cacheType) {
        if (CacheType.COLLECTIONS.equals(cacheType)) {
            this.dubboCacheInfos.remove(cacheInfo);
            this.historyDubboCacheInfos.addFirst(cacheInfo);
        } else {
            this.historyDubboCacheInfos.remove(cacheInfo);
        }
    }


    public static DubboRpcConfig getInstance() {
        return ServiceManager.getService(DubboRpcConfig.class);
    }


    @Nullable
    @Override
    public DubboRpcConfig getState() {
        return this;
    }


    @Override
    public void loadState(@NotNull DubboRpcConfig dubboConfigCache) {
        XmlSerializerUtil.copyBean(dubboConfigCache, this);
    }
}
