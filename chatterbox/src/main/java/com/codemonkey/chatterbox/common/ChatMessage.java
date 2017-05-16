package com.codemonkey.chatterbox.common;

import java.util.Date;


public class ChatMessage implements java.io.Serializable {

  public enum MessageType {
	  REGULAR_MESSAGE,SIGNIN_MESSAGE
	}

  MessageType type;

  public String msg = null;
  public Date date = null;

	public String username;
	public String password;
	public static  String connectionMsg = "enter your username and password \n";

	public void buildRegularMessage(String msg, Date date,String username) {
		type = MessageType.REGULAR_MESSAGE;
		this.msg = msg;
		this.date = date;
		this.username = username;
	}

	public void buildAuthenticationMessage(String username, String password) {
		type = MessageType.SIGNIN_MESSAGE;
		this.username = username;
		this.password = password;
	}

	public boolean isRegularMessage() {
		return type == MessageType.REGULAR_MESSAGE;
	}

	public boolean isSigninMessage() {
		return type == MessageType.SIGNIN_MESSAGE;
	}
}

