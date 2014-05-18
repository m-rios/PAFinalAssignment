/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlModules;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import playground.Slide;

/**
 *
 * @author mario
 */
public class Monitor extends UnicastRemoteObject implements InterfaceMonitor{

    private Slide slide;
    
    public Monitor(Slide slide)throws RemoteException{
        this.slide = slide;
    }
    
    @Override
    public int checkAge() throws RemoteException {
        return slide.checkAge();
    }
    
}
