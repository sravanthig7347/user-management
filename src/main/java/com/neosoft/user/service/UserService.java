package com.neosoft.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.neosoft.user.exception.UserNotFoundException;
import com.neosoft.user.model.UserRecord;
import com.neosoft.user.repository.UserRepository;

import java.util.*;

	@Service
	public class UserService {
	
		@Autowired
		private UserRepository userrepository;
		
	 
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
				throw new UserNotFoundException(userRecord) ;
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
		
		public List<UserRecord> getAllUsersSorted(String sortBy)  
		{    
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
				return null;
			}
		
		} 
	
}
