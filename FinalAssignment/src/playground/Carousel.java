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

/**
 *
 * @author mario
 */
public class Carousel extends Attraction {

    final CyclicBarrier barrier;

    public Carousel() {
        super();
        super.playingQueue = new ArrayList<>(5);
        barrier = new CyclicBarrier(5,new Runnable() {

            @Override
            public void run() {
                try {
                    sleep((long)5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Carousel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @Override
    public void use(Child child) {
        enter(child);
       /* try {
            sleep((long) 5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Carousel.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        leave(child);
    }

    private synchronized void enter(Child child) {
        super.waitingQueue.add(child);
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException ex) {
            Logger.getLogger(Carousel.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.waitingQueue.remove(child);
        super.playingQueue.add(child);
    }

    private synchronized void leave(Child child) {

    }
}
