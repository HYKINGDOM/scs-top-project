package com.scs.top.project.common.constant;

/**
 * 用户常量信息
 *
 * @author admin
 */
public class UserConstants {


    /**
     * 用户地域的全部数据权限
     */
    public static final String USER_REGION_FULL_DATASOURCE = "All";

    /**
     * 全部数据权限-管理员
     */
    public static final String ROLE_DATASOURCE_ADMIN = "1";

    /**
     * 自定义数据权限
     */
    public static final String ROLE_DATASOURCE_COMMON = "2";




    /**
     * 正常状态
     */
    public static final String NORMAL = "0";

    /**
     * 异常状态
     */
    public static final String EXCEPTION = "1";

    /**
     * 部门正常状态
     */
    public static final Integer DEPT_NORMAL = 1;

    /**
     * 用户名长度限制
     */
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 20;

    /**
     * 登录名称是否唯一的返回结果码
     */
    public final static int USER_NAME_UNIQUE = 0;
    public final static int USER_NAME_NOT_UNIQUE = 1;

    /**
     * 用户状态 1--正常  0--关闭
     */
    public final static int USER_STATUS_IS_ON = 1;
    public final static int USER_STATUS_IS_OFF = 0;

    /**
     * 部门名称是否唯一的返回结果码
     */
    public final static String DEPT_NAME_UNIQUE = "0";
    public final static String DEPT_NAME_NOT_UNIQUE = "1";

    /**
     * 角色名称是否唯一的返回结果码
     */

    public final static String ROLE_NAME_UNIQUE= "0";
    public final static String ROLE_NAME_NOT_UNIQUE = "1";

    /**
     * 角色权限是否唯一的返回结果码
     */
    public final static String ROLE_KEY_UNIQUE = "0";
    public final static String ROLE_KEY_NOT_UNIQUE = "1";

    /**
     * 菜单名称是否唯一的返回结果码
     */
    public final static String MENU_NAME_UNIQUE = "0";
    public final static String MENU_NAME_NOT_UNIQUE = "1";

    /**
     * 字典类型是否唯一的返回结果码
     */
    public final static String DICT_TYPE_UNIQUE = "0";
    public final static String DICT_TYPE_NOT_UNIQUE = "1";

    /**
     * 参数键名是否唯一的返回结果码
     */
    public final static String CONFIG_KEY_UNIQUE = "0";
    public final static String CONFIG_KEY_NOT_UNIQUE = "1";

    /**
     * 密码长度限制
     */
    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 20;

}
