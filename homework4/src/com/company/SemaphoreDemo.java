package com.company;

//This class runs two tests on the semaphore class.
//The first one tests a binary semaphore with 5 threads each acquiring and releasing permits
//and the second one uses a semaphore with a resource pool of 100 with 5 threads grabbing 40
//permits each, this tests wait for .2 seconds before releasing to show more waiting of the
//semaphore structure.
public class SemaphoreDemo {

    //Semaphores
    static Semaphore s1 = new Semaphore(1);
    static Semaphore s2 = new Semaphore(100);

    //Very simple test code. Make 5 threads, run 5 threads, wait for them. Repeat.
    //The only difference is in the task passed to them.
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

    //This tasks grabs a single permit and then releases it
    static class Test1 implements Runnable {
        public void run(){
            System.out.println(Thread.currentThread().getId() + ": trying to acquire a permit.");
            s1.acquire();
            System.out.println(Thread.currentThread().getId() + ": acquired a permit, releasing now.");
            s1.release();
            System.out.println(Thread.currentThread().getId() + ": released a permit.");

        }
    }

    //This tasks grabs 40 permits and then releases them, pausing for .2 seconds before the release.
    static class Test2 implements Runnable {
        public void run(){
            System.out.println(Thread.currentThread().getId() + ": trying to acquire 40 permits.");
            s2.acquire(40);
            System.out.println(Thread.currentThread().getId() + ": acquired 40 permits, releasing in sleep(200).");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            s2.release(40);
            System.out.println(Thread.currentThread().getId() + ": released 40 permits.");

        }
    }
}
