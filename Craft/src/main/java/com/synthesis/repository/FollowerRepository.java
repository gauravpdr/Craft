package com.synthesis.repository;



import org.springframework.data.repository.CrudRepository;

import com.synthesis.entity.Follower;

public interface FollowerRepository extends CrudRepository<Follower, Integer> {

	Follower findByFollowerName(String followerName);

	//public Follower findByName(String userName);

}
