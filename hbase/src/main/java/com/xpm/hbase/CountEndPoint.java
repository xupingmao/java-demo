package com.xpm.hbase;

import org.apache.hadoop.hbase.Coprocessor;
import org.apache.hadoop.hbase.CoprocessorEnvironment;

import java.io.IOException;

/**
 * Created by xupingmao on 2017/9/25.
 */
public class CountEndPoint implements Coprocessor{
    @Override
    public void start(CoprocessorEnvironment env) throws IOException {
        
    }

    @Override
    public void stop(CoprocessorEnvironment env) throws IOException {

    }
}
