package com.bridgelabz.fundooproject.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "note_details")
public class NoteDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long noteId;
	private String tittle;
	private String description;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	private boolean isPin;
	private boolean isArchieve;
	private boolean isTrash;
    private String color;
    
	private LocalDateTime remindme;
	 
	
	public LocalDateTime getRemindme() {
		return remindme;
	}

	public void setRemindme(LocalDateTime remindme) {
		this.remindme = remindme;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public long getNoteId() {
		return noteId;
	}

	public void setNoteId(long noteId) {
		this.noteId = noteId;
	}

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
	
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.ALL) 
	@JoinTable(name = "Label_Note", joinColumns = { @JoinColumn(name = "noteId") }, inverseJoinColumns = {
            @JoinColumn(name = "labelId") })
	@JsonManagedReference
	private List<LabelDetails> labelList;

	public List<LabelDetails> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<LabelDetails> labelList) {
		this.labelList = labelList;
	}
   
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<UserInformation> collabratorUserList;


	public Set<UserInformation> getCollabratorUserList() {
		return collabratorUserList;
	}

	public void setCollabratorUserList(Set<UserInformation> collabratorUserList) {
		this.collabratorUserList = collabratorUserList;
	}
//	
//	@ManyToMany(cascade = CascadeType.ALL)
//	@JsonBackReference 
//	private List<UserInformation> getListUser;
//
//
//	public List<UserInformation> getGetListUser() {
//		return getListUser;
//	}
//
//	public void setGetListUser(List<UserInformation> getListUser) {
//		this.getListUser = getListUser;
//	}
    
	
	
	
	


	
	
}
