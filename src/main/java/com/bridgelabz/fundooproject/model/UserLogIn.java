package com.bridgelabz.fundooproject.model;

import org.springframework.stereotype.Component;

@Component
public class UserLogIn 
{
private String email;
private String password;
//public UserLogIn(String email, String password) {
//	
//}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}

}
