package com.eason.dubbo.utils;

import com.eason.dubbo.db.RegisterCenterConfig;
import com.eason.dubbo.model.DubboCacheInfo;
import com.eason.dubbo.model.RegisterCenterCache;
import com.eason.dubbo.ui.dubboRpc.DubboPanel;
import com.eason.dubbo.ui.dubboRpc.TabBar;
import com.eason.dubbo.ui.dubboRpc.TabWindow;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.*;
import com.intellij.ui.tabs.TabInfo;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PluginUtils {

    /**
     * element 只有是 method 或者是 method 引用时才可用.
     *
     * @param project
     * @param editor
     * @param element
     * @return
     */
    public static boolean isAvailable(Project project,
                                      Editor editor,
                                      PsiElement element) {
        if (project == null || editor == null) {
            return false;
        }

        return getPsiMethod(element) != null;
    }

    /**
     * 解析当前光标选中的元素, 返回 PsiMethod.
     *
     * @param element
     * @return
     */
    public static PsiMethod getPsiMethod(PsiElement element) {
        PsiMethod method = null;
        if (element instanceof PsiMethod) {
            method = (PsiMethod) element;
        } else if (element instanceof PsiIdentifier) {
            // 如果标识符是方法
            if (element.getParent() instanceof PsiMethod) {
                method = (PsiMethod) element.getParent();
            } else if (element.getParent() instanceof PsiReferenceExpression) {
                // 如果标识符是方法引用
                PsiElement resolve = ((PsiReferenceExpression) element.getParent()).resolve();
                if (resolve instanceof PsiMethod) {
                    method = (PsiMethod) resolve;
                }
            }
        }

        return method;
    }

    /**
     * 打开插件板块
     *
     * @param project
     * @param element
     */
    public static void openToolWindow(Project project, PsiElement element, Editor editor) {
        PsiMethod psiMethod = getPsiMethod(element);
        PsiParameterList parameterList = psiMethod.getParameterList();
        PsiJavaFile javaFile = (PsiJavaFile) psiMethod.getContainingFile();
        PsiClass psiClass = (PsiClass) psiMethod.getParent();
        //接口名称
        String interfaceName = String.format("%s.%s", javaFile.getPackageName(), psiClass.getName());
        // 入参类型
        List<String> methodTypes =
                Stream.of(parameterList.getParameters()).map(x ->
                        x.getType().getCanonicalText()
                ).collect(Collectors.toList());
        // 入参
        List<Object> initParamArray = ParamUtil.getInitParamArray(psiMethod.getParameterList());
        // 接口名称
        String methodName = psiMethod.getName();

        ToolWindow toolWindow = ToolWindowManager
                .getInstance(Objects.requireNonNull(project))
                .getToolWindow(Constants.PLUGIN_NAME);
        if (toolWindow != null) {
            // 无论当前状态为关闭/打开，进行强制打开 ToolWindow
            toolWindow.show(() -> {

            });
        }
        TabInfo selectedInfo = TabBar.getSelectionTabInfo();
        if (selectedInfo == null) {
            HintManager instance = HintManager.getInstance();
            instance.showErrorHint(editor, "Onclick Add Tab");
            return;
        }
        TabWindow component = (TabWindow) selectedInfo.getComponent();
        RegisterCenterConfig settings = RegisterCenterConfig.getInstance();
        RegisterCenterCache registerCenterCache = settings.getCache().get(0);
        DubboCacheInfo dubboCacheInfo = new DubboCacheInfo();
        String name = methodName + "#" + interfaceName;
        dubboCacheInfo.setName(name);
        dubboCacheInfo.setRegisterCenterName(registerCenterCache.getName());
        dubboCacheInfo.setParam(initParamArray);
        dubboCacheInfo.setDate(new Date());
        dubboCacheInfo.setInterfaceName(interfaceName);
        dubboCacheInfo.setMethodName(methodName);
        dubboCacheInfo.setMethodType(methodTypes);

        DubboPanel.refreshUI(component.getDubboPanel(), dubboCacheInfo);
    }
}
