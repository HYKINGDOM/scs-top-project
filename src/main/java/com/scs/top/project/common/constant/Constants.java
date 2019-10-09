package com.scs.top.project.common.constant;

/**
 * 通用常量信息
 * @author Administrator
 */
public class Constants {

    /**
     * 软通邮箱地址
     */
    public static final String ISOFT_EMAIL_ADDRESS = "@isoftstone.com";

    /**
     * 数据异常
     */
    public final static String DATA_EXCEPTION = "数据异常!,请检查数据!";

    /**
     * 默认字符串常量1
     */
    public final static String DEFAULT_CONSTANTS_ONE_STRING = "1";

    /**
     * 默认字符串常量0
     */
    public final static String DEFAULT_CONSTANTS_ZERO_STRING = "0";

    /**
     * 默认数字常量1
     */
    public final static Integer DEFAULT_CONSTANTS_ONE_INTEGER = 1;

    /**
     * 默认数字常量0
     */
    public final static Integer DEFAULT_CONSTANTS_ZERO_INTEGER = 0;


    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 自动去除表前缀
     */
    public static String AUTO_REOMVE_PRE = "true";

    /**
     * 当前记录起始索引
     */
    public static String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static String IS_ASC = "isAsc";
    /**
     * 区分部门开始常量
     */
    public static int DEPT_ONE = 1100;
    public static int DEPT_TWO = 1200;
    public static int DEPT_THREE = 1300;
    public static int DEPT_FOUR = 2000;
    public static int DEPT_FIVE = 3000;
    public static int DEPT_SIX = 4000;
    public static int DEPT_SEVEN = 8000;

    /**
     * flag为1表示pm信息及其所在部门
     */
    public static int PM_INFO_DEPT = 1;
    /**
     * flag为2表示QA信息及部门
     */
    public static int QA_INFO_DEPT = 2;
    /**
     * pm的role_id
     */
    public static int PM_ROLE_ID = 64;
    /**
     * QA的role_id
     */
    public static int QA_ROLE_ID = 65;
    /**
     * 定时器同步pmoit系统中的pm,qa时候的创建人
     */
    public static String CREATE_BY = "Administrator";
    /**
     * 如果没查到人员，默认返回总数为0，页数为0
     */
    public static int TOTAL_COUNT = 0;
    public static int TOTAL_PAGE = 0;
}
