package com.itmuch.nio;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class TestCounrtMap {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String args[]) throws Exception{
        List<Integer> list = new ArrayList<>(1000);
        MyThread myThread0 = new MyThread(list, 1);
        MyThread myThread1 = new MyThread(list,2);
        myThread0.start();
        myThread1.start();
        Thread.sleep(2000);
        System.out.println(list.toString());

        System.out.println(list.size());
        System.exit(0);
    }

    private  static class MyThread extends Thread{
        private List<Integer> list;

        private Integer val;

        public MyThread(List<Integer> list, Integer val) {
            this.list = list;
            this.val = val;
        }

        @Override
        public void run() {
            while(true){
                lock.lock();
                if(list.size() >= 1000) break;
                list.add(val);
                lock.unlock();
            }
        }
    }
}
