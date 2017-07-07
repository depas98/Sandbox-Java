package com.depas.test;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketTest {

	
	public static void main(String[] args) {
        try { 
            ServerSocket srvr = new ServerSocket(5501);
            Socket skt = srvr.accept();
        }
        catch (Exception e) {
             e.printStackTrace();
        }
	}
}
