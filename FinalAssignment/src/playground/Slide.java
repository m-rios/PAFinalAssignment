/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playground;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class Slide extends Attraction {

    private Semaphore semaphore;

    public Slide() {
        super();
        super.playingQueue = new ArrayList<>(1);
        semaphore = new Semaphore(1);
    }

    @Override
    public void use(Child child) {
        enter(child);        
        climb();
        drop();
        leave(child);
    }

    private void climb() {
        try {
            sleep((long) 1200);
        } catch (InterruptedException ex) {
            System.out.println("got kicked out0");
        }
    }

    private void enter(Child child) {
        synchronized (this) {
            waitingQueue.add(child);
        }
        try {
            semaphore.acquire();
        } catch (InterruptedException ex) {
            System.out.println("Child:" + child.getIdN() + "didn't slide");
            return;
        }
        synchronized (this) {
            waitingQueue.remove(child);
            playingQueue.add(child);
        }
    }

    private void leave(Child child) {
        synchronized (this) {
            playingQueue.remove(child);
        }
        semaphore.release();
    }

    private void drop() {
        try {
            sleep((long) 500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Slide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
