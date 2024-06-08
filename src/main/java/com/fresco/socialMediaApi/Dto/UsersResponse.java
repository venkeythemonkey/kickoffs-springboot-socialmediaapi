package com.fresco.socialMediaApi.Dto;

import com.fresco.socialMediaApi.entities.Users;

public class UsersResponse {
	
	private String username;
	private int age;
	private String gender;
	private int noOfFriends;
	
	public UsersResponse() {}
	
	public UsersResponse(Users users) {
		this.username = users.getUsername();
		this.age = users.getAge();
		this.gender = users.getGender();
		this.noOfFriends = users.getFriends().size();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getNoOfFriends() {
		return noOfFriends;
	}

	public void setNoOfFriends(int noOfFriends) {
		this.noOfFriends = noOfFriends;
	}
	
	

}
