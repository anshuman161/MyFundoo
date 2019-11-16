package com.bridgelabz.fundooproject.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;




@Entity
@Table(name = "user_details")
public class UserInformation
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	private String username;
	private String email;
	private long phoneno;
	private String address;
	private String password;
	private boolean isVerified;	
   
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(long phoneno) {
		this.phoneno = phoneno;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isVerified() 
	{
		return isVerified;
	}
	public void setVerified(boolean isVerified) 
	{
		this.isVerified = isVerified;
	}

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private List<NoteDetails> note ;

	public List<NoteDetails> getNote() 
	{
		return note;
	}
	public void setNote(List<NoteDetails> note) 
	{
		this.note = note;
	}
	

	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	private List<LabelDetails> label ;
	public List<LabelDetails> getLabel() {
		return label;
	}
	public void setLabel(List<LabelDetails> label) {
		this.label = label;
	}


	
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "collabratorUserList")
	@JsonBackReference
	private List<NoteDetails> collabratorNoteList;


	public void addColab(NoteDetails theNote) {
		if (collabratorNoteList == null) {
			collabratorNoteList = new ArrayList<>();
		}
		collabratorNoteList.add(theNote);
	}
	public List<NoteDetails> getCollabratorNoteList() {
		return collabratorNoteList;
	}
	public void setCollabratorNoteList(List<NoteDetails> collabratorNoteList) {
		this.collabratorNoteList = collabratorNoteList;
	}


	
	
	
	
}
