package com.synthesis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synthesis.entity.Follower;
import com.synthesis.entity.Tweet;
import com.synthesis.entity.User;
import com.synthesis.repository.TweetRepository;
import com.synthesis.service.FollowerService;
import com.synthesis.service.TweetService;

@Service
public class TweetServiceImpl implements TweetService {

	@Autowired
	TweetRepository repository;

	@Autowired
	FollowerService followerService;

	@Override
	public Tweet postTweet(Tweet tweet) {

		return repository.save(tweet);

	}

	@Override
	public List<Tweet> getTweetsList(User user) {
		return repository.fetchTweets(user);
	}

	@Override
	public String deleteTweet(int id) {
		Tweet t = new Tweet();
		t.setTweetId(id);
		repository.deleteById(id);
		System.out.println("Tweet"+id+"deleted successfully");
		return "Tweet"+id+"deleted successfully";

	}

	@Override
	public List<Tweet> getFeedTweets(User user) {

		List<Follower> listOfFollowing = followerService.getListOfFollowing(user);
		if (null!=listOfFollowing) {
			ArrayList<User> listOfUserFollowing = new ArrayList<User>();
			for(Follower tempUserId : listOfFollowing)
			{
				
				listOfUserFollowing.add(tempUserId.getFollowed());
				
			}
			
			
					
			return repository.findFirst100ByUserInOrderByCreatedDateDesc(listOfUserFollowing);
		}

		else {
			return null;
		}

	}

}
