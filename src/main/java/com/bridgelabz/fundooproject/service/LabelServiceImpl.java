package com.bridgelabz.fundooproject.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundooproject.exception.UserException;
import com.bridgelabz.fundooproject.model.LabelDetails;
import com.bridgelabz.fundooproject.model.NoteDetails;
import com.bridgelabz.fundooproject.model.UserInformation;
import com.bridgelabz.fundooproject.repository.Label;
import com.bridgelabz.fundooproject.utilmethods.Utility;

@Service
public class LabelServiceImpl implements LabelService {
	@Autowired
	private Label labelRepositry;

	@Autowired
	private Utility utils;
	
	public UserInformation generatingToken(String token)
	{
		long id = utils.parseToken(token);
		UserInformation userInformation = labelRepositry.findById(id);
		return userInformation;
	}

	@Transactional
	@Override
	public void save(LabelDetails label, String token) 
	{  
		long id = utils.parseToken(token);
		UserInformation userInformation = labelRepositry.findById(id);
		
		LabelDetails labelDemo=labelRepositry.fetchLabelByName(label.getLabelName());		
		     
		if (userInformation != null && labelDemo==null) 
		{
			label.setUserId(id);
			labelRepositry.save(label);
		}
		else 
		{
			throw new UserException("Duplicate label is not allowed");
		}

	}
   @Transactional 
	@Override
	public boolean delete(long labelId, String token) 
   {

	   System.out.println("inside user");
		int demo=labelRepositry.deleteLabel(labelId);
		if (demo>0) {
			return true;
		}
		else 
			{
				throw new UserException("User not exist"); 
			}
     			
	}
	

    @Transactional
	@Override
	public boolean addNoteToLabel(long labelId, long noteId, String token) 
    {
		if (generatingToken(token) !=null) 
		{      
		    NoteDetails note= labelRepositry.findNoteById(noteId);
		    
			LabelDetails label=labelRepositry.findLabelById(labelId);
		     
			   note.getLabelList().add(label); 
			  
		       labelRepositry.save(label);
		} 
		else
		{
			throw new UserException("null is showing");
		}
		return false;
	}
    @Transactional
    @Override
    public boolean deleteNoteToLabel(long labelId, long noteId, String token) {
    System.out.println("inside delete label------");
    	if (generatingToken(token) !=null) 
		{      
		    NoteDetails note= labelRepositry.findNoteById(noteId);
		    
			LabelDetails label=labelRepositry.findLabelById(labelId);
		     
			   note.getLabelList().remove(label); 
		       labelRepositry.save(label);
		} 
		else
		{
			throw new UserException("user not present");
		}
		return false;
    }
    
    
    
    
 @Transactional   
@Override
public void edit(long labelId,LabelDetails label, String token) 
{
	 UserInformation user= labelRepositry.findById(utils.parseToken(token));
	 System.out.println("user check---"+user.getUserId());
	 
	 LabelDetails labelDetails= labelRepositry.findLabelById(labelId);
	 System.out.println("labelDetails check---"+labelDetails.getLabelId());
	   
	if (labelDetails !=null)
	{
	
	     labelDetails.setLabelName(label.getLabelName());
         labelRepositry.save(labelDetails);
	}
	else {
		throw new UserException("User not exist");
	}
}
@Transactional
@Override
public List<LabelDetails> fetchAllLabel(String token) 
{
	
 List<LabelDetails> ldetails= labelRepositry.fetchAllLabelById(utils.parseToken(token));
 System.out.println("isdsss"+    utils.parseToken(token));
 return ldetails;
}
@Transactional
@Override
public void updatelabel(long labelId,LabelDetails label, String token) {
	
	 UserInformation user= labelRepositry.findById(utils.parseToken(token));
	
	 if (user!=null) {
		  label.setLabelName(label.getLabelName());
		 labelRepositry.save(label);
		 
	}
	 else {
		 throw new UserException("User not exist");
	}
}




	 
	
}
