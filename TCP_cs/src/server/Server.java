package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author Owner
 */


import java.io.*;
import java.net.*;
//finall
public class Server {
    public static void main(String[] args) throws IOException {
    	
        final int PORT = 1111;
        ServerSocket ss = new ServerSocket(PORT);
        Socket s = ss.accept();

        
       
        InputStreamReader in=  new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        PrintWriter wr = new PrintWriter(s.getOutputStream(), true);
        
        
        
        while (true) {
           String input = bf.readLine();
           
            if ( input.isBlank()||input.isEmpty()) {
            	wr.println("500 Request is empty");
           continue;
            }
           
          
         
            String type=input.substring(0, 1).toUpperCase();
            try {
             
         
             if (!input.startsWith("B") && !input.startsWith("H")||type==null) {
             	wr.println("300 Bad request");  
             	  continue;
             }
            }catch (Exception e) {
            	wr.println("300 Bad request");
            }

            try {
            if (input.length() != 2) {
                	wr.println("400 The number is missing");
                	  continue;
              } 
         
            	String num=input.substring(2);
               int value = Integer.valueOf(num);
               String res=converter(value,type);
               wr.println("200 " + res);
               
               } catch (NumberFormatException e) {
            	wr.println("400 The number is missing");
            }
        }
    }
    
    public static String converter(int v,String type) {//convertion method
    	if (type.equals("H")) {
        	return Integer.toHexString(v).toUpperCase();
        }
    	else {
        	return Integer.toBinaryString(v);
        	} 
    }
    
    
}

