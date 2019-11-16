package com.bridgelabz.fundooproject.utilmethods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class Utility 
{
	@Autowired
	private JavaMailSender javaMailSender;
	
	private static final String secret = "qwertyuioplkjhgfdsazxcvbnm123654789";

	public String generateTokens(long lid) 
	{
		String token = null;

		token = JWT.create().withClaim("userId", lid).sign(Algorithm.HMAC512(secret));
		return token;
	}
	public Long parseToken(String token) 
	{ 
		Long userId =0L;
		if (token != null) 
		{
			userId = JWT.require(Algorithm.HMAC512(secret)).build().verify(token).getClaim("userId").asLong();
		}
		return userId;
	}	
	public void sendMail(String to,String subject,String text)
	{
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);
		msg.setSubject(subject);
		msg.setText(text);
		javaMailSender.send(msg);	
	}
}
