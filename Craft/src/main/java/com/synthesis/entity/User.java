package com.synthesis.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {

	

	@Id
	private String userId;

	private String paswd;

	private String email;

	@OneToMany(mappedBy = "follower")
	private Collection<Follower> followers;
	
	
	@OneToMany(mappedBy = "followed")
	private Collection<Follower> following;
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPaswd() {
		return paswd;
	}

	public void setPaswd(String paswd) {
		this.paswd = paswd;
	}

	public Collection<Follower> getFollowers() {
		return followers;
	}

	public void setFollowers(Collection<Follower> followers) {
		this.followers = followers;
	}

	public Collection<Follower> getFollowing() {
		return following;
	}

	public void setFollowing(Collection<Follower> following) {
		this.following = following;
	}

	
	

}
