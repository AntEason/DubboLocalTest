package com.eason.dubbo.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class DubboCacheInfo implements Serializable {

    /**
     * Interface name
     */
    private String interfaceName;
    /**
     * Method name
     */
    private String methodName;
    /**
     * Method type
     */
    private List<String> methodType;
    /**
     * Param obj
     */
    private List<Object> param;
    /**
     * 创建时间
     */
    private Date date;
    /**
     * 注册中心
     */
    private String registerCenterName;

    private String name;

}
