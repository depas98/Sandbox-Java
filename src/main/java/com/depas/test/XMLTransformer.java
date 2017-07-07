package com.depas.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XMLTransformer {
	private final TransformerFactory tFactory = TransformerFactory.newInstance();
	
	private StreamSource xslSource;
	private StreamSource xmlSource;
	private String output;

	public void transform (String xslFile, String xmlFile, String outputFile) {
			
		xslSource=new StreamSource(xslFile);
		//xslSource=new StreamSource(XMLTransformer.class.getResourceAsStream(xslFile));		
		xmlSource=new StreamSource(xmlFile);		
		output=outputFile;
		try {
			// $$ tried to fix directories with characters above ascii 127, didn't work
			//xmlSource=new StreamSource(new InputStreamReader(new FileInputStream(xmlFile),"UTF8"));
			transform();
		}
		catch (Exception e) {
         /*
			if (logger.isEnabledFor(Priority.ERROR)) {
	    		logger.error("Unable to perform the transformation: " + e);
           
			}
          */
         	
		}
	}
	
	public void transform (String xslFile, InputStream xmlFile, String outputFile) {
			
		//xslSource=new StreamSource(xslFile);		
		xslSource=new StreamSource(XMLTransformer.class.getResourceAsStream(xslFile));
		xmlSource=new StreamSource(xmlFile);
		output=outputFile;
		try {
			// $$ tried to fix directories with characters above ascii 127, didn't work
			//xmlSource=new StreamSource(new InputStreamReader(new FileInputStream(xmlFile),"UTF8"));			
			transform();
		}
		catch (Exception e) {
         System.out.println("Unable to perform the transformation: " + e);
		}		
	}	
	
	private void transform() throws TransformerException, TransformerConfigurationException, 
           							FileNotFoundException, IOException {
                                 
      try{                                 
		 Transformer transformer = tFactory.newTransformer(xslSource);
       // Use the Transformer to apply the associated Templates object to an XML document
       // (foo.xml) and write the output to a file (foo.out).
       // $$ tried to fix directories with characters above ascii 127, didn't work
       //transformer.transform(xmlSource, new StreamResult(new OutputStreamWriter(new FileOutputStream(output),"UTF8")));
		 
       transformer.transform(xmlSource, new StreamResult(output));          
       
       StreamResult result = new StreamResult(new StringWriter());
       
       transformer.transform(xmlSource, result);
       
       Writer out = result.getWriter();
       System.out.println(out.toString());
      }
      catch(Exception e){
         System.out.println("Unable to perform the transformation: " + e);
      }
		
	}
			
	public static void main(String[] args) {
      System.out.println("Main: Entering");
            
//      String xslIn="C:\\sql server html query plan\\html-query-plan\\qp_page.xslt";
//      String xmlIn="C:\\sql server html query plan\\plan.xml";
//      String out="C:\\sql server html query plan\\output\\qp.html";
//      
//      String xslIn="C:\\sql server html query plan\\test_sc_notation.xslt";
//      String xmlIn="C:\\sql server html query plan\\sc_notation.xml";
//      String out="C:\\sql server html query plan\\output\\sc_notation.html";

      String xslIn="C:\\sql server html query plan\\testSqlServerQP.xslt";
      String xmlIn="C:\\sql server html query plan\\plan2.xml";
      String out="C:\\sql server html query plan\\output\\plan2.html";      
      
      if (args.length > 2){
         // we have three arguments 
         // 1) xls file 2) XML file 3) Output file

         xslIn=args[0];
         xmlIn=args[1];
         out=args[2];          
      }
      
				
		XMLTransformer myTransformer = new XMLTransformer();
		try {
         System.out.println("Main: Start Transform");			
		myTransformer.transform(xslIn,xmlIn,out);		
         System.out.println("Main: End Transform");	
		}		
		catch (Exception e) {			
         System.out.println("Unable to perfrom the transformation: " + e);
		}		
	}
}