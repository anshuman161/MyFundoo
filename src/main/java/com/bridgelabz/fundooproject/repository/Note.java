package com.bridgelabz.fundooproject.repository;

import java.util.List;

import com.bridgelabz.fundooproject.model.NoteDetails;
import com.bridgelabz.fundooproject.model.UserInformation;

public interface Note {
 public void save(NoteDetails noteDetails);
 
public UserInformation findById(long userId);

public void updateNotes(long noteId, NoteDetails details);

public NoteDetails findNoteById(long noteId);

public int deleteNotes(long id);

public List<NoteDetails> fetchNotesByUserId(long id);

public List<NoteDetails> fetchNotesByTrash(long id);

//public int sortingNotes(Long id);

public List<NoteDetails> fetchArchiveNotes(long userId);

public void saveColab(Note colab);


}
