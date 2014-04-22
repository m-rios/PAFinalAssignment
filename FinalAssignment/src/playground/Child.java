/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playground;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public Child(int id, Carousel carousel, Slide slide, Swing swing) {
        this.id = id;
        this.age = 3 + (id % 10);
        this.carousel = carousel;
        this.slide = slide;
        this.swing = swing;
    }
    
    public int getIdN() {
        return this.id;
    }

    @Override
    public void run() {

        Random gen = new Random();
        
        while (true) {
            try {
                //decide for some time
                sleep((long)(200 + 1800*Math.random()));
            } catch (InterruptedException ex) {
                Logger.getLogger(Child.class.getName()).log(Level.SEVERE, null, ex);
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
