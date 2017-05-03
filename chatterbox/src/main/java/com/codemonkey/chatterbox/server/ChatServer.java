/* Copyright 2017 Codemonkey */
package com.codemonkey.chatterbox.server;

import java.io.*;
import java.net.*;
import java.util.*;

import com.codemonkey.chatterbox.server.ChatUser;
import com.codemonkey.chatterbox.common.ChatMessage;

public class ChatServer  {

  public static List<ChatUser> list = null;
  public static void main(String args[]) {
    
    int port = Integer.parseInt(args[0]); 
    try { 
      ServerSocket serverSocket = new ServerSocket(port);
      list = new LinkedList<ChatUser>();
      while(true) {
        Socket socket = serverSocket.accept();
        ChatUser user  = new ChatUser(socket);        
        // Adds every new client connected to the server into a list to
        // broadcast eachothers' messages.
        list.add(user);
        // Create another thread for handline socket read blocking call.
        new Thread(user).start();
      }
    } catch (Exception e) {
      System.err.println("Error with main "+e);
    }
  }

  // Writes messages to the list of clients connected to server.
  public static void msgAllClients(ChatMessage cm) {
     for(int i=0; i<list.size(); i++){
       list.get(i).writeMsgToSocket(cm);
     }
  }

}
