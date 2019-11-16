package com.bridgelabz.fundooproject.service;

import java.util.List;

import com.bridgelabz.fundooproject.model.NoteDetails;

public interface ElasticService {
	public void save(NoteDetails note);
	public void update(long noteId, NoteDetails modifynote); 
	public void deleteNote(NoteDetails modifynote);
	public List<NoteDetails> search(String tittle, String description);
}
