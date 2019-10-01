package com.synthesis.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Follower  {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "follower_id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="follower")
	private User follower;

    
	@ManyToOne
	@JoinColumn(name="followed")
	private User followed;

	public Follower() {
	}
	
	public Follower(User follower, User followed) {
		
		this.follower = follower;
		this.followed = followed;
	};

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
