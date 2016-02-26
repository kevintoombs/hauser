package com.company;

//Simple demo. Throws 5 threads at a Cyclic Barrier requiring 5 pokes
//before it breaks.
public class CyclicBarrierDemo {

    static CyclicBarrier b1 = new CyclicBarrier(5);

    public static void main(String[] args) {
        Thread t1 = new Thread(new Test1());
        Thread t2 = new Thread(new Test1());
        Thread t3 = new Thread(new Test1());
        Thread t4 = new Thread(new Test1());
        Thread t5 = new Thread(new Test1());
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static class Test1 implements Runnable {
        public void run() {
            System.out.println(Thread.currentThread().getId() + ": hitting the barrier.");
            b1.await();
            System.out.println(Thread.currentThread().getId() + ": past the barrier.");
        }
    }
}
