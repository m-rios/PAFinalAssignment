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

    public Carousel(JTextArea outputPlay, JTextArea outputWait, Gateway gate) {
        super(outputPlay, outputWait, gate);
        super.playingQueue = new ArrayList<>(5);
        barrier = new CyclicBarrier(5);
        awaker = new Awaker(this);
    }

    @Override
    public void use(Child child) {
        enter(child);
        gate.look();
        try {
            barrier.await();
            sleep((long) 5000);
        } catch (InterruptedException | BrokenBarrierException ex) {
            Logger.getLogger(Carousel.class.getName()).log(Level.SEVERE, null, ex);
        }
        gate.look();
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