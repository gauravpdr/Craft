package com.synthesis.repository;



import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.synthesis.entity.Follower;
import com.synthesis.entity.User;

public interface FollowerRepository extends CrudRepository<Follower, Integer> {

	List<Follower> findByFollower(User follower);


}
