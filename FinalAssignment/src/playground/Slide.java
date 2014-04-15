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
public class Slide extends Attraction{

    public Slide() {
        super.playingQueue = new ArrayList<>(1);
    }

    @Override
    public void use(Child child) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
