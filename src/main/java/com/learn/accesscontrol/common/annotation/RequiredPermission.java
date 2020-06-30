package com.learn.accesscontrol.common.annotation;

/**
 * Created by wst on 2020/6/30.
 */
public @interface RequiredPermission {
    /**
     * 提供权限名称
     * @return
     */
    String value();
}
