package com.synthesis.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synthesis.bean.TweetBean;
import com.synthesis.entity.Tweet;
import com.synthesis.entity.User;
import com.synthesis.exception.TweetNotFoundException;
import com.synthesis.exception.UserNotFoundException;
import com.synthesis.service.TweetService;

@RestController
@RequestMapping("/tweets")
public class TweetController extends BaseController{

	@Autowired
	TweetService service;

	@PostMapping("/postTweet")
	public ResponseEntity<TweetBean> postTweet(@Valid @RequestBody Tweet tweet, HttpServletRequest request) {

		User user = validateUser(request);
	
        if(null!=user)
        {
         tweet.setUser(user);
		Tweet tweetDB = service.postTweet(tweet);
		TweetBean bean = new TweetBean();
		bean.setUserId(tweetDB.getUser().getUserId());
		bean.setTweetText(tweetDB.getTweetText());
		bean.setCreatedDate(tweetDB.getCreatedDate());
		
		return new ResponseEntity<TweetBean>(bean,HttpStatus.OK);
        }
        else
        	throw new UserNotFoundException("Invalid User Credentials !! Please contact System Administrator");
	}

	@GetMapping("")
	public ResponseEntity<Object> getTweetsList(HttpServletRequest request) {

		User user = validateUser(request);
		if (null != user) {
			List<Tweet> list = service.getTweetsList(user);
			if (null != list && !list.isEmpty()) {
				List<TweetBean> tweetBeanList = convertTweetEntityToBean(list);
				return new ResponseEntity<>(tweetBeanList, HttpStatus.OK);
			} else
				throw new TweetNotFoundException("No such tweet Found in System for user " + user.getUserId());
		} else {
			throw new UserNotFoundException("Invalid User !! Please contact System Administrator");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteTweet(@PathVariable("id") int tweetId) {
         
		service.deleteTweet(tweetId);
		return new ResponseEntity<Object>("Tweet "+tweetId+" deleted successfully", HttpStatus.OK);

	}

	@GetMapping("/feed")
	public ResponseEntity<List<TweetBean>> getFeedTweets(HttpServletRequest request) {

		User user = validateUser(request);
		if (null != user) {
			
			List<TweetBean> list = new ArrayList<TweetBean>();
			
			List<Tweet> latestTweets = service.getFeedTweets(user);
			if (null != latestTweets && latestTweets.size() > 0)
			{
				for(Tweet tweet : latestTweets) 
				{
					    TweetBean bean = new TweetBean();
					    bean.setTweetText(tweet.getTweetText());
					    bean.setCreatedDate(tweet.getCreatedDate());
					    bean.setUserId(tweet.getUser().getUserId());
					    list.add(bean);
					    
				}
				
				return new ResponseEntity<List<TweetBean>>(list, HttpStatus.OK);
			}
			else
				throw new TweetNotFoundException(
						"No such tweet/feed Found in System for user " + user.getUserId());
		} else {
			throw new UserNotFoundException("Invalid User !! Please contact System Administrator");
		}

	}
	
	private List<TweetBean> convertTweetEntityToBean(List<Tweet> inputList )
	{
		List<TweetBean> list = new ArrayList<TweetBean>();

		if (null != inputList && inputList.size() > 0) {
			for (Tweet tweet : inputList) {
				TweetBean bean = new TweetBean();
				bean.setTweetText(tweet.getTweetText());
				bean.setCreatedDate(tweet.getCreatedDate());
				bean.setUserId(tweet.getUser().getUserId());
				list.add(bean);

			}

		}
		return list;

	} 


	

}