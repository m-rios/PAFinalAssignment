/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlModules;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import playground.Playground;

/**
 *
 * @author mario
 */
public class SupervisorServer extends Thread{
    
    ServerSocket server;
    Socket connection;
    ObjectOutputStream output;
    DataInputStream input;
    Playground playground;
    DataOutputStream statusOutput;
    
    public SupervisorServer(Playground playground, int socket){
        this.playground = playground;
        try {
            server = new ServerSocket(socket);
        } catch (IOException ex) {
            System.out.println("something happened, try relaunching Supervisor Server");
            return;
        }
        start();
    }
    
    private String[][] getSnapshot(){
        String[][] value = new String[3][2];
        value[0] = playground.swing.getSnapShot();
        value[1] = playground.carousel.getSnapShot();
        value[2] = playground.slide.getSnapShot();        
        return value;
    }
    
    @Override
    public void run(){
        String status = "Close";
        while(true){
            try {
                connection = server.accept();
                input = new DataInputStream(connection.getInputStream());
                output = new ObjectOutputStream(connection.getOutputStream());
                switch (input.readUTF()){
                    case "close":
                        status = playground.switchGate();                        
                        output.writeObject(getSnapshot());
                        statusOutput = new DataOutputStream(connection.getOutputStream());
                        statusOutput.writeUTF(status);
                        break;
                    case "refresh":
                        output.writeObject(getSnapshot());
                        break;
                }
                //input.close();
                //output.close();
                //connection.close();
                //listen to terminate order
                sleep(100);
                
            } catch (IOException ex) {
                Logger.getLogger(SupervisorServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex2) {return;}
            
        }
    }
    
}
