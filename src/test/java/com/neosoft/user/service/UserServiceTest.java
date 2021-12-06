package com.neosoft.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.mockito.Mockito.anyLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import com.neosoft.user.model.UserRecord;
import com.neosoft.user.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc 
@SpringBootTest
class UserServiceTest {

	@InjectMocks
	UserService userService;
	
	@Mock
	UserRepository userRepository;
	
	@Autowired
	MockMvc mockMvc;

	@Autowired
    private WebApplicationContext webApplicationContext;
	
	RequestBuilder requestBuilder;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	
	}

	String exampleUserJson = "{\"id\" : 1 , \"fistName\" : \"Sravanthi\",		\"lastName\" : \"Gangalapudi\",			\"dateOfJoining\" : \"30-11-2021\",			\"dateOfBirth\" : \"20-06-1989\",			\"email\" : \"sravanthi.gangalapudi@neosoftmail.com\",			\"pinCode\" : 524132		}";
	String updatedUserJson = "{\"id\" : 1 , \"fistName\" : \"Sravanthi Reddy\",		\"lastName\" : \"Gangalapudi\",			\"dateOfJoining\" : \"30-11-2021\",			\"dateOfBirth\" : \"20-06-1989\",			\"email\" : \"sravanthi.gangalapudi@neosoftmail.com\",			\"pinCode\" : 524132		}";

	
	@Test
	void testGetUserById() {
		UserRecord userRecord = new UserRecord();
		userRecord.setId(1L);
		userRecord.setFistName("Sravanthi");
		userRecord.setLastName("Gangalapudi");
		Optional<UserRecord> mockUser = Optional.of(userRecord);
		when(userRepository.findById(anyLong())).thenReturn(mockUser);
		
		UserRecord gotUserByID = userService.getUserById(1L);
		
		assertNotNull(gotUserByID);
		assertEquals("Sravanthi", gotUserByID.getFistName());
	}
	
	@Test
	void testGetUserById_ThrowsUserNotFoundException() {

		when(userRepository.findById(anyLong())).thenReturn(null);
		assertThrows(NullPointerException.class, 
			()-> {
					userService.getUserById(1L);
				}
				
		);
		
	}
	
	@Test
	void testAddUser() throws Exception {
 
		addUsersForTest();
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

	}

	
	@Test
	void testModifyUser() throws Exception {

		RequestBuilder requestBuilderM = MockMvcRequestBuilders
				.put("/usermanagement/modifyUser")
				.accept(MediaType.APPLICATION_JSON).content(updatedUserJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilderM).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertTrue( response.getContentAsString().contains("Sravanthi Reddy"));

	}
	
	@Test
	void testDeleteUser() throws Exception {

		RequestBuilder requestBuilderD = MockMvcRequestBuilders
				.delete("/usermanagement/deleteUserById/1");

		MvcResult result = mockMvc.perform(requestBuilderD).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertTrue( response.getContentAsString().contains("Sravanthi Reddy"));

	}
	
	void addUsersForTest() {
		requestBuilder = MockMvcRequestBuilders
				.post("/usermanagement/addUser")
				.accept(MediaType.APPLICATION_JSON).content(exampleUserJson)
				.contentType(MediaType.APPLICATION_JSON);
	}

}
