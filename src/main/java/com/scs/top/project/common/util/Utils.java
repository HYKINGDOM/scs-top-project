package com.scs.top.project.common.util;

import com.google.common.collect.Lists;
import com.isoftstone.pmit.common.xml.ResourceRenderer;
import com.isoftstone.pmit.system.user.entity.User;
import com.isoftstone.pmit.system.user.entity.UserRoleDeptRegion;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author MeasurePlatform
 * 公共方法类
 */
public class Utils {

    public static final Logger logger = LoggerFactory.getLogger(Utils.class);
    /**
     * 解析字符串公式
     *
     * @param formula
     * @return
     */
    public static String analysisFormula(String formula) {
        String result = "";
        DecimalFormat df = new DecimalFormat("#.00");
        ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
        try {
            result = df.format(jse.eval(formula));
        } catch (Exception e) {
            return "";
        }
        return "∞".equals(result) ? "" : result;
    }

    /**
     * 校验 String，Map，List，Set
     * String == ""、null|| Map = null、size()==0 " || List = null、size()==0 || Set = null、size()==0 return true
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object... obj) {
        List<Boolean> result = new ArrayList<>();
        for (Object o : obj) {
            boolean flag = false;
            Object param = o;
            if (null == param) {
                flag = true;
            } else if (param instanceof String) {
                flag = "".equals((String) param);
            } else if (param instanceof List) {
                flag = ((List) param).size() == 0;
            } else if (param instanceof Map) {
                flag = ((Map) param).size() == 0;
            } else if (param instanceof Set) {
                flag = ((Set) param).size() == 0;
            } else if (param.getClass().isArray()) {
                flag = Array.getLength(param) == 0;
            }
            result.add(flag);
        }
        return result.contains(true);
    }


    /**
     * 校验String类型可以转为float
     *
     * @param value
     * @return
     */
    public static boolean parseFloat(String value) {
        try {
            Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    /**
     * 拼接get方法
     *
     * @param fieldName
     * @return String
     */
    public static String parGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_') {
            startIndex = 1;
        }
        return "get"
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }

    /**
     * 拼接 set方法
     *
     * @param fieldName
     * @return String
     */
    public static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_') {
            startIndex = 1;
        }
        return "set"
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }


    /**
     * 校验value值是不是数字
     *
     * @param value
     * @return
     */
    public static boolean isNumber(Object value) {
        try {
            Float.valueOf(value.toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * java 处理 小数点后保留两位有效数字
     *
     * @param b double类型
     * @return double 类型
     */
    public static double doubleFormat(double b) {
        DecimalFormat df = new DecimalFormat("0.00");
        return Double.parseDouble(df.format(b));
    }

    /**
     * java 处理 小数点后保留两位有效数字
     *
     * @param str String 类型
     * @return String 类型
     */
    public static String getStringFormat(String str) {
        if (null != str && (!"".equals(str))) {
            double d = Double.parseDouble(str);
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(d);
        } else {
            return str;
        }

    }


    /**
     * List<Map<String, Object>> 去重
     *
     * @param oldParam 需要去重的List
     * @param str      按照该字段去除重复
     * @return 去重过的List
     */
    public static List<Map<String, String>> deleteListRepeat(List<Map<String, String>> oldParam, String str) {

        List<Map<String, String>> newList = new ArrayList<>();
        for (Map<String, String> oldMap : oldParam) {
            if (newList.size() > 0) {
                boolean isContain = false;
                for (Map<String, String> newMap : newList) {
                    if (newMap.get(str).equals(oldMap.get(str))) {
                        for (String key : oldMap.keySet()) {
                            newMap.put(key, oldMap.get(key));
                        }
                        isContain = true;
                        break;
                    }
                }
                if (!isContain) {
                    newList.add(oldMap);
                }
            } else {
                newList.add(oldMap);
            }
        }
        return newList;
    }

    /**
     * 判断字符串是否为数字 9.26
     * 字符串为空时,返回false
     * 数字：ASCII 码 (0:48 -- 9：57)
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (null != str && (!"".equals(str))) {
            for (int i = str.length(); --i >= 0; ) {
                int chr = str.charAt(i);
                if (chr < 48 || chr > 57) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }

    }

    public static int getDutyDays(String strStartDate, String strEndDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        try {
            startDate = df.parse(strStartDate);
            endDate = df.parse(strEndDate);
        } catch (ParseException e) {
            System.out.println("非法的日期格式,无法进行转换");
            e.printStackTrace();
        }
        int result = 0;
        assert startDate != null;
        assert endDate != null;
        while (startDate.compareTo(endDate) <= 0) {
            if (calendar.get(Calendar.DAY_OF_MONTH) != 6 && calendar.get(Calendar.DAY_OF_MONTH) != 0) {
                result++;
            }
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }
        // 此处 -1 不包含结束日期的那一天
        return result - 1;
    }


    /**
     * String字符串去重
     * @param splitStr
     * @return
     */
    public static String stringBuliderAppend(String splitStr){
        // 正则“,”切割字符串
        String[] splitArrays = splitStr.split(",");

        List<String> list = Arrays.asList(splitArrays);
        Collections.sort(list);

        // 去重
        List<String> resultList = new ArrayList<>();
        // 遍历
        for (String string : list) {
            if (resultList.isEmpty() || !resultList.contains(string)) {
                // 结果集合为空或不含有String 添加
                resultList.add(string);
            }
        }

        //list 转 字符串
        StringBuilder sbBuilder = new StringBuilder();
        for (String string : resultList) {
            sbBuilder.append(string).append(",");
        }
        //去除字符串最后的一个 “，”
        return sbBuilder.toString().substring(0, sbBuilder.length()-1);
    }
    /**
     * 设置Excel导出的表头
     * @param
     * @return
     */
    public static int getFristHead(XSSFSheet sheet, XSSFCellStyle style, String[] headFirstPart,List<String> resultyw, XSSFRow row1, int fristHead, int headLength, int i ) {
            if(i==resultyw.size()+headLength-1){
                sheet.addMergedRegion(new CellRangeAddress(0, 0, headLength, i));
                XSSFCell yw = row1.createCell(headLength);
                yw.setCellValue(headFirstPart[fristHead]);
                yw.setCellStyle(style);
                fristHead++;
            }
        return fristHead;
    }
    /**
     * 设置抄送人
     *
     * */
    public static List<String>  getccListByresultMap(Map<String,Object> resultMap){
        String ccliststr = "";
        List<String> ccList = new ArrayList<>();
        if(null != resultMap.get("cclist")){
            ccliststr = resultMap.get("cclist").toString();
        }
        if(!"".equals(ccliststr) && ccliststr.contains(",")){
            String [] cclists = ccliststr.split(",");
            if( cclists.length>0){
                for (int i = 0; i <cclists.length ; i++) {
                    ccList.add(cclists[i]+"@isoftstone.com");
                }
            }
        }else if(!"".equals(ccliststr) && !ccliststr.contains(",")){
            ccList.add(ccliststr +"@isoftstone.com");
        }

        return ccList;
    }

    /**
     * 下载resource下面的模板
     * @param response 响应
     * @param filename 下载后的文件名
     * @param path 文件路径
     */
    public static void downLoadTemplate(HttpServletResponse response, String filename, String path) {
        InputStream inputStream = null;
        ServletOutputStream servletOutputStream = null;
        try {
            inputStream = ResourceRenderer.resourceLoader(path);
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.addHeader("charset", "utf-8");
            response.addHeader("Pragma", "no-cache");
            String encodeName = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodeName + "\"; filename*=utf-8''" + encodeName);

            servletOutputStream = response.getOutputStream();
            IOUtils.copy(inputStream, servletOutputStream);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("==========downLoadSowTemp error===========" + e.getMessage());
        } finally {
            try {
                if (servletOutputStream != null) {
                    servletOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                // 召唤jvm的垃圾回收器
                System.gc();
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("==========downLoadSowTemp error===========" + e.getMessage());
            }
        }
    }

    /**
     * 转换数据格式,将List<Map>,转换成Map<String,List<Map>>
     * @param mapList
     * @param keyMap
     * @return
     */
    public static Map<Object,List<Map<String,Object>>> listSwitchMapData(List<Map<String, Object>> mapList,String keyMap){
        Map<Object, List<Map<String, Object>>> mapWall = mapList.stream().collect(Collectors.toMap(key -> key.get(keyMap),
                Lists::newArrayList,
                (List<Map<String, Object>> newValueList, List<Map<String, Object>> oldValueList) -> {
                    oldValueList.addAll(newValueList);
                    return oldValueList;
                }));
        return mapWall;
    }

    /**
     * 处理数据成树状结构
     * @param list
     * @param pid
     * @return
     */
    public static List<Map<String,Object>> pushManyGroup(List<Map<String,Object>> list,String pid){
        List<Map<String,Object>> arr = new ArrayList<Map<String,Object>>();
        for (Map<String,Object> map : list) {
            if(pid.equals(map.get("parentId") + "")){
                map.put("children",(pushManyGroup(list, (String)map.get("deptNum"))));
                arr.add(map);
            }
        }
        return arr;
    }

    /**
     * 获取当前登录人员的地域信息
     * @param user 当前user
     * @param roleId 当前角色ID
     * @return 逗号隔开的地域ID
     */
    public static String getCurrentRegions(User user, Integer roleId) {
        String regions = "";
        List<UserRoleDeptRegion> userRoleDeptRegionList = user
                .getUserRoleDeptRegionList();
        for (UserRoleDeptRegion  urdr: userRoleDeptRegionList) {
            if(roleId.equals(urdr.getRoleId())){
                regions = urdr.getRegionId();
                break;
            }
        }
        return regions;
    }
}
