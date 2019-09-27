package com.synthesis.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synthesis.entity.Follower;
import com.synthesis.repository.FollowerRepository;
import com.synthesis.service.FollowerService;

@Service
public class FollowerServiceImpl implements FollowerService {

	@Autowired
	FollowerRepository repository;

	@Override
	public Follower updateFollower(Follower follower) {
		
		return repository.save(follower);

	}

	
	  @Override
	public List<String> getListOfFollowing(String followerName) {
		Follower follower = repository.findByFollowerName(followerName);
		if (null != follower) {
			List<String> followingNames = Arrays.asList(follower.getFollowing().split(","));
			return followingNames;
		} else {
			return null;
		}
	}
	 
}
