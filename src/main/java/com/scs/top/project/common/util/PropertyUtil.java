package com.scs.top.project.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 导入application.yml，配置属性
 * @author admin
 */
public class PropertyUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

    private static Properties props;

    static {
        loadProps();
    }

    synchronized static private void loadProps() {
        logger.info("start to load application.yml properties.......");
        props = new Properties();
        InputStream in = null;
        try {
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("application.yml");
            props.load(in);
            String name = getProperty("active");
            if (("dev").equals(name)) {
                in = PropertyUtil.class.getClassLoader().getResourceAsStream("application-dev.yml");
            } else if (("prod").equals(name)) {
                in = PropertyUtil.class.getClassLoader().getResourceAsStream("application-prod.yml");
            } else if (("test").equals(name)) {
                in = PropertyUtil.class.getClassLoader().getResourceAsStream("application-test.yml");
            }
            props.load(in);
            logger.info("使用环境配置："+name);
        } catch (FileNotFoundException e) {
            logger.error("properties not found!");
        } catch (IOException e) {
            logger.error("IOException");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("properties close Exception!");
            }
        }
        //logger.info(String.valueOf(props));
        logger.info("load properties over...........");
    }

    public static String getProperty(String key) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }

}
