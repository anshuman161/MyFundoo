package com.bridgelabz.fundooproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.bridgelabz.fundooproject.model.LabelDetails;
import com.bridgelabz.fundooproject.model.NoteDetails;
import com.bridgelabz.fundooproject.model.UserInformation;
import com.bridgelabz.fundooproject.service.LabelService;
import com.bridgelabz.fundooproject.utilmethods.Response;
@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = {"jwtToken"})
@RestController
@RequestMapping("/labels")
public class LabelController 
{
	@Autowired
	private LabelService service;

	@PostMapping("/addlables")
	public ResponseEntity<Response> addLabel(@RequestBody LabelDetails label, @RequestHeader String token)
	{
		service.save(label, token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Label Saved successfully", 200, label));
	}
	
	@GetMapping("/fetchalllabel")
	public List<LabelDetails> FetchLabel( @RequestHeader String token)
	{
		System.out.println("under label!!!!!"+token);
		List<LabelDetails>	labellist=service.fetchAllLabel(token);
		return labellist;
	}
	
     
	@PutMapping("/addNotesLables")
	public ResponseEntity<Response> addNoteLabel(@RequestParam long labelId,@RequestParam long noteId, @RequestHeader String token)
	{
		service.addNoteToLabel(labelId,noteId, token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Label assigned to Notes successfully", 200, labelId));
	}
	@DeleteMapping("/deleteNotesLables")
	public ResponseEntity<Response> deleteNoteLabel(@RequestParam long labelId,@RequestParam long noteId, @RequestHeader String token)
	{
		service.deleteNoteToLabel(labelId,noteId, token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Label deleted to Notes successfully", 200, labelId));
	}
	
	
	@DeleteMapping("/deleteLabels")
	public ResponseEntity<Response> deleteLabel(@RequestParam long labelId, @RequestHeader String token) 
	{
		service.delete(labelId, token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Deleted successfully", 200, labelId));
	}
	
	@PutMapping("/editLabels")
	public ResponseEntity<Response> editLabel(@RequestParam long labelId,@RequestBody LabelDetails label, @RequestHeader String token) 
	{
		service.edit(labelId,label, token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Edited successfully", 200, token));
	}

    @PostMapping("/updation")
    public ResponseEntity<Response> updateNote(@RequestParam long labelId,@RequestBody LabelDetails label , @RequestHeader String token)
    {
    	service.updatelabel(labelId, label, token);
    	 return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("label Updated successfully", 200, token));
    }
    
	
	
}
