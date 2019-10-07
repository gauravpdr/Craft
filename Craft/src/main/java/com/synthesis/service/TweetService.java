package com.synthesis.service;

import java.util.List;

import com.synthesis.entity.Tweet;
import com.synthesis.entity.User;

public interface TweetService {

	public Tweet postTweet(Tweet tweet);

	public List<Tweet> getTweetsList(User user);

	public String deleteTweet(int tweetId);

	List<Tweet> getFeedTweets(User user);

	

}
