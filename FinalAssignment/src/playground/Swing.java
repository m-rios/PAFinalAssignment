/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package playground;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class Swing extends Attraction{

    public Swing() {
        super.playingQueue = new ArrayList<>(3);
    }

    @Override
    public void use (Child child) {
        enter(child);
        //play
        try {
            sleep((long)(200 + 1800 * Math.random()));
        } catch (InterruptedException ex) {
            Logger.getLogger(Swing.class.getName()).log(Level.SEVERE, null, ex);
        }
        leave(child);       
    }
    
    private synchronized void enter(Child child) {
        //swing full -> wait
        while (super.playingQueue.size() == 3) {
            if (!super.waitingQueue.contains(child)) 
                super.waitingQueue.add(child);
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Swing.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //free slot -> use swing
        super.playingQueue.add(child);
    }
    
    private synchronized void leave (Child child) {
        playingQueue.remove(child);
        notifyAll();
    }
    
}
