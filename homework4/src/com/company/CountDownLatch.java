package com.company;

/*
CountDownLatch is described in section 5.5.1. The constructor initializes a counter to the
provided value. The countdown() method decrements the counter (by 1); the await()
method returns when the counter is no longer positive
*/

public class CountDownLatch {

    //simple counting variable
    private volatile int counter;

    //basic constructor
    public CountDownLatch (int counter){
        this.counter = counter;
    }

    //when a thread calls countdown, this decrements counter,
    //then if the counter has reach 0, it notifies all threads waiting
    public synchronized void countdown()
    {
        counter--;
        if (counter == 0)
        {
            System.out.println("Thread " + Thread.currentThread().getId() + " notifying other threads.");
            notifyAll();
        }
        return;
    }

    //This is probably as basic as a synchronized waiting method can get.
    //When a thread calls await, it just waits until the counter is at 0.
    public synchronized void await()
    {
        while(counter > 0){
            try {
                System.out.println("Thread " + Thread.currentThread().getId() + " waiting.");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return;
    }

}
