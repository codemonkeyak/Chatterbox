/* Copyright 2017 Codemonkey */
package com.codemonkey.chatterbox.server;

import java.io.*;
import java.net.*;
import java.util.*;

import com.codemonkey.chatterbox.server.ChatServer;
import com.codemonkey.chatterbox.server.AuthModule;
import com.codemonkey.chatterbox.common.ChatMessage;

public class ChatUser implements Runnable {

  ObjectOutputStream out = null;
  ObjectInputStream in = null;
  Socket socket = null;
	String username = null;
	String password = null;
  ChatMessage cm = null;
  public ChatUser(Socket socketVal) {
    socket = socketVal;
    try {
      out = new ObjectOutputStream(socket.getOutputStream());
      in = new ObjectInputStream((socket.getInputStream()));
    } catch (Exception e) {
      System.err.println("Error with input/out stream "+e);
    }
  }

  public void authenticateClient() {
    try {
		  cm = (ChatMessage)in.readObject();
		  if(cm.isSigninMessage()) {
			  System.out.println("client sent username = "+cm.username+" password "+cm.password+" to the server");
				AuthModule authModule = AuthModule.getInstance();
				authModule.authenticate(cm.username,cm.password,out,in,socket);
		    manageReadWriteToSocket();
		  } else {
        throw new Exception();
		  }
		} catch (Exception e) {
       System.err.println("Error with authentication "+e);
		}
	}

  public void run() {
	  authenticateClient();
  }

  public void manageReadWriteToSocket() { 
    try {
      while(true) {
        cm = (ChatMessage)in.readObject();
        System.out.println(cm.username+" said "+cm.msg+" to server at "+cm.date.toLocaleString()+" ");
        if(cm.msg.equals("end")) {
          in.close();
          out.close();
          socket.close();
          break;
        }
        ChatServer.msgAllClients(cm);
      }
    } catch (Exception e) {
      System.err.println("Error with socket "+e);
    }
  }
 

  public void writeMsgToSocket(ChatMessage cm) {
    try {
      out.writeObject(cm);
      out.flush();
    } catch (Exception e) {
      System.err.println("error with writing msg to socket "+e);
    }
  }

}
