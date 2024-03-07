package Main;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Philosopher extends Thread{
    private String name;
    private int leftFork;
    private int rightFork;
    private int eatCounter = 0;
    private Random random = new Random();
    private CountDownLatch cdl;
    private Waiter waiter;

    public Philosopher(String name, Waiter waiter, int leftFork, int rightFork, CountDownLatch cdl){
        this.name = name;
        this.waiter = waiter;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.cdl = cdl;

    }

    @Override
    public void run() {
        while (eatCounter < 3){
            try {
                think();
                eat();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        System.out.println(name + " сыт!");
        cdl.countDown();
    }

    private void think() throws InterruptedException{
        Thread.sleep(random.nextLong(500, 2000));
    }

    private void eat() throws InterruptedException{
        if(waiter.tryToGetForks(leftFork, rightFork)){
            System.out.println(name + " кушает, с помощью вилок: " + leftFork + ", " + rightFork);
            sleep(random.nextLong(3000, 6000));
            waiter.returnForks(leftFork, rightFork);
            System.out.println(name + " мыслит...");
            eatCounter++;
        }
    }
}
