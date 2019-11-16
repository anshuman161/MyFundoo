package com.bridgelabz.fundooproject.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundooproject.model.LabelDetails;
import com.bridgelabz.fundooproject.model.NoteDetails;
import com.bridgelabz.fundooproject.model.UserInformation;

@Repository
public class LabelRepositryImpl implements Label
{
@Autowired
EntityManager entity;

@Override
public UserInformation findById(long userId) 
{
	Session session = entity.unwrap(Session.class);
	Query query = session.createQuery("from UserInformation where userId='"+userId+"'");
	System.out.println(query);
	//query.setParameter("userId", userId);	
	return  (UserInformation) query.uniqueResult();
}


	@Override
	public void save(LabelDetails label)
	{
		Session session = entity.unwrap(Session.class);
		session.save(label);
	}
	
	
	@Override
	public NoteDetails findNoteById(long id) 
	{
	  NoteDetails demo= (NoteDetails) entity.unwrap(Session.class).createQuery("from NoteDetails where noteId='"+id+"'").uniqueResult();
		return demo;
		
	}
	@Override
	public LabelDetails fetchLabelByName(String labelName) 
	{
		Session session=entity.unwrap(Session.class);
		Query query=session.createQuery("from LabelDetails where labelName='"+labelName+"'");
		return  (LabelDetails) query.uniqueResult();
	}
	@Override
	public int deleteLabel(long labelId) 
	{ 
		Session session = entity.unwrap(Session.class);
		Query query = session.createQuery("delete from LabelDetails l where l.labelId=:id");
		query.setParameter("id", labelId);
		return query.executeUpdate();
		
	}
	@Override
	public LabelDetails editLabel(LabelDetails labelDetails) {
	return (LabelDetails) entity.unwrap(Session.class).createQuery("update LabelDetails label set label.labelName='"+labelDetails.getLabelName()+"'").uniqueResult();
	}


	@Override
	public LabelDetails findLabelById(long labelId) {
		
	     Session session = entity.unwrap(Session.class);
	  	Query query = session.createQuery("from LabelDetails where labelId='"+labelId+"'");
	  	System.out.println(query);
	  	//query.setParameter("userId", userId);	
	  	return  (LabelDetails) query.uniqueResult();   
	      
	      
	      
	}

	@Override
	public List<LabelDetails> fetchAllLabelById(long id)
	{
		Session session= entity.unwrap(Session.class);
		return session.createQuery("from LabelDetails where user_id='"+id+"'").getResultList();		 
	}


	@Override
	public int updateLabelById(long labelId, LabelDetails labelDetails) {
		return  entity.unwrap(Session.class).createQuery("update LabelDetails label set label.labelName='"+labelDetails.getLabelName()+"' where label.labelId='"+labelId+"'").executeUpdate();
		
	}



	
  
}
