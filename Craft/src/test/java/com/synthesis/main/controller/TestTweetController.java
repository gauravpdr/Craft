package com.synthesis.main.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.hibernate.internal.util.compare.ComparableComparator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.synthesis.controller.TweetController;
import com.synthesis.entity.Tweet;
import com.synthesis.entity.User;
import com.synthesis.service.TweetService;
import com.synthesis.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = TweetController.class, secure = false)
public class TestTweetController {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TweetService service;

	@MockBean
	private UserService userService;
	
	
	
	
	@Test
	public void testPostTweet() throws Exception {

		User user = new User();
		user.setUserId("U1");

		Tweet mockTweet = new Tweet();
		mockTweet.setTweetId(1);
		mockTweet.setTweetText("hello");
		mockTweet.setUser(user);
		MockHttpServletRequest mockHttpRequest = new MockHttpServletRequest();
		mockHttpRequest.addHeader("Authorization", "Basic VTE6MTIzNA==");

		String inputInJson = this.mapToJson(mockTweet);
		String URI = "/tweets/postTweet";

		String expectedJson = "Tweet is posted successfully by User U1";

		when(userService.validateUser(any(), any())).thenReturn(user);
		when(service.postTweet(Mockito.any(Tweet.class))).thenReturn(mockTweet);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Basic VTE6MTIzNA==");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertThat(outputInJson).isEqualTo(expectedJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	
	@Test
	public void getTweetsList() throws Exception {

		User user = new User();
		user.setUserId("U1");

		Tweet mockTweet = new Tweet();
		mockTweet.setTweetId(1);
		mockTweet.setTweetText("hello");
		mockTweet.setUser(user);

		List<Tweet> list = new ArrayList<Tweet>();
		list.add(mockTweet);

		String inputInJson = this.mapToJson(list);
		String URI = "/tweets";

		when(userService.validateUser(any(), any())).thenReturn(user);
		when(service.getTweetsList(user)).thenReturn(list);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic VTE6MTIzNA==");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void performDeleteTweet() throws Exception {

		String inputString = "Tweet 1 deleted successfully";

		MockHttpServletRequest mockHttpRequest = new MockHttpServletRequest();
		mockHttpRequest.addHeader("Authorization", "Basic VTE6MTIzNA==");

		String URI = "/tweets/1";

		when(service.deleteTweet(1)).thenReturn(inputString);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertThat(response.getContentAsString()).isEqualTo(inputString);
		assertEquals(HttpStatus.OK.value(), response.getStatus());

		verify(service).deleteTweet(1);

	}

	
	  @Test
	public void testFeedTweets() throws Exception {
		  
		User loggedInUser = new User();  
		loggedInUser.setUserId("U1");

		User user3 = new User();
		user3.setUserId("U3");

		User user4 = new User();
		user4.setUserId("U4");

		Tweet mockTweet1 = new Tweet();
		mockTweet1.setTweetId(1);
		mockTweet1.setTweetText("helloU3");
		mockTweet1.setUser(user3);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, -1);
		mockTweet1.setCreatedDate(cal.getTime());

		Tweet mockTweet2 = new Tweet();
		mockTweet2.setTweetId(2);
		mockTweet2.setTweetText("helloU4");
		mockTweet2.setUser(user4);
		cal.add(Calendar.HOUR_OF_DAY, +2);
		mockTweet2.setCreatedDate(cal.getTime());

		List<Tweet> list = new ArrayList<Tweet>();
		list.add(mockTweet1);
		list.add(mockTweet2);
		Collections.sort(list, new SortbyCreatedDateDesc());
		MockHttpServletRequest mockHttpRequest = new MockHttpServletRequest();
		mockHttpRequest.addHeader("Authorization", "Basic VTE6MTIzNA==");

		String inputInJson = this.mapToJson(list);

		String URI = "/tweets/feed";

		when(userService.validateUser(any(), any())).thenReturn(loggedInUser);
		when(service.getFeedTweets(loggedInUser)).
		   thenReturn(list);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic VTE6MTIzNA==");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	 

	/**
	 * Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
	 */
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return objectMapper.writeValueAsString(object);
	}
	
	private static class SortbyCreatedDateDesc implements Comparator<Tweet> 
	{ 
	    // Used for sorting in ascending order of 
	    // roll number 
	    public int compare(Tweet a, Tweet b) 
	    { 
	        if(a.getCreatedDate().after(b.getCreatedDate()))
	       	{
	            return -1;       	
	  		}
	        else if(a.getCreatedDate().before(b.getCreatedDate()))
	        {
	        	return 1;
	        }
	        else
	        {
	        	return 0;
	        }
	    } 
	} 

}
