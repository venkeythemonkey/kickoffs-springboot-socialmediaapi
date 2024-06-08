package com.fresco.socialMediaApi.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fresco.socialMediaApi.Dto.FriendsDto;
import com.fresco.socialMediaApi.Dto.RequestsDto;
import com.fresco.socialMediaApi.Dto.UsersResponse;
import com.fresco.socialMediaApi.entities.Users;
import com.fresco.socialMediaApi.repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
	
	@Autowired
	UsersRepo usersRepo;
	
	public ResponseEntity<UsersResponse> register(Users user){
		UsersResponse usersResponse = new UsersResponse(usersRepo.save(user));
		return ResponseEntity.status(HttpStatus.CREATED).body(usersResponse);
	}
	
	
	
	public ResponseEntity<Object> getAllUsers(){
		List<Users> users = usersRepo.findAll();
		List<UsersResponse> usersResponses = new ArrayList<>();
		for(Users user : users){
			usersResponses.add(new UsersResponse(user));
		}
		return ResponseEntity.status(HttpStatus.OK).body(usersResponses);
	}
	
	public Map<String, String> addFriend(int uid, int id){
		Map<String, String> response = new LinkedHashMap<>();
		Optional<Users> fromUser = usersRepo.findById(uid);
		Optional<Users> toUser = usersRepo.findById(id);

		if(fromUser.isEmpty()){
			throw new IllegalArgumentException("Invalid from_UserID");
		}
		if (toUser.isEmpty()){
			throw new IllegalArgumentException("Invalid to_UserID");
		}

		Users from = fromUser.get();
		Users to = toUser.get();

		if(from.getFriends().contains(to)){
			response.put("status", "You have already sent request");
			return response;
		}

		if(to.getRequest().contains(from)){
			response.put("status", "The User has already sent you a request");
			return response;
		}
		to.addRequest(from);
		usersRepo.save(to);
		response.put("status" , "Request sent to " + to.getUsername());
		return response;
	}
	
	
	
	public ResponseEntity<Object> getAllRequest(int id){
		return null;
	}
	
	
	public ResponseEntity<Object> getUserById(int id){
		Optional<Users> users = usersRepo.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(new UsersResponse(users.get()));

	}



	public ResponseEntity<Object> acceptFriend(int uid, int id) {
		return null;
	}



	public ResponseEntity<Object> deleteRequest(int uid, int id) {
		return null;
	}
	
	
	public ResponseEntity<Object> deleteFriend(int uid, int id) {
		return null;
	}
	
	
	
	public ResponseEntity<Object> getFriends(int id){
		return null;
	}

}
