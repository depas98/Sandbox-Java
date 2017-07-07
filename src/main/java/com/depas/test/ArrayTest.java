package com.depas.test;

import java.util.Arrays;
import java.util.List;

public class ArrayTest {
	private final static String[] EXTENDED_ENCODING_SET = {"Big5","Big5_HKSCS","EUC_JP","EUC_KR","GB18030","EUC_CN","GBK","Cp838","Cp1140","Cp1141","Cp1142","Cp1143","Cp1144","Cp1145","Cp1146","Cp1147","Cp1148","Cp1149",
		"Cp037","Cp1026","Cp1047","Cp273","Cp277","Cp278","Cp280","Cp284","Cp285","Cp297","Cp420","Cp424","Cp500","Cp860","Cp861","Cp863","Cp864","Cp865","Cp868","Cp869","Cp870","Cp871",
		"Cp918","ISO2022CN","ISO2022JP","ISO2022KR","ISO8859_3","ISO8859_6","ISO8859_8","JIS_X0201","JIS_X0212-1990","SJIS","Cp1255","Cp1256","Cp1258","MS932","Big5_Solaris","EUC_JP_LINUX",
		"EUC_TW","EUC_JP_Solaris","Cp1006","Cp1025","Cp1046","Cp1097","Cp1098","Cp1112","Cp1122","Cp1123","Cp1124","Cp1381","Cp1383","Cp33722","Cp834","Cp856","Cp875","Cp921","Cp922","Cp930",
		"Cp933","Cp935","Cp937","Cp939","Cp942","Cp942C","Cp943","Cp943C","Cp948","Cp949","Cp949C","Cp950","Cp964","Cp970","ISCII91","ISO2022_CN_CNS","ISO2022_CN_GB","x-iso-8859-11","x-JIS0208",
		"JISAutoDetect","x-Johab","MacArabic","MacCentralEurope","MacCroatian","MacCyrillic","MacDingbat","MacGreek","MacHebrew","MacIceland","MacRoman","MacRomania","MacSymbol","MacThai","MacTurkish",
		"MacUkraine","MS950_HKSCS","MS936","PCK","Cp50220","Cp50221","MS874","MS949","MS950","x-windows-iso2022jp"};

	static{
		Arrays.sort(EXTENDED_ENCODING_SET);
	}
	
	public static void main(String[] args) {
		
		for (int i = 0; i < EXTENDED_ENCODING_SET.length; i++) {
//			System.out.print(EXTENDED_ENCODING_SET[i] + ",");	
			int index = Arrays.binarySearch(EXTENDED_ENCODING_SET, EXTENDED_ENCODING_SET[i]);
			if (i!=index){
				System.out.println("it didn't work");
				
			}
		}
		
		int index = Arrays.binarySearch(EXTENDED_ENCODING_SET, "bogus");
		if (index<0){
			System.out.println("It worked index " + index);
		}
		
		System.out.println("Finished");
		
		// switch to a list
		 List<String> encodeList = Arrays.asList(EXTENDED_ENCODING_SET);
		 
		 for (String encode: encodeList){
			 System.out.println(encode);
		 }		 		
	}
}
