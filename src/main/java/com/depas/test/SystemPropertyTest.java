package com.depas.test;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Enumeration;
import java.util.Properties;

public class SystemPropertyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Get all system properties
		Properties props = System.getProperties();

		// Enumerate all system properties
		Enumeration enums = props.propertyNames();
		for (; enums.hasMoreElements(); ) {
		    // Get property name
		    String propName = (String)enums.nextElement();

		    // Get property value
		    String propValue = (String)props.get(propName);
		    
//		    System.out.println("Property Name: " + propName + " Value: " + propValue);
		}
		
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] allFonts = e.getAllFonts();
		for (int i = 0; i < allFonts.length; i++) {
			Font font = allFonts[i];
			System.out.println("Font family: " + font.getFamily() + " Font Name: " + font.getPSName());			
		}
		
	}

}
