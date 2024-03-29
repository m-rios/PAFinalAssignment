/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playground;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author mario
 */
public class Child extends Thread {

    private Carousel carousel;
    private Slide slide;
    private Swing swing;
    private int id;
    private int age;
    private Gateway gate;
    private ArrayList<Child> deciding;
    private JTextArea output;

    public Child(int id, Carousel carousel, Slide slide, Swing swing, ArrayList<Child> deciding, Gateway gate,JTextArea output) {
        this.id = id;
        this.age = 3 + (id % 10);
        this.carousel = carousel;
        this.slide = slide;
        this.swing = swing;
        this.gate = gate;
        this.deciding = deciding;        
        this.output = output;
        start();
    }
    
    public int getIdN() {
        return this.id;
    }
    
    public String getStatus() {
        if (carousel.checkIsPlaying(this)) {
            return "Playing on the carousel";
        } else if (carousel.checkIsWaiting(this)) {
            return "Waiting to mount on the carousel";
        } else if (slide.checkIsPlaying(this)) {
            return "Playing on the slide";
        } else if (slide.checkIsWaiting(this)) {
            return "Waiting to mount on the slide";
        } else if (swing.checkIsPlaying(this)) {
            return "Playing on the swing";
        } else if (swing.checkIsWaiting(this)) {
            return "Waiting to mount on the swing";
        }
        return "deciding";
    }
    
    public int getAge(){
        return this.age;
    }
    
    private void updateOuput(){
        String value = "";
        for (int i = 0; i < deciding.size(); i++) {
            value += deciding.get(i).getIdN()+ "\n";
        }
        this.output.setText(value);
    }

    @Override
    public void run() {

        Random gen = new Random();
        
        while (true) {
            gate.look();
            synchronized (deciding) {
                deciding.add(this);
                updateOuput();
            }
            try {
                //decide for some time
                sleep((long)(200 + 1800*Math.random()));
            } catch (InterruptedException ex) {
                Logger.getLogger(Child.class.getName()).log(Level.SEVERE, null, ex);
            }
            gate.look();
            synchronized (deciding) {
                deciding.remove(this);
                updateOuput();
            }
            //randomly pick an attraction
            switch (gen.nextInt(4) % 3) {
                case 0:
                    carousel.use(this);
                    break;
                case 1:
                    slide.use(this);
                    break;
                case 2:
                    swing.use(this);
                    break;
            }
        }
    }
}
