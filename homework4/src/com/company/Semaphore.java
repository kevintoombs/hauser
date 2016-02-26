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

    //These two variables record the current number of resources in the resource pool
    //as well as the maximum number of resources available.
    private int maxPermits;
    private volatile int permits;

    //Basic constructor, nothing fancy here.
    //Takes in an integer and sets that number as the maximum amount of permits the
    //semaphore can distribute.
    public Semaphore(int permits) {
        this.maxPermits = permits;
        this.permits = permits;
    }

    //This method allows for a thread to attempt to grab input.n permits from the semaphore
    //If enough aren't available then it waits for other threads to run until resources are
    //available.
    //There are two reasons why acquire(n) isn't implemented as acquire() multiple times.
    //The first is trivial, acquire() is implemented as acquire(1). Stack overflow anyone?
    //The second actually has to do with concurrency. If we acquired permits one at a time,
    //then we could run into the situation where a thread wants 20 permits, gets 12, then can't
    //acquire anymore and just holds onto those 12 and waits for more to be free, instead of those
    //12 going to other thread(s) that can actually use them.
    public synchronized int acquire(int n){
        //Basic error catching. This came about because I was trying to find a bug in my code.
        //Not actually part of the implementation.
        if (n > maxPermits){
            System.out.println("ERROR: Trying to acquire more permits than ever available");
            return 1;
        }

        //Simple synchronized waiting. While there aren't enough permits available
        //the thread will wait() until there are and then it "takes" that many
        //permits from the pool
        while(n > permits){
            try {
                System.out.println("Thread " + Thread.currentThread().getId() + " waiting.");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        permits -= n;

        return 0;
    }

    //Overloaded acquire() acts as acquire(1)
    public synchronized int acquire(){
        return this.acquire(1);
    }

    //This method releases input.n permits and then notifies all waiting threads that more permits are available.
    public synchronized int release(int n){
        //Simple error catching
        if (n > (maxPermits-permits)){
            System.out.println("ERROR: Trying to release more permits than those distributed...");
            return 1;
        }

        //The following lines remove input.n permits and then notify waiting threads
        permits += n;
        System.out.println("Thread " + Thread.currentThread().getId() + " notifying other threads.");
        notifyAll();

        return 0;
    }

    //Overloaded release() acts as release(1)
    public synchronized int release(){
        return this.release(1);
    }

}