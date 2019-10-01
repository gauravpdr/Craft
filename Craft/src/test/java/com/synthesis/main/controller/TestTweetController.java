package com.synthesis.main.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.synthesis.bean.TweetBean;
import com.synthesis.controller.TweetController;
import com.synthesis.entity.Tweet;
import com.synthesis.entity.User;
import com.synthesis.repository.TweetRepository;
import com.synthesis.repository.UserRepository;
import com.synthesis.service.TweetService;
import com.synthesis.service.UserService;
import com.synthesis.service.impl.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = TweetController.class, secure = false)
public class TestTweetController {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TweetController tweetControllerMock;

	@Test
	public void testPostTweet() throws Exception {

		TweetBean mockTweet = new TweetBean();
		mockTweet.setTweetId(1);
		mockTweet.setTweetText("hello");
		MockHttpServletRequest mockHttpRequest = new MockHttpServletRequest();
		mockHttpRequest.addHeader("Authorization", "Basic VTE6MTIzNA==");

		// mockTweet.setCreatedBy("gaurav");

		String inputInJson = this.mapToJson(mockTweet);
		String URI = "/tweets/postTweet";

		Mockito.when(tweetControllerMock.postTweet(Mockito.any(Tweet.class), Mockito.any(MockHttpServletRequest.class)))
				.thenReturn(new ResponseEntity<TweetBean>( mockTweet,HttpStatus.OK));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputInJson).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Basic VTE6MTIzNA==");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	public void getTweetsList() throws Exception {

		Tweet mockTweet = new Tweet();
		mockTweet.setTweetId(1);
		mockTweet.setTweetText("hello");

		List list = new ArrayList();
		list.add(mockTweet);

		MockHttpServletRequest mockHttpRequest = new MockHttpServletRequest();
		mockHttpRequest.addHeader("Authorization", "Basic VTE6MTIzNA==");

		// mockTweet.setCreatedBy("gaurav");

		String inputInJson = this.mapToJson(list);
		String URI = "/tweets";

		Mockito.when(tweetControllerMock.getTweetsList(Mockito.any(MockHttpServletRequest.class)))
				.thenReturn(new ResponseEntity<Object>(list, HttpStatus.OK));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic VTE6MTIzNA==");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}
	
	@Test
	public void performDeleteTweet() throws Exception {

		String inputString = "Tweet 1 deleted successfully";
		

		MockHttpServletRequest mockHttpRequest = new MockHttpServletRequest();
		mockHttpRequest.addHeader("Authorization", "Basic VTE6MTIzNA==");

		// mockTweet.setCreatedBy("gaurav");

		
		String URI = "/tweets/1";

		Mockito.when(tweetControllerMock.deleteTweet(1))
				.thenReturn(new ResponseEntity<Object>("Tweet "+1+" deleted successfully", HttpStatus.OK));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertThat(outputInJson).isEqualTo(inputString);
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	 @Test
	 public void testFeedTweets() throws Exception
	 {
		 //User U3
		 User user1 = new User();
		 user1.setUserId("U3");
		 
		 // User U4
		 User user2 = new User();
		 user2.setUserId("U4");
		 
		 Tweet mockTweet1 = new Tweet();
		 mockTweet1.setTweetId(1);
		 mockTweet1.setTweetText("helloU3");
		 mockTweet1.setUser(user1);
		 Calendar cal = Calendar.getInstance(); // creates calendar
		 cal.setTime(new Date()); // sets calendar time/date
		 cal.add(Calendar.HOUR_OF_DAY, -1); // adds one hour
		  cal.getTime();
		mockTweet1.setCreatedDate(cal.getTime());
		 
		 Tweet mockTweet2 = new Tweet();
		 mockTweet2.setTweetId(2);
		 mockTweet2.setTweetText("helloU4");
		 mockTweet2.setUser(user2);
		 mockTweet2.setCreatedDate(new Date());
		  
		 List<Tweet> list = new ArrayList<Tweet>();
		 list.add(mockTweet1);
		 list.add(mockTweet2);
		 
		 MockHttpServletRequest mockHttpRequest = new MockHttpServletRequest();
		 mockHttpRequest.addHeader("Authorization", "Basic VTE6MTIzNA==");
		 
		 String inputInJson = this.mapToJson(list);
		 String URI = "/tweets/feed";
		 
		/*
		 * Mockito.when(tweetControllerMock.getFeedTweets(Mockito.any(
		 * MockHttpServletRequest.class))) .thenReturn(new
		 * ResponseEntity<List<Tweet>>(list, HttpStatus.OK));
		 */

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Basic VTE6MTIzNA==");
		
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

}
