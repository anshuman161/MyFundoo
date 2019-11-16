package com.bridgelabz.fundooproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundooproject.model.UserDto;
import com.bridgelabz.fundooproject.model.UserInformation;
import com.bridgelabz.fundooproject.model.UserLogIn;
import com.bridgelabz.fundooproject.model.UserResetPasswordDto;
import com.bridgelabz.fundooproject.service.UserService;
import com.bridgelabz.fundooproject.utilmethods.Response;
import com.bridgelabz.fundooproject.utilmethods.Utility;

@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = {"jwtToken"})
@RestController
//@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService service;

	@Autowired
	Utility util;

	@PostMapping("/goRegister")
	public ResponseEntity<Response> doRegister(@RequestBody UserDto details) {
		service.save(details);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Registration success", 200, details));

	}

	@PostMapping("/goLogin")
	public ResponseEntity<Response> doLogIn(@RequestBody UserLogIn user) {
		String token = service.getLogIn(user);
		return ResponseEntity.status(HttpStatus.OK).body(new Response(token, 200, HttpStatus.OK.value()));

	}

	@GetMapping("/verify/{token}")
	public ResponseEntity<Response> verification(@PathVariable ("token") String token) {
		System.out.println("111111111111");
		service.isVerify(token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Verification Done", 200, token));

	}

	@PostMapping("/forgetPassword")
	public ResponseEntity<Response> forgetPassword(@RequestBody UserDto userEmail) 
	{
		System.out.println("inside forget");
		String email=userEmail.getEmail();
		boolean getResponse = service.forgetPassword(email);
		if (getResponse) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Email Send Succeffull", 200, email));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Email Sending Failed", 400, email));
		}

	}

	@PostMapping("/reset")
	public ResponseEntity<Response> doResetPassword(@RequestHeader String token,
			@RequestBody UserResetPasswordDto userPasswordDto) {
		boolean getResponse = service.resetPassword(userPasswordDto, token);
		if (getResponse) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new Response("Password Reset done", 200, token));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Response("Reset Passsword Failed", 400, token));
		}

	}
	
	
}
