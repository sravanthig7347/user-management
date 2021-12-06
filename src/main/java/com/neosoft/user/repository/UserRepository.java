package com.neosoft.user.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.neosoft.user.model.UserRecord;
@Repository
public interface UserRepository  extends PagingAndSortingRepository<UserRecord, Long>{
	
}
