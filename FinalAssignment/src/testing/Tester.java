/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testing;

import playground.Carousel;
import playground.Child;
import playground.Slide;
import playground.Swing;

/**
 *
 * @author mario
 */
public class Tester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Executing playground tests");
        Swing swing = new Swing();
        Slide slide = new Slide();
        Carousel carousel = new Carousel();
        
        for (int i = 0; i < 10; i++) {
            new Child(i, carousel, slide, swing);
        }
    }
    
}
