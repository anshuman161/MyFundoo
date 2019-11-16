package com.bridgelabz.fundooproject.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundooproject.exception.UserException;
import com.bridgelabz.fundooproject.model.UserDto;
import com.bridgelabz.fundooproject.model.UserInformation;
import com.bridgelabz.fundooproject.model.UserLogIn;
import com.bridgelabz.fundooproject.model.UserResetPasswordDto;
import com.bridgelabz.fundooproject.repository.UserRepositry;
import com.bridgelabz.fundooproject.utilmethods.Utility;

@Service
public class UserServieImpl implements UserService 
{
	@Autowired
	private UserRepositry user;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	private Utility util;

	@Autowired
	private ModelMapper mapper;
    
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	
	@Transactional
	@Override
	public void save(UserDto userDto) 
	{
	
		UserInformation userinformation = user.getUser(userDto.getEmail());
		
		if (userinformation != null) 
		{
			 throw new UserException("User Already exist");
		}
		else 
		{
			
			 
				UserInformation information = mapper.map(userDto, UserInformation.class);
				String newPassword = bcrypt.encode(information.getPassword());
				information.setPassword(newPassword);
				information.setVerified(false);
				user.save(information);
				String tokens = util.generateTokens(information.getUserId());
				util.sendMail(userDto.getEmail(), "Email verifying", "http://localhost:8080/verify/" + tokens);
		}
	}
	@SuppressWarnings("unused")
	@Transactional
	@Override
	public void isVerify(String token) 
	{
		Long id = (long) util.parseToken(token);
		   UserInformation userInfo =user.getUserById(id);
		if (userInfo != null) 
		{
			userInfo.setVerified(true);
			user.save(userInfo);
			//user.saveVerfied(id);	
		} 
		else 
		{
			 throw new UserException("token verification failed");
		}
	}
	@Transactional
	@Override
	public String getLogIn(UserLogIn login) {
		UserInformation userInfo = user.getUser(login.getEmail());
		String token=null;
		if (userInfo.isVerified()) {
			if (bcrypt.matches(login.getPassword(), userInfo.getPassword()) ) 
			{
				System.out.println("inside login====");
			   	token=util.generateTokens(userInfo.getUserId());
			   	redisTemplate.opsForValue().set("token", token);
			    redisTemplate.opsForValue().get(token);
			   	System.out.println("redisinfo-------------"+redisTemplate.opsForValue().get(token));
			   	System.out.println("   //////id/////////   "+token);
			   	return token;
			}
			else 
			{
				 throw new UserException("Password is wrong!!!");
			}
		}
		else 
		{
			 throw new UserException("verification is pending!!!");
		}

	}
	@Transactional
	@Override
	public boolean forgetPassword(String email) {
		UserInformation info = user.getUser(email);
		if (info != null) {
			util.sendMail(info.getEmail(), "Reset Password","http://localhost:3001/reset/" + util.generateTokens(info.getUserId()));
			return true;
		} else {
			throw new UserException("User Not Exist");
		}
	}
	@Transactional
	@Override
	public boolean resetPassword(UserResetPasswordDto password, String token)
	{         
		if (password.getPassword().equals(password.getConfirmPassword())) 
		{
			long userId =(long) util.parseToken(token);
			String pass=password.getConfirmPassword();
			System.out.println(bcrypt.encode(pass));
			password.setConfirmPassword(bcrypt.encode(pass));
			user.changePassword(password, userId);
			return true;
		} 
		else
		{
			throw new UserException("Password and Confirm password are not matched.");
		}
	}

	

}





//} else {
//util.sendMail(login.getEmail(), "Email verifying", "http://localhost:8080/user/verify/"+util.generateTokens(userInfo.getUserId()));
// throw new UserException("User Not Exist");
//}
