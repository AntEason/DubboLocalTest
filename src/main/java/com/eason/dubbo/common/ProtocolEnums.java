package com.eason.dubbo.common;

public enum ProtocolEnums {
    /**
     * Dubbo address type enum
     */
    dubbo,
    /**
     * nacos address type enum
     */
    nacos,
    /**
     * Zookeeper address type enum
     */
    zookeeper;

    public static ProtocolEnums getByName(String name) {
        for (ProtocolEnums protocolEnums : ProtocolEnums.values()) {
            if (protocolEnums.name().equals(name)) {
                return protocolEnums;
            }
        }
        return ProtocolEnums.nacos;
    }

}
