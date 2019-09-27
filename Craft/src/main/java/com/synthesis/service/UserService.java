package com.synthesis.service;

import com.synthesis.entity.User;


public interface UserService {
	
	User validateUser(String userName , String password);

}
