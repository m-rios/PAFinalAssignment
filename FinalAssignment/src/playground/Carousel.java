/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playground;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

/**
 *
 * @author mario
 */
public class Carousel extends Attraction {
    
    private class Awaker extends Thread{
        Carousel c;
        public Awaker(Carousel c){
            this.c = c;
            start();
        }
        @Override
        public void run(){
            while(true){
                if (c.threadsWaiting()){
                    c.awake();
                }
                try {
                    sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Carousel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    Awaker awaker;
    final CyclicBarrier barrier;
    JProgressBar indicator;

    public Carousel(JTextArea outputPlay, JTextArea outputWait, JProgressBar progressBar) {
        super(outputPlay, outputWait);
        super.playingQueue = new ArrayList<>(5);
        barrier = new CyclicBarrier(5);
        this.indicator = progressBar;
        indicator.setMaximum(10);
        awaker = new Awaker(this);
    }

    @Override
    public void use(Child child) {
        enter(child);
        try {
            barrier.await();
            sleep((long) 5000);
        } catch (InterruptedException | BrokenBarrierException ex) {
            Logger.getLogger(Carousel.class.getName()).log(Level.SEVERE, null, ex);
        }
        leave(child);
    }
    
    public synchronized void awake(){
        notifyAll();
    }

    private synchronized void enter(Child child) {
        super.waitingQueue.add(child);
        updateWaitView();
        //wait while full or not first in waiting queue
        while (playingQueue.size() == 5 || waitingQueue.indexOf(child) != 0) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        super.waitingQueue.remove(child);
        updateWaitView();
        super.playingQueue.add(child);
        updatePlayView();
    }

    private synchronized void leave(Child child) {
        playingQueue.remove(child);
        updatePlayView();
        notifyAll();
    }

    public boolean threadsWaiting() {
        synchronized (waitingQueue) {
            for (int i = 0; i < waitingQueue.size(); i++) {
                if (waitingQueue.get(i).getState() == Thread.State.WAITING) {
                    return true;
                }
            }
        }
        return false;
    }
}
