/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playground;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import javax.swing.JTextArea;

/**
 *
 * @author mario
 */
public class Slide extends Attraction {

    private Semaphore semaphore;

    public Slide(JTextArea outputPlay,JTextArea outputWait, Gateway gate) {
        super(outputPlay,outputWait, gate);
        super.playingQueue = new ArrayList<>(1);
        semaphore = new Semaphore(1);
    }

    @Override
    public void use(Child child) {
        enter(child);   
        gate.look();
        play();
        gate.look();
        leave(child);
    }

    private void play() {
        try {
            sleep((long) 1200 + 500);
        } catch (InterruptedException ex) {
            System.out.println("got kicked out0");
        }
    }

    private void enter(Child child) {
        synchronized (this) {
            waitingQueue.add(child);
            updateWaitView();
        }
        try {
            semaphore.acquire();
        } catch (InterruptedException ex) {
            System.out.println("Child:" + child.getIdN() + "didn't slide");
            return;
        }
        synchronized (this) {
            waitingQueue.remove(child);
            updateWaitView();
            playingQueue.add(child);
            updatePlayView();
        }
    }

    private void leave(Child child) {
        synchronized (this) {
            playingQueue.remove(child);
            updatePlayView();
        }
        semaphore.release();
    }
    
    public synchronized int checkAge(){
        Child child;
        if (!playingQueue.isEmpty()) {
            child = playingQueue.get(0);
            if (child.getAge() > 7) {
                child.interrupt();
                return child.getAge();
            }
        }
        return 0;
    }

}
