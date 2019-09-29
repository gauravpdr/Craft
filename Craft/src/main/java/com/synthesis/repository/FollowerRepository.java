package com.synthesis.repository;



import org.springframework.data.repository.CrudRepository;

import com.synthesis.entity.Follower;
import com.synthesis.entity.User;

public interface FollowerRepository extends CrudRepository<Follower, Integer> {

	Follower findByFollowerName(User followerName);

	//public Follower findByName(String userName);

}
