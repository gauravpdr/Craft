package com.synthesis.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.synthesis.model.Follower;

@RequestMapping("/Craft")
@RestController
public class RestClientFollowerController {

	@Autowired
	RestTemplate restTemplate;

	private static final String URI_ADD_FOLLOWING = "http://localhost:8080/followers/follower";
	private static final String URI_GET_FOLLOWER = "http://localhost:8080/followers";

	@PutMapping("/follow")
	public String addFollowing(@RequestBody Follower follower, HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", request.getHeader("Authorization"));
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Follower> entity = new HttpEntity<Follower>(follower, headers);
		try {
			return restTemplate.exchange(URI_ADD_FOLLOWING, HttpMethod.PUT, entity, String.class).getBody();
		} catch (HttpStatusCodeException e) {
			return e.getResponseBodyAsString();

		}
	}

	@GetMapping("/follower")
	public String getFollower(HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", request.getHeader("Authorization"));
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Follower> entity = new HttpEntity<Follower>(headers);
		try {
			return restTemplate.exchange(URI_GET_FOLLOWER, HttpMethod.GET, entity, String.class).getBody();
		} catch (HttpStatusCodeException e) {

			return e.getResponseBodyAsString();
		}

	}

}
