package com.invindible.facetime.model;

import java.awt.Image;
import java.io.InputStream;

public class User {
	private String username;
	private String password;
	private InputStream image;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	public InputStream getB() {
		return image;
	}
	public void setB(InputStream b) {
		this.image = b;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
