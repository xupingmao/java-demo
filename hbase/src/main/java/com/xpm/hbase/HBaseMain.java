package com.xpm.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import sun.tools.jconsole.Tab;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xupingmao on 2017/9/13.
 */
public class HBaseMain {

    public static final byte[] CF = "cf".getBytes();
    static final byte[] A = "a".getBytes();
    static final byte[] B = "b".getBytes();

    public static void main(String[] args) throws Exception {
        Configuration conf = HBaseConfiguration.create();
        conf.addResource("META-INF/hbase-site.xml");

        Connection connection = ConnectionFactory.createConnection(conf);
        final Table table = connection.getTable(TableName.valueOf("test"));

        Get get = new Get("row1".getBytes());
        Result result = table.get(get);
        byte[] value = result.getValue(CF, "a".getBytes());
        System.out.println(BitUtils.toString(value));

        Put put = new Put("row-x".getBytes());
        put.addColumn(CF, "b".getBytes(), "Test".getBytes());
        table.put(put);

        ProfileUtils.timeit("put", new ProfileUtils.Procedure<Void>() {
            @Override
            public Void execute() throws Exception {
                doPut(table);
                return null;
            }
        });

        ProfileUtils.timeit("get", new ProfileUtils.Procedure<Void>() {
            @Override
            public Void execute() throws Exception {
                doGet(table);
                return null;
            }
        });

        ProfileUtils.timeit("scan", new ProfileUtils.Procedure<Void>() {
            @Override
            public Void execute() throws Exception {
                scanRand(table);
                doScanWithFilters(table);
                return null;
            }
        });

        ProfileUtils.timeit("count", new ProfileUtils.Procedure<Void>() {
            @Override
            public Void execute() throws Exception {
                doCount(table);
                doCount(conf, table);
                return null;
            }
        });
    }

    private static void doGet(Table table) throws IOException {
        Get get = new Get("row1".getBytes());
        Result result = table.get(get);

        System.out.println(Arrays.asList(result.raw()));
        System.out.println(resultToStr(result));
    }

    private static String resultToStr(Result result) {
        String row = BitUtils.toString(result.getRow());
        String a = BitUtils.toString(result.getValue(CF, A));
        String b = BitUtils.toString(result.getValue(CF, B));
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("row=");
        sb.append(row);
        sb.append(",a=");
        sb.append(a);
        sb.append(",b=");
        sb.append(b);
        sb.append('}');
        return sb.toString();
    }

    private static void doPut(Table table) throws IOException {
        for (int i = 0; i < 10; i++) {
            Put put = new Put(("rand-" + RandomUtils.randomString(5)).getBytes());
            put.addColumn(CF, "b".getBytes(), RandomUtils.randomString(10).getBytes());
                table.put(put);
        }
    }

    private static void scanRand(Table table) throws IOException {
        Scan scan = new Scan();
        scan.addFamily(CF);
        scan.setStartRow("rand-".getBytes());
        scan.setMaxResultSize(10);
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> iterator = scanner.iterator();
        int i = 0;
        while (iterator.hasNext() && ++i <= 10) {
            Result result = iterator.next();
            System.out.println(resultToStr(result));
        }
    }


    private static void doScan(Table table) throws IOException {
        Scan scan = new Scan();
        scan.addFamily(CF);
        scan.setMaxResultSize(10);
        SingleColumnValueFilter colFilter = new SingleColumnValueFilter(CF, "b".getBytes(), CompareFilter.CompareOp.EQUAL, "Hello,World".getBytes());
        scan.setFilter(colFilter);
        ResultScanner scanner = table.getScanner(scan);

        Iterator<Result> iterator = scanner.iterator();
        int i = 0;
        while (iterator.hasNext() && ++i <= 10) {
            Result result = iterator.next();
            System.out.println(resultToStr(result));
        }
    }

    private static void doScanWithFilters(Table table) throws IOException {
        // RowFilter
        // FamilyFilter
        // QualifierFilter
        // ValueFilter

        // 选择特定的row
        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("row1")));
        // 只选择特定的family
        FamilyFilter familyFilter = new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("cf")));
        // 只选择特定的列
        QualifierFilter qualifierFilter = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("col-2")));


        Scan scan = new Scan();
        scan.addFamily(CF);
//        scan.addColumn(CF, Bytes.toBytes("a"));
        SingleColumnValueFilter colFilter = new SingleColumnValueFilter(CF, "b".getBytes(), CompareFilter.CompareOp.EQUAL, "Hello,World".getBytes());
        SingleColumnValueFilter colFilter2 = new SingleColumnValueFilter(CF, "b".getBytes(), CompareFilter.CompareOp.EQUAL, "What".getBytes());

        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        filterList.addFilter(colFilter);
        filterList.addFilter(colFilter2);

        scan.setFilter(filterList);
        scan.setMaxResultSize(10); // maximum result size (bytes)
        ResultScanner scanner = table.getScanner(scan);

        Iterator<Result> iterator = scanner.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Result result = iterator.next();
            System.out.println(resultToStr(result));
        }
    }

    private static void doCount(Table table) throws IOException {
        Scan scan = new Scan();
        FilterList filterList = new FilterList(new FirstKeyOnlyFilter(), new KeyOnlyFilter());
//        scan.setFilter(filterList);

        ResultScanner scanner = table.getScanner(scan);

        long count = 0;
        Iterator<Result> iterator = scanner.iterator();
        while (iterator.hasNext()) {
            Result result = iterator.next();
//            System.out.println(resultToStr(result));
            count++;
        }
        // 不做任何优化
        // count=120183
        // run [count], time elapsed: 391 ms

        // 优化之后反而慢了。。。应该跟filter执行时间有关
        // count=120213
        // run [count], time elapsed: 435 ms
        System.out.println("count=" + count);

        // 生产环境的做法是开启 RowCounter的 Map Reduce Job
        // hbase org.apache.hadoop.hbase.mapreduce.RowCounter <tablename>
    }

    private static void doCount(Configuration configuration, Table table) throws IOException {
        // TODO 目前不工作，协处理器没有在hbase-site中注册
        // No registered coprocessor service found for name AggregateService in region test
        long rowCount = 0;
        // Increase RPC timeout, in case of a slow computation
        configuration.setLong("hbase.rpc.timeout", 600000);
        // Default is 1, set to a higher value for faster scanner.next(..)
        configuration.setLong("hbase.client.scanner.caching", 1000);

        AggregationClient aggregationClient = new AggregationClient(configuration);
        try {
            Scan scan = new Scan();
            scan.addFamily(CF);
            rowCount = aggregationClient.rowCount(table, new LongColumnInterpreter(), scan);
            System.out.println("count=" + rowCount);
        } catch (Throwable e) {
            throw new IOException(e);
        }
    }

}
