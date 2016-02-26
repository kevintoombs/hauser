package com.company;

//This class runs two tests on semaphore 
public class SemaphoreDemo {

    static Semaphore s1 = new Semaphore(1);
    static Semaphore s2 = new Semaphore(100);
    static Object acquireLock = new Object();
    static Object releaseLock = new Object();

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
        System.out.println("Test 1 over");

        t1 = new Thread(new Test2());
        t2 = new Thread(new Test2());
        t3 = new Thread(new Test2());
        t4 = new Thread(new Test2());
        t5 = new Thread(new Test2());
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
        System.out.println("Test 2over");
    }


    static class Test1 implements Runnable {
        public void run(){
            System.out.println(Thread.currentThread().getId() + ": trying to acquire a permit.");
            synchronized (acquireLock) {
                s1.acquire();
                System.out.println(Thread.currentThread().getId() + ": acquired a permit.");
            }
            synchronized (releaseLock) {
                s1.release();
                System.out.println(Thread.currentThread().getId() + ": released a permit.");
            }
        }
    }

    static class Test2 implements Runnable {
        public void run(){
            System.out.println(Thread.currentThread().getId() + ": trying to acquire 40 permits.");
            s2.acquire(40);
            System.out.println(Thread.currentThread().getId() + ": acquired 40 permits.");
            s2.release(40);
            System.out.println(Thread.currentThread().getId() + ": released 40 permits.");

        }
    }
}
