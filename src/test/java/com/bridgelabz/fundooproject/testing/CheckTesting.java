package com.bridgelabz.fundooproject.testing;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.fundooproject.model.NoteDetails;
import com.bridgelabz.fundooproject.model.UserInformation;
import com.bridgelabz.fundooproject.model.UserLogIn;
import com.bridgelabz.fundooproject.repository.NoteRepositryImpl;
import com.bridgelabz.fundooproject.repository.UserRepositry;
import com.bridgelabz.fundooproject.service.NoteServiceImpl;
import com.bridgelabz.fundooproject.service.UserServieImpl;
import com.bridgelabz.fundooproject.service.colabService;
import com.bridgelabz.fundooproject.utilmethods.Response;
import com.bridgelabz.fundooproject.utilmethods.Utility;

@RunWith(MockitoJUnitRunner.class)
public class CheckTesting {
@InjectMocks
private NoteServiceImpl noteservice;

@Mock
private NoteRepositryImpl noteRepository;
@Mock
private UserRepositry userRepository;
@Mock
private UserServieImpl userservie;

@Mock
private colabService colabService;

@Mock
private Utility utils;

@Test
public void retriveUserFromDatabaseTest() {
String token="uioi";
	noteservice.fetchAllNotes(token);
	

List<NoteDetails> details =new ArrayList<NoteDetails>();
NoteDetails notedetails= new NoteDetails();
notedetails.setDescription("mi");
details.add(notedetails);

when(noteRepository.fetchNotesByUserId(utils.parseToken(token))).thenReturn(details);
when(colabService.getCollaboratedNoteList(utils.parseToken(token))).thenReturn(details);

    assertEquals(2, noteservice.fetchAllNotes(token).size());
}


@Test
public void loginTest() {

String email="anshumanjoshi161@gmail.com";
List<UserInformation> listofusers=new ArrayList<UserInformation>();
UserInformation user =new UserInformation();
user.setEmail("anshumanjoshi161@gmail.com");
listofusers.add(user);
when(userRepository.getListOfUser(email)).thenReturn(listofusers);
List<UserInformation> excepteddata=userRepository.getListOfUser(email);
assertEquals(excepteddata,listofusers); 
assertEquals(1, excepteddata.size());
}








//    @Test
//    public void testLoginUserSuccess() throws URISyntaxException
//    {
//        RestTemplate restTemplate = new RestTemplate();
//        final String baseUrl = "http://localhost:+8085/user/login";
//        URI uri = new URI(baseUrl);
//        UserLogIn user = new UserLogIn( "anshumanjoshi161@gmail.com","1234");
//         
////        HttpHeaders headers = new HttpHeaders();
////        headers.set("X-COM-PERSIST", "true");    
// 
//        HttpEntity<UserLogIn> request = new HttpEntity<>(user);
//        try
//        {
//        ResponseEntity<Response> response = restTemplate.exchange(uri, HttpMethod.POST,request, Response.class);
//         assertEquals(201,response.getStatusCode());
//        }
//        catch(HttpClientErrorException ex)
//        {
//            //Verify bad request and missing header
//            Assert.assertEquals(400, ex.getRawStatusCode());
//            Assert.assertEquals(true, ex.getResponseBodyAsString().contains("Missing request header"));
//        }
//    }
}
