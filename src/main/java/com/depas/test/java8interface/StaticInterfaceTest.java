package com.test.java8interface;

import java.net.InetAddress;
import java.net.UnknownHostException;

public interface StaticInterfaceTest {
	public String getIP();
	
	static boolean isValidIP(String ip){
		if (ip == null) {
			return false;
		}
		
		try {
			InetAddress.getByName(ip);
    	} catch (UnknownHostException e) {
    		return false;
    	}		
		return true;		
	}
}
