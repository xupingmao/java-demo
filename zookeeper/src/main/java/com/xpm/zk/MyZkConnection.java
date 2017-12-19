package com.xpm.zk;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by xupingmao on 2017/7/25.
 */
public class MyZkConnection {

        // declare zookeeper instance to access ZooKeeper ensemble
        private MyZkClient zoo;
        final CountDownLatch connectedSignal = new CountDownLatch(1);

        // Method to connect zookeeper ensemble.
        public MyZkClient connect(String host) throws IOException,InterruptedException {

//            zoo = new MyZkClient(host,5000,new Watcher() {
//
//                public void process(WatchedEvent we) {
//
//                    if (we.getState() == KeeperState.SyncConnected) {
//                        connectedSignal.countDown();
//                    }
//                }
//            });

            connectedSignal.await();
            return zoo;
        }

        // Method to disconnect from zookeeper server
        public void close() throws InterruptedException {
            zoo.close();
        }


}
