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
import javax.swing.JTextArea;

/**
 *
 * @author mario
 */
public class Swing extends Attraction {

    public Swing(JTextArea outputPlay,JTextArea outputWait) {
        super(outputPlay,outputWait);
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
        
        super.waitingQueue.add(child);
        updateWaitView();
        //wait while full or not first in waitqueue
        while (super.playingQueue.size() == 3 || super.waitingQueue.indexOf(child) != 0) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Swing.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //free slot
        super.waitingQueue.remove(child);
        updateWaitView();
        super.playingQueue.add(child);
        updatePlayView();
    }
    
    private synchronized void leave (Child child) {
        playingQueue.remove(child);
        updatePlayView();
        notifyAll();
    }    
}
