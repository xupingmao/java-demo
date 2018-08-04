package org.javademo.jdk;

import java.util.Properties;

/**
 * Description here
 *
 * @author pingmao.xpm
 * @version 1.0
 * @since 2018/5/9
 */
public class VmArgumentsAndProgramArgumentsMain {

    public static void main(String[] args) {
        // Input arguments java VmArgumentsAndProgramArgumentsMain -demo.name test -Ddemo.name test1
        // -Ddemo.name不能设置VM Arguments,必须通过环境变量设置
        System.out.println("-- Program Arguments --");
        for (String arg: args) {
            System.out.println(arg);
        }

        System.out.println("-- VM Arguments --");
        String test = System.getProperty("demo.name");
        System.out.println("demo.name=" + test);

        System.out.println("\n\n");
        Properties properties = System.getProperties();
        properties.list(System.out);
    }
}
