/* Copyright 2017 Codemonkey */
package com.codemonkey.chatterbox.server;

import java.io.*;
import java.net.*;
import java.util.*;

import com.codemonkey.chatterbox.server.ChatServer;

public class ChatUser implements Runnable {

  PrintWriter out = null;
  BufferedReader in = null;
  Socket socket = null;

  public ChatUser(Socket socketVal) {
    socket = socketVal;
    try {
      out = new PrintWriter(socket.getOutputStream());
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
        String socketInputData = in.readLine();
        System.out.println("client said "+socketInputData);
        if(socketInputData.equals("end")) {
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
 

  public void writeMsgToSocket(String msg) {
    out.write(msg+"\n");
    out.flush();
  }

}
