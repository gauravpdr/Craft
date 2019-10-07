package com.synthesis.main.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synthesis.controller.FollowerController;
import com.synthesis.entity.Follower;
import com.synthesis.entity.User;
import com.synthesis.service.FollowerService;
import com.synthesis.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = FollowerController.class, secure = false)
public class TestFollowerController {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FollowerService followerService;
	
	@MockBean
	private UserService userService;

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
		String expectedJson = mockFollower.getFollower().getUserId() + " is now following "
				+ mockFollower.getFollowed().getUserId();
		
		String URI = "/followers/follower";

		when(userService.validateUser(any(), any())).thenReturn(followerUser);
		when(followerService.updateFollower(any(Follower.class))).thenReturn(mockFollower);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON).header("Authorization", "Basic VTE6MTIzNA==");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertThat(outputInJson).isEqualTo(expectedJson);
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
		

		List<Follower> list = new ArrayList<Follower>();
		list.add(mockFollower);
		
		List<String> listFollower = new ArrayList<String>();
		listFollower.add(mockFollower.getFollower().getUserId());
		String URI = "/followers";

		String expectedOutput=mockFollower.getFollowed().getUserId() +" is followed by "+listFollower;
		when(userService.validateUser(any(), any())).thenReturn(followingUser);
		when(followerService.getListOfFollowing(any())).thenReturn(list);
		 
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Basic VTM6MTIzNA==");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		String outputInJson = response.getContentAsString();

		assertThat(outputInJson).isEqualTo(expectedOutput);
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
