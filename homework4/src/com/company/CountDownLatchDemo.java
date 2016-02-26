package com.company;

//First, 7 threads are created. 2 are responsible for waiting for the countdown latch
//and the other 5 decrement it. I started one waiting thread before the others and 1 after
//so that the demo is (maybe?) more likely to show interleaving threads.
//I also used an 8th thread called after the latch is released to show that future
//threads are allowed through the CountDownLatch.await() method
public class CountDownLatchDemo {

    static CountDownLatch l1 = new CountDownLatch(5);

    public static void main(String[] args) {
        //two waiter threads
        Thread waiter1 = new Thread(new Test2());
        Thread waiter2 = new Thread(new Test2());

        //five counter threads
        Thread t1 = new Thread(new Test1());
        Thread t2 = new Thread(new Test1());
        Thread t3 = new Thread(new Test1());
        Thread t4 = new Thread(new Test1());
        Thread t5 = new Thread(new Test1());

        //start and joining
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

        //Third waiter thread
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

    //This task is for the counter threads to decrement the latch
    static class Test1 implements Runnable {
        public void run(){
            System.out.println(Thread.currentThread().getId() + ": countdown.");
            l1.countdown();
        }
    }

    //This task is for the waiter threads
    static class Test2 implements Runnable {
        public void run(){
            System.out.println(Thread.currentThread().getId() + ": waiting for countdown.");
            l1.await();
            System.out.println(Thread.currentThread().getId() + ": countdown finished.");
        }
    }
}
