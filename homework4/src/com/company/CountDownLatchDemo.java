package com.company;

public class CountDownLatchDemo {

    static CountDownLatch l1 = new CountDownLatch(5);

    public static void main(String[] args) {
        Thread waiter1 = new Thread(new Test2());
        Thread waiter2 = new Thread(new Test2());

        Thread t1 = new Thread(new Test1());
        Thread t2 = new Thread(new Test1());
        Thread t3 = new Thread(new Test1());
        Thread t4 = new Thread(new Test1());
        Thread t5 = new Thread(new Test1());

        waiter1.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        waiter2.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            waiter1.join();
            waiter2.join();
        } catch (InterruptedException e) {
           e.printStackTrace();
        }

        //This is to show that if countdown is already over, the await() method still works correct
        Thread waiter3 = new Thread(new Test2());
        waiter3.start();
        try {
            waiter3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("Test 1 over");
    }

    static class Test1 implements Runnable {
        public void run(){
            System.out.println(Thread.currentThread().getId() + ": countdown.");
            l1.countdown();
        }
    }
    static class Test2 implements Runnable {
        public void run(){
            System.out.println(Thread.currentThread().getId() + ": waiting for countdown.");
            l1.await();
            System.out.println(Thread.currentThread().getId() + ": countdown finished.");
        }
    }
}
