package com.company;

public class CountDownLatch {

    private volatile int counter;

    public CountDownLatch (int counter){
        this.counter = counter;
    }

    public synchronized void countdown()
    {
        counter--;
        if (counter == 0)
        {
            notifyAll();
        }
        return;
    }

    public synchronized void await()
    {
        while(counter > 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return;
    }

}
