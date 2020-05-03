package com.report.miscallaneous;

public class Info {
	
	private String username;
	private String password;
	private String backend_path;
	
	public Info(String username, String password, String backend_path) {
		super();
		this.username = username;
		this.password = password;
		this.backend_path = backend_path;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBackend_path() {
		return backend_path;
	}
	public void setBackend_path(String backend_path) {
		this.backend_path = backend_path;
	}
	
	

}
