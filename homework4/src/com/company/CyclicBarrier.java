package com.company;

/*
CyclicBarrier is described in section 5.5.4. The constructor initializes a counter. The await()
method decrements the counter and waits until the counter reaches zero at which point all waiting
threads are released, the counter is reset to its initial value, and subsequent calls to await again
block until the counter reaches 0. You do not need to implement the handling of timeouts and
interruptions described in the text nor do you need to implement the barrier action feature
described in the first line of p. 101.) Note: my Race program for assignment 1 contained a barrier
implementation but it does not fully meet the re-usability requirements for this assignment. You
may use it as a starting point if you wish.
*/

public class CyclicBarrier {
    //maxCounter is set on initialization and it the number of threads that need to touch
    //the barrier before it opens. counter keeps track of how many threads have touched the barrier.
    //Finally, I implemented secondCounter to keep track of how many threads have past the barrier.
    //When all threads are past the barrier logic, counter and secondCounter are reset.
    private final int maxCounter;
    private volatile int counter;
    private volatile int secondCounter;

    //basic constructor
    public CyclicBarrier(int counter) {
        this.maxCounter = counter;
        this.counter = counter;
    }

    //This is essentially the entire class. This method causes all threads that
    //call it to wait until this.counter = 0. Then is allows each of those threads to procede
    //incrementing this.secondCounter as they go. When the last thread increments this.secondCounter
    //then the values of this.counter and this.secondCounter are reset.
    public synchronized void await()
    {
        //First threads decrement the counter then wait for it to reach 0.
        counter --;
        while(counter > 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //The last thread to hit the barrier will then call this (so will all following threads, unnecesarrily) so
        //that the other threads can proceed
        notifyAll();

        //Before I had these lines, I just reset counter here and that causes the first thread to get here
        //to lock the other ones inside of the while(counter > 0) loop.
        //The use of second counter solves this problem by only reseting the counter once
        //all threads have reached this point
        secondCounter++;
        if (secondCounter == maxCounter){
            counter = maxCounter;
            secondCounter = 0;
        }

        return;
    }
}
