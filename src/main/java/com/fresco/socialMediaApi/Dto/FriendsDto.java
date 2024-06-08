package com.fresco.socialMediaApi.Dto;

import java.util.ArrayList;
import java.util.List;

import com.fresco.socialMediaApi.entities.Users;

public class FriendsDto {
	
	private String username;
	private List<UsersResponse> friendList = new ArrayList<>();
	
	public FriendsDto(Users user) {
		for(Users u: user.getFriends()) {
			friendList.add(new UsersResponse(u));
		}
		username = user.getUsername();
	}

	public List<UsersResponse> getFriendList() {
		return friendList;
	}	

}
