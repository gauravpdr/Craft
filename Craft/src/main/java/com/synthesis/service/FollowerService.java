package com.synthesis.service;

import java.util.ArrayList;
import java.util.List;

import com.synthesis.entity.Follower;

public interface FollowerService {

	public Follower updateFollower(Follower follower);

	 public List<String> getListOfFollowing(String string); 

}
