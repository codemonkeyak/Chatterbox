/* Copyright 2017 Codemonkey */
package com.codemonkey.chatterbox.server;

import java.io.*;
import java.net.*;
import java.util.*;

import com.codemonkey.chatterbox.server.ChatServer;
import com.codemonkey.chatterbox.common.ChatMessage;

public class ChatUser implements Runnable {

  ObjectOutputStream out = null;
  ObjectInputStream in = null;
  Socket socket = null;

  public ChatUser(Socket socketVal) {
    socket = socketVal;
    try {
      out = new ObjectOutputStream(socket.getOutputStream());
      in = new ObjectInputStream((socket.getInputStream()));
    } catch (Exception e) {
      System.err.println("Error with input/out stream "+e);
    }
  }


  public void run() {
    manageReadWriteToSocket();
  }

  public void manageReadWriteToSocket() { 
    try {
      while(true) {
        ChatMessage socketInputData = (ChatMessage)in.readObject();
        System.out.println("client said "+socketInputData.msg);
        if(socketInputData.msg.equals("end")) {
          in.close();
          out.close();
          socket.close();
          break;
        }
        ChatServer.msgAllClients(socketInputData);
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
