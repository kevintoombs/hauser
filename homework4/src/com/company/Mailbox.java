package com.company;

/*
CountDownLatch is described in section 5.5.1. The constructor initializes a counter to the
provided value. The countdown() method decrements the counter (by 1); the await()
method returns when the counter is no longer positive
*/

public class Mailbox {

    //string to store the message
    private volatile String message = null;

    //This method checks to see if the mailbox is empty, if not, it waits until it is.
    //Then it puts input.in into the mailbox and notifies all waiting threads so that
    //they can grab the message from the mailbox if they were waiting.
    public synchronized void put (String in) {
        while (message != null){
            try {
                System.out.println("Thread " + Thread.currentThread().getId() + " waiting to put.");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.message = in;
        System.out.println("Thread " + Thread.currentThread().getId() + " notifying other threads.");
        notifyAll();
    }

    //This method checks to see if the mailbox has a message, if not, it waits until it does.
    //Then it takes that message our of the mailbox and returns it.
    //It notifies all other threads that the mailbox is empty after emptying the mailbox
    //so that threads waiting to put(String) can.
    public synchronized String take () {
        while (message == null){
            try {
                System.out.println("Thread " + Thread.currentThread().getId() + " waiting to take.");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String s = message;
        message = null;
        System.out.println("Thread " + Thread.currentThread().getId() + " notifying other threads.");
        notifyAll();
        return s;
    }
}
