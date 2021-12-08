package com.neosoft.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.neosoft.user.model.UserRecord;
import com.neosoft.user.repository.UserRepository;
import com.neosoft.user.validators.RequestValidator;
import com.neosoft.user.exception.UserNotFoundException;

import java.util.*;

	@Service
	public class UserService {
	
		@Autowired
		private UserRepository userrepository;
		
		@Autowired
		private RequestValidator requestValidator;
	 
		public List<UserRecord> getAllUsers()  
		{    
			List<UserRecord> userRecords = new ArrayList<>();    
			userrepository.findAll().forEach(userRecords::add);    
			return userRecords;    
		}  
		
		public UserRecord addUser(UserRecord userRecord)  
		{    
			userrepository.save(userRecord);  
			return userRecord;
			
		}    
		
		public UserRecord getUserById(Long userRecord) {
			
			Optional<UserRecord> tempUserRecord = userrepository.findById(userRecord);
			if (tempUserRecord.isPresent()) {
				return tempUserRecord.get();
			} else {
				throw new UserNotFoundException("User Not Found with ID :"+ userRecord.toString());  
			}
		}
	
		public UserRecord modifyUser(UserRecord userRecord)  
		{    
			
			UserRecord updatedUserRecord = getUserById(userRecord.getId());
			updatedUserRecord.setFistName(userRecord.getFistName());
			updatedUserRecord.setLastName(userRecord.getLastName());
			updatedUserRecord.setUserStatus(userRecord.getUserStatus());
			updatedUserRecord.setDateOfJoining(userRecord.getDateOfJoining());
			updatedUserRecord.setDateOfBirth(userRecord.getDateOfBirth());
			updatedUserRecord.setEmail(userRecord.getEmail());
			updatedUserRecord.setPinCode(userRecord.getPinCode());
			userrepository.save(updatedUserRecord);  
			return updatedUserRecord;
			
		}

		public List<UserRecord> getAllActiveUsers() {
			
			List<UserRecord> userRecords = new ArrayList<>();  
			List<UserRecord> userActiveRecords = new ArrayList<>(); 
			userrepository.findAll().forEach(userRecords::add); 
			userRecords.stream().filter(user ->user.getUserStatus()==0).forEach(userActiveRecords::add);
			return userActiveRecords;   
		} 
		
		public List<UserRecord> getAllUsersSorted(String sortBy) {    
			List<UserRecord> userRecords = new ArrayList<>();    
			userrepository.findAll(Sort.by(Direction.ASC,sortBy)).forEach(userRecords::add);    
			return userRecords;    
		}

		public UserRecord deleteById(Long id) {
			Optional<UserRecord> userRecord = userrepository.findById(id);
			if (userRecord.isPresent()) {
				UserRecord deleteUser = userRecord.get();
				deleteUser.setUserStatus(1);
				userrepository.save(deleteUser);
				return deleteUser;
			} else {
				throw new UserNotFoundException("User Not Found to Delete with ID :"+ userRecord.toString());
			}
		
		}

		public List<UserRecord> searchForUsers(String searchString) {
			
			List<UserRecord> userRecords = new ArrayList<>();  

			if(requestValidator.isValidPincode(searchString)) {
				getAllUsers().stream().filter(user -> user.getPinCode() == Integer.valueOf(searchString))
					.forEach(userRecords::add);
			} else {
				getAllUsers().stream().filter(user -> user.getFistName().equals( searchString) 
						|| user.getLastName() .equals( searchString)).forEach(userRecords::add);
			}
			
			if (!userRecords.isEmpty())
				return userRecords;
			else 
				throw new UserNotFoundException("User Not Found with search string"+ searchString);
			
			
		}
		
		
	
}
