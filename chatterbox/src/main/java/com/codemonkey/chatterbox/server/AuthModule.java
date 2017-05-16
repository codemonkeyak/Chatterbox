/*copyright 2017 codemonkey*/
package com.codemonkey.chatterbox.server;

import java.io.*;
import java.net.*;
import java.util.*;


public class AuthModule {

  private static AuthModule auth = new AuthModule();

  private int userCount = 0;
	
	private AuthModule() {
	  System.out.println("creating an object ..");
	}

  public static boolean isSocketClosed = false;

  public static AuthModule getInstance( ) {
    System.out.println("Getting instance ");
    return auth;
	}

	public void authenticate(String username, String password,ObjectOutputStream out,ObjectInputStream in,Socket socket) {
	  if(username.equals("appu") && password.equals("pass")) {
      System.out.println("Username and password match");
			userCount++;
		} else {
      try {
			  out.close();
			  in.close();
			  socket.close();
				isSocketClosed = true;
			} catch (Exception e) {
        System.err.println("Error closing the connection due to authentication issues "+e);
			}
		}
	}








}
