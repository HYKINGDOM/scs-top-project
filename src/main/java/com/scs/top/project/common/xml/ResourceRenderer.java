package com.scs.top.project.common.xml;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Administrator
 */
public class ResourceRenderer {
    public static InputStream resourceLoader(String fileFullPath) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        return resourceLoader.getResource(fileFullPath).getInputStream();
    }
}
