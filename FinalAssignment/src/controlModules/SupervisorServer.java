/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlModules;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
    DataOutputStream output;
    DataInputStream input;
    Playground playground;
    
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
    
    private void close(){
        playground.gate.close();
    }
    
    private String getSnapshot(){
        String value="swing\n";
        value+=playground.swing.getSnapShot();
        value+="carousel\n";
        value+=playground.carousel.getSnapShot();
        value+="slide\n";
        value+=playground.slide.getSnapShot();
        return value;
    }
    
    @Override
    public void run(){
        while(true){
            try {
                connection = server.accept();
                input = new DataInputStream(connection.getInputStream());
                output = new DataOutputStream(connection.getOutputStream());
                switch (input.readUTF()){
                    case "close":
                        close();
                        output.writeUTF(getSnapshot());
                        break;
                    case "refresh":
                        output.writeUTF(getSnapshot());
                        break;
                }
                //listen to terminate order
                sleep(100);
                
            } catch (IOException ex) {
                Logger.getLogger(SupervisorServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex2) {return;}
            
        }
    }
    
}
