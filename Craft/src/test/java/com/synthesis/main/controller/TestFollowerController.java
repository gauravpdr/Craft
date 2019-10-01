package com.synthesis.main.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synthesis.controller.FollowerController;
import com.synthesis.entity.Follower;
import com.synthesis.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = FollowerController.class, secure = false)
public class TestFollowerController {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FollowerController followerControllerMock;

	@Test
	public void testAddToFollowingList() throws Exception {

		User followerUser = new User();
		followerUser.setUserId("U1");
		
		User followingUser = new User();
		followingUser.setUserId("U3");
		
		Follower mockFollower = new Follower();
		mockFollower.setFollower(followerUser);
		mockFollower.setFollowed(followingUser);
		MockHttpServletRequest mockHttpRequest = new MockHttpServletRequest();
		mockHttpRequest.addHeader("Authorization", "Basic VTE6MTIzNA==");

		

		String inputInJson = this.mapToJson(mockFollower);
		String URI = "/followers/follower";

		
		  Mockito.when(followerControllerMock.addToFollowingList(Mockito.any(Follower.
		  class), Mockito.any(MockHttpServletRequest.class))) .thenReturn(new
		  ResponseEntity<Follower>(mockFollower, HttpStatus.OK));
		 

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic VTE6MTIzNA==");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	public void testGetFollowingList() throws Exception {

		User followerUser = new User();
		followerUser.setUserId("U1");
		
		User followingUser = new User();
		followingUser.setUserId("U3");
		Follower mockFollower = new Follower();
		mockFollower.setFollower(followerUser);
		mockFollower.setFollowed(followingUser);
		MockHttpServletRequest mockHttpRequest = new MockHttpServletRequest();
		mockHttpRequest.addHeader("Authorization", "Basic VTE6MTIzNA==");

		// mockTweet.setCreatedBy("gaurav");

		List<String> list = new ArrayList<String>();
		list.add("U3");
		String inputInJson = this.mapToJson(list);
		String URI = "/followers";

		Mockito.when(followerControllerMock.getFollowingList(Mockito.any(MockHttpServletRequest.class)))
				.thenReturn(new ResponseEntity<List<String>>(list, HttpStatus.OK));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).contentType(MediaType.APPLICATION_JSON)
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

		return objectMapper.writeValueAsString(object);
	}

}
