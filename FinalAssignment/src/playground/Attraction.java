/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playground;

import java.util.ArrayList;

/**
 *
 * @author mario
 */
public abstract class Attraction {

    protected ArrayList<Child> playingQueue;
    protected ArrayList<Child> waitingQueue;

    public Attraction() {
        waitingQueue = new ArrayList<>();
    }
    
    public abstract void use (Child child);
    
    public synchronized boolean checkIsPlaying(Child child) {
        return playingQueue.contains(child);
    }
    
    public synchronized boolean checkIsWaiting(Child child) {
        return waitingQueue.contains(child);
    }
}
