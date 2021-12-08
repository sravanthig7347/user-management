package com.neosoft.user.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.neosoft.user.model.UserRecord;

@RepositoryRestResource(collectionResourceRel = "usermanagement", path = "usermanagement")
public interface UserRepository  extends PagingAndSortingRepository<UserRecord, Long>{
	
}
