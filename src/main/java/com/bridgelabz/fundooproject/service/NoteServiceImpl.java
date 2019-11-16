package com.bridgelabz.fundooproject.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundooproject.exception.UserException;
import com.bridgelabz.fundooproject.model.NoteDetails;
import com.bridgelabz.fundooproject.model.NoteDto;
import com.bridgelabz.fundooproject.model.UserInformation;
import com.bridgelabz.fundooproject.repository.Note;
import com.bridgelabz.fundooproject.repository.UserRepositry;
import com.bridgelabz.fundooproject.utilmethods.Utility;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoteServiceImpl implements NoteServie {
	@Autowired
	private Note noteRepositry;

	@Autowired
	private Utility utils;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	ModelMapper mapper;

	@Autowired
	private UserRepositry user;
  
	@Autowired
	private ElasticSearchService elasticsearch;
	
	 @Autowired
	 private colabService colabService;
	
	public UserInformation generatingToken(String token) {
		long id = (long) utils.parseToken(token);
		UserInformation user = noteRepositry.findById(id);
		return user;
	}

	@Transactional
	@Override
	public void save(NoteDto note, String token) {
		long id = utils.parseToken(token);
		UserInformation user = noteRepositry.findById(id);
		if (user != null) {
			NoteDetails noteDetails = mapper.map(note, NoteDetails.class);
			noteDetails.setCreatedTime(LocalDateTime.now());

			noteDetails.setUpdatedTime(LocalDateTime.now());
			noteDetails.setPin(false);
			noteDetails.setArchieve(false);
			noteDetails.setTrash(false);

			user.getNote().add(noteDetails);
			noteRepositry.save(noteDetails);
             List<NoteDetails> notes=noteRepositry.getNoteIdByTitle(noteDetails.getTittle());
             System.out.println(notes.toString());
               NoteDetails noteObject =notes.get(0);
               elasticsearch.createNote(noteObject);
			// redisTemplate.opsForValue().set(noteDetails.getTittle(),noteDetails.getDescription());
		} else {
			throw new UserException("User Not Exist");
		}
	}

	@Transactional
	@Override
	public void updateNotes(long noteId, NoteDetails information, String token) {

		
		 UserInformation user =noteRepositry.findById(utils.parseToken(token));
		 System.out.println(user+" user");
       if (user!=null) {
		
		System.err.println("Under user");
		NoteDetails note = noteRepositry.findNoteById(noteId);
		System.out.println(note.getNoteId());

		if (note != null) {
			System.err.println("under note is  ");
			note.setDescription(information.getDescription());
			note.setTittle(information.getTittle());
		  //  note.setPin(information.isPin());
		//	note.setArchieve(information.isArchieve());
		//	note.setTrash(information.isTrash());
			note.setUpdatedTime(LocalDateTime.now());
		//	note.setRemindme(information.getRemindme());
		//	note.setColor(information.getColor());
			noteRepositry.save(note);
		} else {
			throw new UserException("note is not present");
		}
       }
       else {
    	   throw new UserException("user is not present");
	}

	}

	@Transactional
	@Override
	public void noteColor(long noteId, String color, String token) {
		UserInformation user = noteRepositry.findById(utils.parseToken(token));
		if (user != null) {
			NoteDetails note = noteRepositry.findNoteById(noteId);
			if (note != null) {
				note.setColor(color);
				noteRepositry.save(note);
			} else {
				throw new UserException("note is not present");
			}
		} else {
			throw new UserException("user is not present");
		}
	}

	@Transactional
	@Override
	public long deleteNotes(long noteId, String token) {

		long demo = 0;
		if (generatingToken(token) != null) {
			NoteDetails noteDetails = noteRepositry.findNoteById(noteId);
			if (noteDetails != null) {
				System.out.println("inside if block...");
				demo = noteRepositry.deleteNotes(noteDetails.getNoteId());
				return demo;
			} else {
				throw new UserException("Note is Not Exist");
			}
		}
		return demo;
	}

//	@Transactional
//	@Override
//	public List<NoteDetails> fetchAllNotes(String token)
//	{
//		
//		List<NoteDetails> arrayOfNotes = new ArrayList<>();
//		           
//		arrayOfNotes = noteRepositry.fetchNotesByUserId(utils.parseToken(token));
//		List<NoteDetails> colabNotes=colabService.getCollaboratedNoteList(utils.parseToken(token));
//		
//		arrayOfNotes.addAll(colabNotes);
//		return arrayOfNotes;
//		
//
//	}

	@Transactional
	@Override
	public List<NoteDetails> fetchAllNotes(String token)
	{
		List<NoteDetails> notes=noteRepositry.fetchNotesByUserId(utils.parseToken(token));
		List<NoteDetails> notesColab=colabService.getCollaboratedNoteList(utils.parseToken(token));
		notes.addAll(notesColab);
		return  notes;
	
		
		

	}
	
	@Transactional
	@Override
	public List<NoteDetails> fetchNotesToBin(String token) {
		// TODO Auto-generated method stub
		return noteRepositry.fetchNotesByTrash(utils.parseToken(token));
	}

	@Transactional
	@Override
	public List<NoteDetails> fetchArchiveNotes(String token) {
		// TODO Auto-generated method stub
		return noteRepositry.fetchArchiveNotes(utils.parseToken(token));
	}

	@Transactional
	@Override
	public void changeTrashTrue(long noteId, String token) {

		UserInformation user = noteRepositry.findById(utils.parseToken(token));
		if (user != null) {
			NoteDetails noteDetails = noteRepositry.findNoteById(noteId);
			if (noteDetails != null) {
				noteDetails.setTrash(true);
				noteRepositry.save(noteDetails);
			}
		}
	}

	@Transactional
	@Override
	public void changeTrashFalse(long noteId, String token) {
		UserInformation user = noteRepositry.findById(utils.parseToken(token));
		if (user != null) {
			NoteDetails noteDetails = noteRepositry.findNoteById(noteId);
			if (noteDetails != null) {
				noteDetails.setTrash(false);
				noteRepositry.save(noteDetails);
			}
		}
	}

	@Transactional
	@Override
	public void changeArchiveTrue(long noteId, String token) {

		UserInformation user = noteRepositry.findById(utils.parseToken(token));
		if (user != null) {
			NoteDetails noteDetails = noteRepositry.findNoteById(noteId);
			if (noteDetails != null) {
				System.out.println("inside archive save..........");
				noteDetails.setArchieve(true);
				noteRepositry.save(noteDetails);
			}
		}
	}

	@Transactional
	@Override
	public void changeArchiveFalse(long noteId, String token) {
		UserInformation user = noteRepositry.findById(utils.parseToken(token));
		if (user != null) {
			NoteDetails noteDetails = noteRepositry.findNoteById(noteId);
			if (noteDetails != null) {
				noteDetails.setArchieve(false);
				noteRepositry.save(noteDetails);
			}
		}
	}

	@Transactional
	@Override
	public NoteDetails updateReminder(LocalDateTime reminderDate, String token, long noteId) {
		UserInformation user = noteRepositry.findById(utils.parseToken(token));
		NoteDetails noteDetails = null;
		if (user != null) {
			noteDetails = noteRepositry.findNoteById(noteId);
			if (noteDetails != null) {
				noteDetails.setRemindme(reminderDate);
				noteDetails.setUpdatedTime(LocalDateTime.now());
				noteRepositry.save(noteDetails);
			} else {
				throw new UserException("Note is Not Exist");
			}
		} else {
			throw new UserException("user is Not Exist");
		}
		return noteDetails;
	}

	@Transactional
	@Override
	public List<NoteDetails> getReminder(String token) {
		UserInformation user = noteRepositry.findById(utils.parseToken(token));
		NoteDetails noteDetails = null;
		List<NoteDetails> notesList = null;
		if (user != null) {
			notesList = user.getNote();
			notesList = notesList.stream().filter(
					notecheck -> notecheck.getRemindme() != null && !notecheck.isArchieve() && !notecheck.isTrash())
					.collect(Collectors.toList());
		} else {
			throw new UserException("user is Not Exist");
		}

		return notesList;
	}

	@Transactional
	@Override
	public String deleteRemainder(long noteId, String token) {
		UserInformation user = noteRepositry.findById(utils.parseToken(token));
		NoteDetails noteDetails = null;
		if (user != null) {
			noteDetails = noteRepositry.findNoteById(noteId);
			if (noteDetails != null) {
				noteDetails.setRemindme(null);
				noteRepositry.save(noteDetails);
			} else {
				throw new UserException("Note is Not Exist");
			}
		} else {
			throw new UserException("user is Not Exist");
		}
		return "reminder is removed!!!!!!";
	}

	@Override
	@Transactional
	public void CollaborateNote(long noteId, String email) {
		List<UserInformation> users = user.getListOfUsers();
		users = users.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).collect(Collectors.toList());
		log.info("user add " + users.toString());
		
		NoteDetails note = noteRepositry.findNoteById(noteId);
		
		log.info("note add " + note.toString());
		
		note.getCollabratorUserList().add(users.get(0));
	}

	@Override
	@Transactional
	public Set<UserInformation> getCollabaratedUsers(long noteId) {
		NoteDetails note = noteRepositry.findNoteById(noteId);
		log.info("note add " + note.toString());
		return note.getCollabratorUserList();
	}

	@Override
	@Transactional
	public void deleteCollabaratedNote(long noteId, String email) {
		NoteDetails note = noteRepositry.findNoteById(noteId);
		List<UserInformation> users = user.getListOfUsers();
		users = users.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).collect(Collectors.toList());
		note.getCollabratorUserList().remove(users.get(0));
	}

	
	@Override
	@Transactional
	public List<UserInformation> getCollaboratedList(String token,long noteId) {
		long id = utils.parseToken(token);
		UserInformation owner = user.getUserById(id);
		
		NoteDetails ownerNotes = noteRepositry.findNoteById(noteId);
		
		List<UserInformation> colabUser = (List<UserInformation>) ownerNotes.getCollabratorUserList();
		
		return colabUser;
 }
	
	@Override
	@Transactional
	public List<NoteDetails> searchNotes(String token, String keyword, String field) 
	{
	long id = utils.parseToken(token);
	List<NoteDetails> notes= elasticsearch.search(keyword, field);
	return notes;
	}

}
