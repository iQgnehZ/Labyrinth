package com.juvenxu.mvnbook.maze;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;


/**
 * 
 * <p>
 * ClassName LoadFile
 * </p>
 * <p>
 * Description 将Java项目打包，发布到Web项目下，依然可以正确的加载资源
 * </p>
 * 
 * @author TKPad wangx89@126.com
 *         <p>
 *         Date 2015年4月22日 下午9:06:07
 *         </p>
 * @version V1.0.0
 *
 */
public class LoadFile {
    public void read(String f) throws IOException {
        URL resource = this.getClass().getResource(f);
        loadJar(resource.getFile());
    }
    /**
     * 
     * <p>
     * Title: loadJar
     * </p>
     * <p>
     * Description: ③ 封装了一个加载Jar包的方法，使用URL根据jar包加载规范获取输入流，并据此输入流做进一步的操作
     * </p>
     * 
     * @param filePath
     * @throws IOException
     *
     */
    public void loadJar(String filePath) throws IOException {
        // 注意在WINDOWS环境一定要使用正斜杠
        filePath = "jar:" + filePath;
        URL url = new URL(filePath);
        JarURLConnection openStream = (JarURLConnection) url.openConnection();
        InputStream inputStream = openStream.getInputStream();
        // 使用IO工具类将输入流输出到控制台
        IOUtils.copy(inputStream, System.out);
        // 使用IO工具类从输入流中按行读取并存入List集合
        List<String> contents = IOUtils.readLines(inputStream);
        System.out.println(contents);
    }
}
