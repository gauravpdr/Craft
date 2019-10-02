package com.synthesis.model;



public class Follower {
	
	private int id;
	private User follower;
	private User followed;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getFollower() {
		return follower;
	}
	public void setFollower(User follower) {
		this.follower = follower;
	}
	public User getFollowed() {
		return followed;
	}
	public void setFollowed(User followed) {
		this.followed = followed;
	}

}
