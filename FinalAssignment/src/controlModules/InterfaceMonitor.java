/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlModules;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author mario
 */
public interface InterfaceMonitor extends Remote{
    public int checkAge() throws RemoteException;
}
