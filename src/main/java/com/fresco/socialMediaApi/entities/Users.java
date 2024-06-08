package com.fresco.socialMediaApi.entities;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;





@Entity
public class Users  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique = true)
	private String username;
	@Column(nullable = false)
	private String password;

	private String gender;
	private int age;
	
	@ManyToMany
	@JoinTable(name = "friend_list",
	joinColumns = @JoinColumn(name="users_id",referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name="friends_id",referencedColumnName = "id")
	)
	List <Users> friends = new ArrayList<>();
	
	
	@ManyToMany
	@JoinTable(name = "friend_request",
	joinColumns = @JoinColumn(name="to_id",referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name="from_id",referencedColumnName = "id")
	)
	List <Users> request = new ArrayList<>();
	
	public Users() {}
	
	
	public Users(String username, String password, String gender, int age) {
		super();
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.age = age;
	}

	
	
	
	public void deleteRequest(Users user) {
		request.remove(user);
	}
	
	public void deleteFriend(Users user) {
		friends.remove(user);
	}
	
	
	public List<Users> getRequest() {
		return request;
	}
	public void addRequest(Users requestedUser) {
		this.request.add(requestedUser);
	}
	public List<Users> getFriends() {
		return friends;
	}
	public void addFriends(Users friends) {
		this.friends.add(friends);
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	

}
