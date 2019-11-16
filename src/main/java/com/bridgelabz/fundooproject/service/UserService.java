package com.bridgelabz.fundooproject.service;

import com.bridgelabz.fundooproject.model.UserDto;
import com.bridgelabz.fundooproject.model.UserLogIn;
import com.bridgelabz.fundooproject.model.UserResetPasswordDto;

public interface UserService 
{
	public void save(UserDto student);

	public String getLogIn(UserLogIn login);

	public void isVerify(String token);

	public boolean forgetPassword(String email);

	public boolean resetPassword(UserResetPasswordDto password,String token);
	
}
