package com.company;

//6 threads are used, the first 3 try to take messages from the mailboxes, while the next 3
//are responsible for putting messages in the mailbox. this order was chosen just to show
//the waiting
public class MailboxDemo {

    static Mailbox m1 = new Mailbox();

    public static void main(String[] args) {
        Thread p1 = new Thread(new Test1());
        Thread p2 = new Thread(new Test1());
        Thread p3 = new Thread(new Test1());
        Thread t1 = new Thread(new Test2());
        Thread t2 = new Thread(new Test2());
        Thread t3 = new Thread(new Test2());

        t1.start();
        t2.start();
        t3.start();
        p1.start();
        p2.start();
        p3.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            p3.join();
            p2.join();
            p1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //This task puts a message in the mailbox
    static class Test1 implements Runnable {
        public void run(){
            System.out.println("Thread " + Thread.currentThread().getId() + " putting message in mailbox");
            m1.put("Thread " + Thread.currentThread().getId() + " says hi!");
        }
    }

    //This task takes a message from the mailbox
    static class Test2 implements Runnable {
        public void run(){
            System.out.println("Thread " + Thread.currentThread().getId() + " trying to grab from mailbox");
            System.out.println("Thread " + Thread.currentThread().getId() + " got \"" + m1.take() + "\" from mailbox.");
        }
    }

}
