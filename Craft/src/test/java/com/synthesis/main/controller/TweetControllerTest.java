package com.synthesis.main.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synthesis.controller.TweetController;
import com.synthesis.entity.Tweet;
import com.synthesis.service.TweetService;

@RunWith(SpringRunner.class)
@WebMvcTest (value=TweetController.class ,secure = false )
public class TweetControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TweetService tweetService;
	
	@Test
	public void testPostTweet() throws Exception
	{
		Tweet mockTweet = new Tweet();
		mockTweet.setTweetId(1);
		mockTweet.setTweetText("hello");
		//mockTweet.setCreatedBy("gaurav");
		
		String inputInJson = this.mapToJson(mockTweet);
		String URI = "/tweets/postTweet";
		
		Mockito.when(tweetService.postTweet(Mockito.any(Tweet.class))).thenReturn(mockTweet);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);

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
		
		return objectMapper.writeValueAsString(object);
	}
	

}
