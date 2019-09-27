package com.synthesis.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User {

	@Id
	private String userId;

	private String paswd;

	private String email;

	/*
	 * @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL) private
	 * List<Tweet> tweets;
	 */

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return paswd;
	}

	public void setPassword(String paswd) {
		this.paswd = paswd;
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

	/*
	 * public List<Tweet> getTweets() { return tweets; }
	 * 
	 * public void setTweets(List<Tweet> tweets) { this.tweets = tweets; }
	 */

}
