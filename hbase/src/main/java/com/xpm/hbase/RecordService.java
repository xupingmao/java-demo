package com.xpm.hbase;

import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import sun.tools.jconsole.Tab;

import javax.print.attribute.standard.MediaSize;
import java.io.IOException;

/**
 * Created by xupingmao on 2017/9/25.
 */
public class RecordService {

    private final Table table;
    private final static byte[] CF = "cf".getBytes();
    private final static byte[] NAME = "NAME".getBytes();
    private final static byte[] AGE = "AGE".getBytes();
    private final static byte[] VERSION = "VERSION".getBytes();
    private final static byte[] ADDRESS = "ADDRESS".getBytes();

    public RecordService(Table table) {
        this.table = table;
    }

    public Record findByRowKey(String rowKey) throws IOException {
        Get get = new Get(rowKey.getBytes());
        Result result = table.get(get);
        if (result != null) {
            return resultToRecord(result);
        }
        return null;
    }

    private Record resultToRecord(Result result) {
        if (result.getRow() == null) {
            return null;
        }
        String name = BitUtils.toString(result.getValue(CF, NAME));
        Integer age  = BitUtils.toInt(result.getValue(CF, AGE));
        String address = BitUtils.toString(result.getValue(CF, ADDRESS));
        Long version = BitUtils.toLong(result.getValue(CF, VERSION));
        Record record = new Record();
        record.setName(name);
        record.setAge(age);
        record.setAddress(address);
        record.setVersion(version);
        return record;
    }

    public boolean merge(Record record) throws IOException {
        Record check = findByRowKey(record.getName());
        Put put = new Put(record.getName().getBytes());
        put.addColumn(CF, VERSION, Bytes.toBytes(record.getVersion()+1));
        put.addColumn(CF, NAME, Bytes.toBytes(record.getName()));
        put.addColumn(CF, AGE, Bytes.toBytes(record.getAge()));
        put.addColumn(CF, ADDRESS, Bytes.toBytes(record.getAddress()));

        boolean updated = false;
        if (check == null) {
            System.out.println("Insert Row");
            updated = table.checkAndPut(record.getName().getBytes(), CF, VERSION, null, put);
        } else {
            System.out.println("Update Row");
            updated = table.checkAndPut(record.getName().getBytes(), CF, VERSION,
                    Bytes.toBytes(record.getVersion()), put);
        }
        return updated;
    }
}
