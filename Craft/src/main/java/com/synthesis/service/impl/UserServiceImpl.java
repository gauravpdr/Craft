package com.synthesis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synthesis.entity.User;
import com.synthesis.repository.UserRepository;
import com.synthesis.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repository;
	
	@Override
	public User validateUser(String userName , String password) {
		
		return repository.findByUserIdInAndPaswd(userName, password);
		
		
	}
	
	

}
