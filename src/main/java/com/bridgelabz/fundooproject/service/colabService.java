package com.bridgelabz.fundooproject.service;

import java.util.List;

import com.bridgelabz.fundooproject.model.NoteDetails;
import com.bridgelabz.fundooproject.model.UserInformation;

public interface colabService {
	public List<UserInformation> getCollaboratedList(String token,long noteId);
	
	public List<NoteDetails> getCollaboratedNoteList(long id);
}
