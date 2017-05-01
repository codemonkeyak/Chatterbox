/*copyright 2017 codemonkey*/
package com.codemonkey.chatterbox.client;

import java.io.*;
import java.util.*;
import java.net.*;
import java.lang.*;

public class Client implements Runnable {

  public static String host= null;
	public static int port = 0;
	Socket socket = null;
	Scanner scan = null;
	PrintWriter out = null;
	BufferedReader in = null;
  public static void main(String args[]) {
  
	  host = args[0];
    port = Integer.parseInt(args[1]);
		String clientOutput = "";
    Client client = new Client();
		try {
		  client.socket = new Socket(host,port);
		  client.in = new BufferedReader(new InputStreamReader(client.socket.getInputStream()));
		  
		  new Thread(client).start();
			while(true) {
		    try {
          clientOutput =  client.in.readLine()+"\n";
          System.out.println("output to client from the socket "+clientOutput);
		    } catch(IOException e) {
          System.err.println("error with reading from socket "+e);
				  break;
			  }
		  }
		} catch (Exception e) {
      System.err.println("Error with socket "+e);
    }
  }

 public void run(){
    manageClient();
	}

  public void manageClient() {
	  try {
      scan = new Scanner(System.in);
		  out = new PrintWriter(socket.getOutputStream());
		  String userInput = "";
		  String userString = "";
      while(true) {
		    userString = scan.nextLine();
        userInput = userString+"\n";
			  out.write(userInput);
			  out.flush();
        if(userString.equals("end")) {
          out.close();
					scan.close();
				  in.close();
				  socket.close();
				  break;
        }
      }
		} catch (Exception e) {
      System.err.println("error with manage client "+e);
		}
	}



}
