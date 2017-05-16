/*copyright 2017 codemonkey*/
package com.codemonkey.chatterbox.client;

import java.io.*;
import java.util.*;
import java.net.*;
import java.lang.*;

import com.codemonkey.chatterbox.common.ChatMessage;

public class Client implements Runnable {

  public static String host= null;
  public static int port = 0;
  Socket socket = null;
  Scanner scan = null;
  ObjectOutputStream out = null;
  ObjectInputStream in = null;
	Scanner username = null;
	Scanner password = null;
	ChatMessage cm = null;
	String name = null;
  public static void main(String args[]) {
  
    host = args[0];
    port = Integer.parseInt(args[1]);
    Client client = new Client();
    try {
      client.socket = new Socket(host,port);
      client.in = new ObjectInputStream(client.socket.getInputStream());
      
      new Thread(client).start();
      while(true) {
        try {
          ChatMessage clientOutput = (ChatMessage) client.in.readObject();
          System.out.println("output to client from the socket "+clientOutput.username+" said "+clientOutput.msg+" at "+clientOutput.date+" time ");
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

	public ChatMessage sendCredentials() {
	  username = new Scanner(System.in);
		password = new Scanner(System.in);
		String name = username.nextLine();
		String pass = password.nextLine();
    cm = new ChatMessage();
		cm.buildAuthenticationMessage(name,pass);
		return cm;
	}

	public ChatMessage sendMsg() {
	   String user = name; 
     Scanner userMsg = new Scanner(System.in);
		 String msg = userMsg.nextLine();
     Date date = new Date();
		 cm = new ChatMessage();
		 cm.buildRegularMessage(msg,date,user);
		 return cm;
	}

  public void manageClient() {
    try {
      scan = new Scanner(System.in);
      out = new ObjectOutputStream(socket.getOutputStream());
      String userString = "";
			cm = sendCredentials();
			name = cm.username;
			out.writeObject(cm);
			out.flush();
      while(true) {
        cm = sendMsg();
        out.writeObject(cm);
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
