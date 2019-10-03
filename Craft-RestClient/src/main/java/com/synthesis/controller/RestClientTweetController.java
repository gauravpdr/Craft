package com.synthesis.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.synthesis.model.Tweet;

@RequestMapping("/Craft/tweets")
@RestController
public class RestClientTweetController {

	@Autowired
	RestTemplate restTemplate;

	// Tweet URI's
	private static final String URI_DELETE_TWEET = "http://localhost:8080/tweets/";
	private static final String URI_CREATE_TWEET = "http://localhost:8080/tweets/postTweet";
	private static final String URI_GET_TWEETS = "http://localhost:8080/tweets";
	private static final String URI_TWEET_FEED = "http://localhost:8080/tweets/feed";

	@DeleteMapping("/{id}")
	public String deleteTweet(@PathVariable("id") int id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Tweet> entity = new HttpEntity<Tweet>(headers);
		try {
			return restTemplate.exchange(URI_DELETE_TWEET + id, HttpMethod.DELETE, entity, String.class).getBody();
		} catch (HttpStatusCodeException e) {

			return e.getResponseBodyAsString();

		}
	}

	@PostMapping("/postTweet")
	public String postTweet(@RequestBody Tweet tweet, HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", request.getHeader("Authorization"));
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Tweet> entity = new HttpEntity<Tweet>(tweet, headers);
		try {
			return restTemplate.exchange(URI_CREATE_TWEET, HttpMethod.POST, entity, String.class).getBody();
		} catch (HttpStatusCodeException e) {
			return e.getResponseBodyAsString();

		}
	}

	@GetMapping("")
	public ResponseEntity<Object> getTweets(HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", request.getHeader("Authorization"));
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Tweet> entity = new HttpEntity<Tweet>(headers);
		try {
			return restTemplate.exchange(URI_GET_TWEETS, HttpMethod.GET, entity, Object.class);
		} catch (HttpStatusCodeException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity<Object>(e.getResponseBodyAsString(), HttpStatus.NOT_FOUND);
			else {
				return new ResponseEntity<Object>(e.getResponseBodyAsString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}
	}

	@GetMapping("/feed")
	public ResponseEntity<Object> getTweetFeed(HttpServletRequest request) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", request.getHeader("Authorization"));
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Tweet> entity = new HttpEntity<Tweet>(headers);
		try {
			return restTemplate.exchange(URI_TWEET_FEED, HttpMethod.GET, entity, Object.class);
		} catch (HttpStatusCodeException e) {
			if (e.getStatusCode() == HttpStatus.NOT_FOUND)
				return new ResponseEntity<Object>(e.getResponseBodyAsString(), HttpStatus.NOT_FOUND);
			else {
				return new ResponseEntity<Object>(e.getResponseBodyAsString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}

	}

}