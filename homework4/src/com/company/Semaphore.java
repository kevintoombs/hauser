package com.company;

/*
1. Semaphore (Semaphore.java, SemaphoreDemo.java)
Semaphore semantics are described in section 5.5.3. Implement a constructor providing the initial
number of permits, and public methods for acquire(int n) and release(int n).
Overloaded parameterless methods acquire() and release() correspond to passing 1 to the
parameter-ful versions. It is important that acquire(n) not be implemented as repeatedly
doing acquire(). Explain in your comments why not.
*/

public class Semaphore {

    private int maxPermits;
    private volatile int permits;

    public Semaphore(int permits) {
        this.maxPermits = permits;
        this.permits = permits;
    }

    public synchronized int acquire(int n){
        if (n > maxPermits){
            System.out.println("ERROR: Trying to acquire more permits than available");
            return 1;
        }
        while(n > permits){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        permits -= n;
        return 0;
    }

    public synchronized int acquire(){
        return this.acquire(1);
    }

    public synchronized int release(int n){
        if (n > (maxPermits-permits)){
            System.out.println("ERROR: Trying to release more permits than those distributed...");
            return 1;
        }
        permits += n;
        notifyAll();
        return 0;
    }

    public synchronized int release(){
        return this.release(1);
    }

}