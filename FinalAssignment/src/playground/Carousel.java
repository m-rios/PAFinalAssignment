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

    final CyclicBarrier barrier;

    public Carousel(JTextArea outputPlay,JTextArea outputWait) {
        super(outputPlay,outputWait);
        super.playingQueue = new ArrayList<>(5);
        barrier = new CyclicBarrier(5);
    }

    @Override
    public void use(Child child) {
        enter(child);
        try {
            sleep((long) 5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Carousel.class.getName()).log(Level.SEVERE, null, ex);
        }
        leave(child);
    }

    private synchronized void enter(Child child) {
        super.waitingQueue.add(child);
        updateWaitView();
        //wait while full or not first in waiting queue
        while (playingQueue.size() == 5 && waitingQueue.indexOf(child) != 0) {
            try {
                wait();
            } catch (InterruptedException ex) {
            }
        }
        super.waitingQueue.remove(child);
        updateWaitView();
        super.playingQueue.add(child);
        updatePlayView();
        //wait until all 5 sits are occupied.
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException ex) {
            Logger.getLogger(Carousel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private synchronized void leave(Child child) {
        playingQueue.remove(child);
        updatePlayView();
        if (playingQueue.isEmpty()) {
            notifyAll();
        }
    }
}
