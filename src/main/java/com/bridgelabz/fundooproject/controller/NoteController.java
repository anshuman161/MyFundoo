package com.bridgelabz.fundooproject.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundooproject.model.NoteAndColab;
import com.bridgelabz.fundooproject.model.NoteDetails;
import com.bridgelabz.fundooproject.model.NoteDto;
import com.bridgelabz.fundooproject.model.UserInformation;
import com.bridgelabz.fundooproject.service.NoteServie;
import com.bridgelabz.fundooproject.service.colabService;
import com.bridgelabz.fundooproject.utilmethods.Response;
@CrossOrigin(allowedHeaders = "*", origins = "*",  exposedHeaders = {"jwtToken"})
@RestController
@RequestMapping("/notes")
public class NoteController
{
 @Autowired
 private NoteServie servie;
 @Autowired
 private colabService colabService;

 
    @PostMapping("/creation")
	public ResponseEntity<Response> saveNote(@RequestBody NoteDto note, @RequestHeader  String token)
	{    
    	System.out.println("inside the note api");
		servie.save(note,token);	
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Note Saved successfully", 200, note));	
	}

    @PutMapping("/updation/{noteId}")
    public ResponseEntity<Response> updateNote(@PathVariable long noteId ,@RequestBody NoteDetails note, @RequestHeader String token)
    {
          servie.updateNotes(noteId,note, token);
          return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("Note updated", HttpStatus.CREATED.value(), token));
    }
    
    @DeleteMapping("/deletion/{noteId}")
    public ResponseEntity<Response> deleteNote(@PathVariable("noteId") long noteId, @RequestHeader String token)
    {
     long notesId=servie.deleteNotes(noteId, token);
   	 return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Note deleted successfully", 200, noteId ));
	}
    
    @GetMapping("/fetchnotestobin")
    public ResponseEntity<List<NoteDetails>> FetchNoteToBin(@RequestHeader String token)
    {
    	List<NoteDetails> noteslist=servie.fetchNotesToBin(token);
   	 return new ResponseEntity<List<NoteDetails>>(noteslist,HttpStatus.OK);
	}
    @GetMapping("/fetcharchivenote")
    public List<NoteDetails> FetchNoteToArchive(@RequestHeader String token)
    {
    	List<NoteDetails> noteslist=servie.fetchArchiveNotes(token);
   	 return noteslist;
	}
    
//    @GetMapping("/fetchallnotes")
//	public List<NoteDetails> FetchNotes( @RequestHeader String token)
//	{	    
//		    List<NoteAndColab> noteColab=new ArrayList<>();
//			List<NoteDetails> notes=	servie.fetchAllNotes(token);
//			
//			List<UserInformation> users= new ArrayList<>();
//			for(NoteDetails n : notes) {
//				NoteAndColab c= new NoteAndColab();
//				c.setNote(n);
//				users=colabService.getCollaboratedList(token, n.getNoteId());
//				c.setUser(users);
//				noteColab.add(c);
//			}
//			return notes;    
//		    
//		    
//		    
	
    @GetMapping("/fetchallnotes")
	public List<NoteDetails> FetchNotes( @RequestHeader String token)
	{	    
    	
    	return servie.fetchAllNotes(token);
			    
		    
		    
		    
	}
    @GetMapping("/changetrashtrue/{id}")
	public ResponseEntity<Response> changeTrashTrue(@PathVariable long id, @RequestHeader String token)
	{
		servie.changeTrashTrue(id,token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Trash is True ", 200,id));
	      
	}
    @GetMapping("/changetrashfalse/{id}")
	public ResponseEntity<Response> changeTrashFalse(@PathVariable long id, @RequestHeader String token)
	{
		servie.changeTrashFalse(id,token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Trash is False", 200,id));
	      
	}
    @GetMapping("/changearchivetrue/{id}")
	public ResponseEntity<Response> changeArchiveTrue(@PathVariable long id, @RequestHeader String token)
	{
    	System.out.println("inside archive true---------");
		servie.changeArchiveTrue(id,token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Archive  True", 200,id));
	} 
    @GetMapping("/changearchivefalse/{id}")
	public ResponseEntity<Response> changeArchiveFalse(@PathVariable long id, @RequestHeader String token)
	{
		servie.changeArchiveFalse(id,token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Archive  false", 200,id));
	      
	} 
   
    
    @PutMapping("/color/{noteId}")
    public ResponseEntity<Response> noteColor(@PathVariable long noteId ,@RequestParam  String color, @RequestHeader("token") String token)
    {
    	  
          servie.noteColor(noteId, color,token);
          return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("Note color updated", HttpStatus.CREATED.value(), token));
    }
    
    @PutMapping("/reminder")
	public ResponseEntity<NoteDetails> updateReminder(@RequestParam Long noteId,
	@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reminderDate,
	@RequestHeader String token) {
	System.out.println(reminderDate);
	NoteDetails notes = servie.updateReminder(reminderDate, token, noteId);
	return new ResponseEntity<NoteDetails>(notes, HttpStatus.OK);
	}
	@GetMapping("/getreminder")
	public ResponseEntity<List<NoteDetails>> getReminders(@RequestHeader String token)
	{
	System.out.println("inside getReminders method");
	List<NoteDetails> notes= servie.getReminder(token);
	return new ResponseEntity<List<NoteDetails>>(notes, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteRemainder")
	public ResponseEntity<Response> deleteRemainder(@RequestParam long noteId, @RequestHeader String token){
		String result=servie.deleteRemainder(noteId, token);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("Note color updated", HttpStatus.CREATED.value(), token));
	}
    
	@PostMapping("/addcollaborator")
	public ResponseEntity<Response> addCollaborator(@RequestParam("noteId") int noteId,
			@RequestParam("email") String email, @RequestHeader String token) {
		servie.CollaborateNote(noteId, email);
		System.out.println("inside add collaborator controller-------"+noteId+"======"+email);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("Collabarator added ", HttpStatus.CREATED.value(), null));
	}
	
	
	@PostMapping("/removecollabarator")
	public ResponseEntity<Response> removeCollabarator(@RequestParam("noteId") int noteId,
			@RequestParam("userId") int userId, @RequestHeader String token) {
//		noteService.CollabarateNote(noteId,userId);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("Collabarator added ", HttpStatus.CREATED.value(), null));
	}

	@GetMapping("/getcollaborator")
	public Set<UserInformation> getCollabarator(@RequestParam("noteId") int noteId, @RequestHeader String token) {
		System.out.println("inside get collaborator controller-------"+servie.getCollabaratedUsers(noteId));
		return servie.getCollabaratedUsers(noteId);
	}
  
	

	@PostMapping("/deletenoteonuser")
	public ResponseEntity<Response> deleteNoteOnUser(@RequestParam("noteId") int noteId,
			@RequestParam("email") String email) {
		servie.deleteCollabaratedNote(noteId, email);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("Note on label  deleted ", HttpStatus.CREATED.value(), null));
	}
    
	
	@PostMapping("/search")
	public List<NoteDetails> searchNotes(@RequestHeader("token") String token, @RequestBody String titleordescription) {
		String title = titleordescription;
		String description = titleordescription;
		return servie.searchNotes(token, title, description);
	}
    
    
}
