package com.synthesis.repository;

import org.springframework.data.repository.CrudRepository;

import com.synthesis.entity.User;


public interface UserRepository extends CrudRepository<User, Integer>{

	User findByUserIdInAndPaswd(String userName,String password);

	User findByUserId(String userId);


	

	
}
