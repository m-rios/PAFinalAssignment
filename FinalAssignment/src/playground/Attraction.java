/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playground;

import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author mario
 */
public abstract class Attraction {

    protected ArrayList<Child> playingQueue;
    protected ArrayList<Child> waitingQueue;
    protected JTextArea outputPlay;
    protected JTextArea outputWait;
    protected Gateway gate;

    public Attraction(JTextArea outputPlay, JTextArea outputWait, Gateway gate) {
        waitingQueue = new ArrayList<>();
        this.outputPlay = outputPlay;
        this.outputWait = outputWait;
        this.gate = gate;
    }
    
    public synchronized String[] getSnapShot(){
        String content="";
        String[] value = new String[2];
        for (int i = 0; i < playingQueue.size(); i++) {
            content+=playingQueue.get(i).getIdN() + "\n";
        }
        value[0] = content;
        content="";
        for (int i = 0; i < waitingQueue.size(); i++) {
            content+=waitingQueue.get(i).getIdN() + "\n";
        }
        value[1] = content;
        return value;
    }

    public abstract void use(Child child);

    public synchronized boolean checkIsPlaying(Child child) {
        return playingQueue.contains(child);
    }

    public synchronized boolean checkIsWaiting(Child child) {
        return waitingQueue.contains(child);
    }

    public void updatePlayView() {
        String value = "";
        for (int i = 0; i < playingQueue.size(); i++) {
            value += playingQueue.get(i).getIdN()+ "\n";
        }
        this.outputPlay.setText(value);
    }

    public void updateWaitView() {
        String value = "";
        for (int i = 0; i < waitingQueue.size(); i++) {
            value += waitingQueue.get(i).getIdN()+ "\n";
        }
        this.outputWait.setText(value);
    }
}
