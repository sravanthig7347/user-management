package com.neosoft.user.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neosoft.user.model.UserRecord;
import com.neosoft.user.service.UserService;

import lombok.NonNull;

@RestController
@RequestMapping("/usermanagement") 
public class UserController {
	
	@Autowired    
	private UserService userService;  
	
	
	@GetMapping("/getAllUsers") 
	public List<UserRecord> getAllUsers() { 
		  
		  return userService.getAllUsers();
	}
	
	
	@GetMapping("/getAllActiveUsers") 
	public List<UserRecord> getAllActiveUsers() { 
		  
		  return userService.getAllActiveUsers();
	}
	
	
	@GetMapping("/getUserById/{id}")
	public UserRecord getUser(@PathVariable Long id) {

		return userService.getUserById(id);

	}
	
	
	@GetMapping("/getAllUsersSorted") 
	public List<UserRecord> getAllUsersSorted(@RequestParam String sortBy ) { 
		  
		  return userService.getAllUsersSorted(sortBy);
	}
	
	 
	@PostMapping(value ="/addUser", consumes = {MediaType.APPLICATION_JSON_VALUE} , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserRecord> addUser(@RequestBody UserRecord userRecord)  
	{    

		UserRecord newUserRecord = userService.addUser(userRecord);
		return ResponseEntity
	            .created(URI
	                     .create(String.format("/getUserById/%s", userRecord.getId())))
	            .body(newUserRecord);
        
	} 
	
	
	@PutMapping(value ="/modifyUser", consumes = {MediaType.APPLICATION_JSON_VALUE} , produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserRecord> updateUser(@RequestBody UserRecord userRecord)  
	{    

		UserRecord updatedUserRecord = userService.modifyUser(userRecord);
		return ResponseEntity
	            .created(URI
	                     .create(String.format("/getUserById/%s", userRecord.getId())))
	            .body(updatedUserRecord);
        
	} 
	
	
	@DeleteMapping("/deleteUserById/{id}")
	public ResponseEntity<UserRecord> deleteUser(@PathVariable Long id) {

		UserRecord deletedUserRecord= userService.deleteById(id);
		return ResponseEntity
	            .created(URI
	                     .create(String.format("/getUserById/%s", deletedUserRecord.getId())))
	            .body(deletedUserRecord);

	} 
	
	// Searching user by fname or lname or pincode
	
	@GetMapping("/searchUser/{input}")
	public List<UserRecord> searchByNameOrPincode(@PathVariable String input){
		
		return userService.searchForUsers(input);
				
	}
}
