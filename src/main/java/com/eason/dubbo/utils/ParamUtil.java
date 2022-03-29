package com.eason.dubbo.utils;

import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiParameterList;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>Description: </p>
 *
 * @author yanglx
 * @version 1.0.0
 * @email "mailto:dev_ylx@163.com"
 * @date 2021.02.20 15:57
 * @since 1.0.0
 */
public class ParamUtil {
    /**
     * 初始化参数
     *
     * @param psiParameterList psi parameter list
     * @return the object [ ]
     * @since 1.0.0
     */
    public static List<Object> getInitParamArray(PsiParameterList psiParameterList) {
        return Stream.of(psiParameterList.getParameters())
                .map(x -> ParamUtil.getValue(x)).collect(Collectors.toList());
    }

    public static Object getValue(PsiParameter psiParameter) {
        Map<String, String> map = new HashMap<>();
        SupportType supportType = SupportType.touch(psiParameter);
        return supportType.getValue(psiParameter, map);
    }
}
