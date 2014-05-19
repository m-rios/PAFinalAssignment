/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlModules;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import playground.Slide;

/**
 *
 * @author mario
 */
public class MonitorServer extends Thread{
    Slide slide;
    public MonitorServer(Slide slide){
       this.slide = slide;
        start();
   }    
   
   @Override
   public void run(){
        try {
            Monitor monitor = new Monitor(slide);
            Registry registry = LocateRegistry.createRegistry(1099);
            Naming.rebind("//localhost/Monitor",monitor);
        } catch (RemoteException ex) {
            Logger.getLogger(MonitorServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(MonitorServer.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
}
