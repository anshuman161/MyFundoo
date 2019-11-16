package com.bridgelabz.fundooproject.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundooproject.model.NoteDetails;
import com.bridgelabz.fundooproject.model.UserInformation;
import com.bridgelabz.fundooproject.repository.Note;
import com.bridgelabz.fundooproject.repository.UserRepositry;
import com.bridgelabz.fundooproject.utilmethods.Utility;
@Service
public class ColabServiceimpl implements colabService {
	@Autowired
	private UserRepositry userDao;
	
	@Autowired
	private Note noteDao;
	
	@Autowired
	private Utility utils;

	@Transactional
	public List<UserInformation> getCollaboratedList(String token,long noteId) {
		long id = utils.parseToken(token);
		UserInformation owner = userDao.getUserById(id);
		
		NoteDetails ownerNotes = noteDao.findNoteById(noteId);
		
		List<UserInformation> colabUser = (List<UserInformation>) ownerNotes.getCollabratorUserList();
		
		return colabUser;
 }
	
	
	@Override
	@Transactional
	public List<NoteDetails> getCollaboratedNoteList(long id) {
		UserInformation owner = userDao.getUserById(id);
		
		List<NoteDetails> ownerNotes= (List<NoteDetails>) userDao.getUserById(owner.getUserId()).getCollabratorNoteList();
		return ownerNotes;
	}
	
	
	
	
	
	
	
	
	
	
//
//	public void deletCollaboratedList(String token,String emailId, Integer noteId) {
//		Integer id = Util.parseToken(token);
//		List<UserDetailsForRegistration> owner = userDao.getUserById(id);
//
//		UserDetailsForRegistration ownerUser = owner.get(0);
//		UserDetailsForRegistration colabUser = userDao.getUserByMail(emailId);
//
//		List<Note> ownerNotes = noteDao.getNotebyUserId(id);
//		Long numberOfNotes = ownerNotes.stream().filter(n -> n.getNoteId() == noteId).count();
//
//		List<Note> checkColab = noteDao.getNotebyUserId(colabUser.getId());
//
//		List<Note> colabNote = checkColab.stream().filter(notes -> notes.getNoteId() == noteId)
//				.collect(Collectors.toList());
//
//		Note note = noteDao.getNotebyNoteId(noteId);
//		note.removeColab(colabUser);
//		noteDao.saveColab(note);
//		System.out.println("deleted");
//	}

	
	
}
