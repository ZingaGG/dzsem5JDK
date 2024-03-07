package Main;

import java.util.concurrent.CountDownLatch;

public class Waiter extends Thread{
    private final int Philosopher_quantity = 5;
    private Fork[] forks;
    private Philosopher[] philosophers;
    private CountDownLatch cdl;

    public Waiter(){
        forks = new Fork[Philosopher_quantity];
        philosophers = new Philosopher[Philosopher_quantity];
        cdl = new CountDownLatch(Philosopher_quantity);
        startDinner();
    }

    @Override
    public void run(){
        System.out.println("Званный ужин начался!");
        try {
            startThinking();
            cdl.await();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.printf("Званный ужин подошел к концу, все сыты!");
    }

    public synchronized boolean tryToGetForks(int leftfork, int rightfork){
        if(!forks[leftfork].isFlag() && !forks[rightfork].isFlag()){
            forks[leftfork].setFlag(true);
            forks[rightfork].setFlag(true);
            return true;
        }
        return false;
    }

    public void returnForks(int leftFork, int rightFork){
        forks[leftFork].setFlag(false);
        forks[rightFork].setFlag(false);
    }

    private void startDinner(){
        for (int i = 0; i < Philosopher_quantity; i++) {
            forks[i] = new Fork();
            philosophers[i] = new Philosopher("Philosopher №" + i, this, i, (i+1) % Philosopher_quantity,cdl);
        }
    }

    private void startThinking(){
        for (Philosopher philosopher: philosophers){
            philosopher.start();
        }
    }
}
