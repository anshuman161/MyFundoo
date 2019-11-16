package com.bridgelabz.fundooproject.utilmethods;

public class Response {
   private String messege;
   private int status;
   private Object object;
public String getMessege() {
	return messege;
}
public void setMessege(String messege) {
	this.messege = messege;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}


public Object getObject() {
	return object;
}
public Response(String messege, int status,Object object) {

	this.messege = messege;
	this.status = status;
	this.object = object;
}
   
}
