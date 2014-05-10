/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlModules;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author mario
 */
public class MonitorServer {
    
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException{
        DatagramSocket socket = new DatagramSocket(2222);
        DatagramPacket packet;
        InetAddress address = InetAddress.getByName("E1");
        int n = 0;
        byte[] buff = new byte[128];
        packet = new DatagramPacket(buff, buff.length);
        socket.receive(packet);
        System.out.println(packet.getData());
    }
    
}
