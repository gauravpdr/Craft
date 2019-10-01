package com.synthesis.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.synthesis.entity.Tweet;
import com.synthesis.entity.User;

public interface TweetRepository extends CrudRepository<Tweet, Integer>{
	
	@Query("select t from Tweet t where t.user=:userId")
	List<Tweet> fetchTweets(@org.springframework.data.repository.query.Param("userId") User userId);

	List<Tweet> findFirst100ByUserInOrderByCreatedDateDesc(List<User> listOfFollowing);

}
