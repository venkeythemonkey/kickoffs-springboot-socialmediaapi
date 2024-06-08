package com.fresco.socialMediaApi.Dto;

import java.util.ArrayList;
import java.util.List;

import com.fresco.socialMediaApi.entities.Users;

public class RequestsDto {
	
	private String username;
	private List<UsersResponse> request = new ArrayList<>();
	
	
	public RequestsDto(Users user) {
		this.username = user.getUsername();
		for(Users use: user.getRequest()) {
			this.request.add(new UsersResponse(use));
		}
		
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public List<UsersResponse> getRequest() {
		return request;
	}


	public void setRequest(List<UsersResponse> request) {
		this.request = request;
	}
	
	

}
