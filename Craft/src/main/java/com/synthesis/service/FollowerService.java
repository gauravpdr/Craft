package com.synthesis.service;

import java.util.List;

import com.synthesis.entity.Follower;
import com.synthesis.entity.User;

public interface FollowerService {

	public Follower  updateFollower(Follower follower);

	 public List<Follower> getListOfFollowing(User user); 

}
