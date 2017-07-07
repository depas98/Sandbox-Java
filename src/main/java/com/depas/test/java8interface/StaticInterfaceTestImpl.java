package com.test.java8interface;

public class StaticInterfaceTestImpl implements StaticInterfaceTest {

	@Override
	public String getIP() {
		String ip = "10.10.10.1";
		if (StaticInterfaceTest.isValidIP(ip)){
			return ip;
		}
		
		return null;
	}

}
