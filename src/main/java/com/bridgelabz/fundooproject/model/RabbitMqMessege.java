package com.bridgelabz.fundooproject.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class RabbitMqMessege implements Serializable {

private static final long serialVersionUID = -8904803216597895332L;
private String email;
private String link;
private String token;
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getLink() {
	return link;
}
public void setLink(String link) {
	this.link = link;
}
public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}
public static long getSerialversionuid() {
	return serialVersionUID;
}


}