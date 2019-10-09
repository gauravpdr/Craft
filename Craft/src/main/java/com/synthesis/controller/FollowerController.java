package com.synthesis.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synthesis.entity.Follower;
import com.synthesis.entity.User;
import com.synthesis.exception.FollowingNotFoundException;
import com.synthesis.exception.UserNotFoundException;
import com.synthesis.service.FollowerService;

/**
 * @author Gaurav Pidyar Controller class for handling all the requests related
 *         to modification of the following list of a follower or user
 */

@RequestMapping("/followers")
@RestController
public class FollowerController extends BaseController {

	@Autowired
	FollowerService followerService;

	@PutMapping(value = "/follower", produces = "application/json")
	public ResponseEntity<String> addToFollowingList(@Valid @RequestBody Follower follower,
			HttpServletRequest request) {

		User validatedUser = validateUser(request);
		if (null != validatedUser) {

			Follower updatedfollower = followerService.updateFollower(follower);
			return new ResponseEntity<String>(follower.getFollower().getUserId() + " is now following "
					+ updatedfollower.getFollowed().getUserId(), HttpStatus.OK);
		} else {
			throw new UserNotFoundException("Invalid User !! Please contact System Administrator");
		}

	}

	@GetMapping("")
	public ResponseEntity<String> getFollowingList(HttpServletRequest request) {

		List<String> followingList = null;
		User user = validateUser(request);
		if (null != user) {
			List<Follower> list = followerService.getListOfFollowers(user);
			if (null != list && list.size() > 0) {
				followingList = new ArrayList<String>();
				for (Follower follower : list) {
					followingList.add(follower.getFollower().getUserId());
				}

				return new ResponseEntity<String>(user.getUserId() + " is followed by " + followingList, HttpStatus.OK);
			} else
				throw new FollowingNotFoundException("No Follower exist for User " + user.getUserId());

		}
		throw new UserNotFoundException("Invalid User !! Please contact System Administrator");

	}

}
