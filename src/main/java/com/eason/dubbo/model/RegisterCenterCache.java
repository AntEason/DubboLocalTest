package com.eason.dubbo.model;

import com.eason.dubbo.common.ProtocolEnums;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RegisterCenterCache implements Serializable {
    /**
     * 名称
     */
    String name;
    /**
     * 协议
     */
    ProtocolEnums protocol;
    /**
     * ip
     */
    String ip;
    /**
     * 端口
     */
    String port;
    /**
     * 分组
     */
    String group;
    /**
     * 版本
     */
    String version;
    /**
     * 创建时间
     */
    Date date;



}
