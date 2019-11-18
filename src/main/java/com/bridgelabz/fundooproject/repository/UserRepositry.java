package com.bridgelabz.fundooproject.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundooproject.model.UserInformation;
import com.bridgelabz.fundooproject.model.UserResetPasswordDto;

@Repository
public class UserRepositry implements User
{
	@Autowired
	private EntityManager entityManager;
	@Override
	public UserInformation getUser(String email) 
	{
		Session currentsession= entityManager.unwrap(Session.class);
		Query query = currentsession.createQuery("from UserInformation where email=:email");
		query.setParameter("email", email);
		return (UserInformation) query.uniqueResult();          
	}
	@Override
	public List<UserInformation> getListOfUser(String email) 
	{
		Session currentsession= entityManager.unwrap(Session.class);
		Query query = currentsession.createQuery("from UserInformation where email=:email");
		query.setParameter("email", email);
		return query.getResultList();          
	}
	
	
	@Override
	public void save(UserInformation userinfo) 
	{
		 entityManager.unwrap(Session.class).save(userinfo);
	}
	
	public boolean saveVerfied(long id) 
	{
		
		Session session= entityManager.unwrap(Session.class);
		Query query=session.createQuery("update UserInformation set isVerified=:'"+true+"'+' where userId=:id");
		query.setParameter("id", id);
		int demo=query.executeUpdate();
		return(demo>0)?true:false;
		
	}
	@Override
	public boolean changePassword(UserResetPasswordDto password, Long userId)
	{
		Session session = entityManager.unwrap(Session.class);
        Query query=session.createQuery("update UserInformation set password=:password where userId=:userId");
		query.setParameter("password", password.getConfirmPassword());
		query.setParameter("userId", userId);
		int demo=query.executeUpdate();
        return(demo>0)?true:false;
	}
	public UserInformation getUserById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("from UserInformation where userId=:userId");
		query.setParameter("userId", id);
		return  (UserInformation) query.uniqueResult();
	}
	
	@Override
	public List<UserInformation> getListOfUsers() {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<UserInformation> query = currentSession.createQuery("from UserInformation", UserInformation.class);
		List<UserInformation> list = query.getResultList();
		
		return list;
	}

	

}
