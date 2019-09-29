package com.synthesis.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity
public class Follower {

	@Id
	@GeneratedValue
	@Column(name = "follower_id")
	private int id;

	@OneToOne
	@JoinColumn(name="followed_by")
	private User followerName;
 
	
	private String following ;

	public Follower() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Follower other = (Follower) obj;
		/*
		 * if (followedBy == null) { if (other.followedBy != null) return false; } else
		 * if (!followedBy.equals(other.followedBy)) return false
		 */;
		if (!(id == other.id))
			return false;

		return true;
	}

	public User getFollowerName() {
		return followerName;
	}

	public void setFollowerName(User followerName) {
		this.followerName = followerName;
	}

	public void setFollowing(String following) {
		this.following = following;
	}

	public String getFollowing() {
		return following;
	}

}
