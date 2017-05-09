package com.depas.hackerrank;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Maps {

	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        Map<String,Integer> phoneMap = new HashMap<>();  
        for(int i = 0; i < n; i++){
            String name = in.next();
            int phone = in.nextInt();
            // Write code here
            phoneMap.put(name, new Integer(phone));
        }
        while(in.hasNext()){
            String s = in.next();
            // Write code here
            Integer phoneNumber = phoneMap.get(s);
            String out="";
            out = phoneNumber == null ? "Not found" : s + "=" + phoneNumber;
            System.out.println(out);
        }
        in.close();

	}

}
