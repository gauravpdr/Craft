package com.synthesis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synthesis.entity.Follower;
import com.synthesis.entity.User;
import com.synthesis.repository.FollowerRepository;
import com.synthesis.repository.UserRepository;
import com.synthesis.service.FollowerService;

@Service
public class FollowerServiceImpl implements FollowerService {

	@Autowired
	FollowerRepository repository;
	@Autowired
	UserRepository userrepository;

	@Override
	public Follower updateFollower(Follower follower) {		
		
		return repository.save(follower);

	}

	
	  @Override
	public List<Follower> getListOfFollowing(User followerName) {

		  return repository.findByFollower(followerName);
		
	}
	  
	  @Override
	public List<Follower> getListOfFollowers(User followerName) {

			  return repository.findByFollowed(followerName);
			
	}
	 
}
