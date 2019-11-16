package com.bridgelabz.fundooproject.model;

import java.util.List;

public class NoteAndColab {
	private NoteDetails note;
	private List<UserInformation> user;
	public NoteDetails getNote() {
		return note;
	}
	public void setNote(NoteDetails note) {
		this.note = note;
	}
	public List<UserInformation> getUser() {
		return user;
	}
	public void setUser(List<UserInformation> user) {
		this.user = user;
	}
	
	
}
