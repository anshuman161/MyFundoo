package com.bridgelabz.fundooproject.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.bridgelabz.fundooproject.model.NoteDetails;
import com.bridgelabz.fundooproject.model.NoteDto;
import com.bridgelabz.fundooproject.model.UserInformation;
import com.bridgelabz.fundooproject.repository.Note;

public interface NoteServie {
public void save(NoteDto note,String token);

public void updateNotes(long noteId, NoteDetails details, String token);
public void noteColor(long noteId, String color, String token);
public long deleteNotes(long noteId, String token);

//public void sortNotes(NoteDetails notes, String token);

public void changeTrashTrue(long id, String token);

public List<NoteDetails> fetchAllNotes(String token);

public List<NoteDetails> fetchNotesToBin(String token);

public void changeTrashFalse(long id, String token);

public void changeArchiveTrue(long id, String token);

public void changeArchiveFalse(long id, String token);

public List<NoteDetails> fetchArchiveNotes(String token);

public  NoteDetails updateReminder(LocalDateTime reminderDate, String token, long noteId);

public List<NoteDetails> getReminder(String token);

public String deleteRemainder(long noteId, String token);

public List<NoteDetails> searchNotes(String token, String title, String description);

public void CollaborateNote(long noteId, String email);

public Set<UserInformation> getCollabaratedUsers(long noteId);

public void deleteCollabaratedNote(long noteId, String email);

public List<UserInformation> getCollaboratedList(String token,long noteId);

//public List<NoteDetails> getCollaboratedNoteList(long id);


}
