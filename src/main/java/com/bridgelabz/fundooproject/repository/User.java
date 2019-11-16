package com.bridgelabz.fundooproject.repository;

import java.util.List;

import com.bridgelabz.fundooproject.model.UserInformation;
import com.bridgelabz.fundooproject.model.UserResetPasswordDto;

public interface User 
{
	public void save(UserInformation student);

	public UserInformation getUser(String email);

	public boolean saveVerfied(long id);
	
	public boolean changePassword(UserResetPasswordDto password, Long userId);
    
	public List<UserInformation> getListOfUsers(); 
}
