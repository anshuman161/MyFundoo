package com.bridgelabz.fundooproject.service;
import java.util.List;

import com.bridgelabz.fundooproject.model.NoteDetails;



public interface ElasticSearchService {
	String createNote(NoteDetails userNote);

	String updateNote(NoteDetails note);

	String deleteNote(NoteDetails note);

	List<NoteDetails> searchNoteByData(String searchData);
}