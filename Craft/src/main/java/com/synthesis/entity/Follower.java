package com.synthesis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Follower {

	@Id
	@GeneratedValue
	@Column(name = "follower_id")
	private int id;

	private String followerName;

	private String following;

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

	public String getFollowerName() {
		return followerName;
	}

	public void setFollowerName(String followerName) {
		this.followerName = followerName;
	}

	public void setFollowing(String following) {
		this.following = following;
	}

	public String getFollowing() {
		return following;
	}

}
