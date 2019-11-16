package com.bridgelabz.fundooproject.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundooproject.model.NoteDetails;
import com.bridgelabz.fundooproject.model.UserInformation;

@Repository
public class NoteRepositryImpl implements Note 
{
	@Autowired
	private EntityManager entity;
 	
	@Override
	public void save(NoteDetails details) 
	{
		Session session = entity.unwrap(Session.class);
		session.saveOrUpdate(details);
	}
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
	public void updateNotes(long noteId, NoteDetails details) 
	{
		Session session = entity.unwrap(Session.class);
		session.saveOrUpdate(details);
	}
	@Override
	public NoteDetails findNoteById(long id) {
		Session session = entity.unwrap(Session.class);
		Query query = session.createQuery("from NoteDetails where note_id=:id");
		query.setParameter("id", id);
		return  (NoteDetails) query.uniqueResult();
	}
	@Override
	public int deleteNotes(long id) 
	{
		Session session = entity.unwrap(Session.class);
		Query query = session.createQuery("delete from NoteDetails n where n.noteId=:id");
		query.setParameter("id", id);
		return  query.executeUpdate();
		
	}
	@Override
	public List<NoteDetails> fetchNotesByUserId(long id) 
	{
		Session session= entity.unwrap(Session.class);
		Query query= session.createQuery("from NoteDetails where user_id=:id and is_trash=:false and is_archieve=:false");
		 query.setParameter("id", id);
		 query.setParameter("false", false);
		 query.setParameter("false", false);
		 List<NoteDetails> list= query.getResultList();
		 return list;
	 }
	
	@Override
	public List<NoteDetails> fetchNotesByTrash(long id) {
		Session session= entity.unwrap(Session.class);
		Query query= session.createQuery("from NoteDetails where user_id=:id and is_trash=:true ");	
		query.setParameter("id", id);
		query.setParameter("true", true);
		List<NoteDetails> noteList=query.getResultList();
		return noteList;
	}
	@Override
	public List<NoteDetails> fetchArchiveNotes(long id) {
		Session session= entity.unwrap(Session.class);
		Query query= session.createQuery("from NoteDetails where user_id=:id and is_archieve=:true and is_trash=:false");	
		query.setParameter("id", id);
		query.setParameter("true", true);
		query.setParameter("false", false);
		List<NoteDetails> noteList=query.getResultList();
		return noteList;
	}

	@Override
	@Transactional
	public void saveColab(Note colab) {
		Session currentSession = entity.unwrap(Session.class);
		currentSession.save(colab);
		System.out.println("note saved");
	}
	/*
	 * @Override public int sortingNotes(Long id) { Session session =
	 * entity.unwrap(Session.class);
	 * // query.setParameter("id", id); return 0;//query.executeUpdate();
	 * }
	 */

}
