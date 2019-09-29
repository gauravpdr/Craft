package com.synthesis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void deleteTweet(int id) {
		Tweet t = new Tweet();
		t.setTweetId(id);
		repository.deleteById(id);

	}

	@Override
	public List<Tweet> getFeedTweets(User user) {

		List<String> listOfFollowing = followerService.getListOfFollowing(user);
		if (null!=listOfFollowing) {
			ArrayList<User> listOfUserFollowing = new ArrayList<User>();
			for(String userId : listOfFollowing)
			{
				User tempUser = new User();
				tempUser.setUserId(userId);
				listOfUserFollowing.add(tempUser);
				
			}
			
			
					
			return repository.findFirst100ByUserIdInOrderByCreatedDateDesc(listOfUserFollowing);
		}

		else {
			return null;
		}

	}

}
