package com.bridgelabz.fundooproject.model;

import java.time.LocalDateTime;

public class NoteDto 
{
private String tittle;
private String description;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
private boolean isPin;
private boolean isArchieve;
private boolean isTrash;
public String getTittle() {
	return tittle;
}
public void setTittle(String tittle) {
	this.tittle = tittle;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public LocalDateTime getCreatedTime() {
	return createdTime;
}
public void setCreatedTime(LocalDateTime createdTime) {
	this.createdTime = createdTime;
}
public LocalDateTime getUpdatedTime() {
	return updatedTime;
}
public void setUpdatedTime(LocalDateTime updatedTime) {
	this.updatedTime = updatedTime;
}
public boolean isPin() {
	return isPin;
}
public void setPin(boolean isPin) {
	this.isPin = isPin;
}
public boolean isArchieve() {
	return isArchieve;
}
public void setArchieve(boolean isArchieve) {
	this.isArchieve = isArchieve;
}
public boolean isTrash() {
	return isTrash;
}
public void setTrash(boolean isTrash) {
	this.isTrash = isTrash;
}

}
