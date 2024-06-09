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
	
	
	
	public List<UsersResponse> getAllUsers(){
		List<Users> users = usersRepo.findAll();
		List<UsersResponse> usersResponses = new ArrayList<>();
		for(Users user : users){
			usersResponses.add(new UsersResponse(user));
		}
		return usersResponses;
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
		if (uid == id) {
			throw new IllegalArgumentException("Invalid UserId");
		}

		Users from = fromUser.get();
		Users to = toUser.get();

		if(from.getFriends().contains(to)){
			response.put("status", "You are already friends with " + to.getUsername());
			return response;
		}

		if(to.getRequest().contains(from)){
			response.put("status", "You have already sent request");
			return response;
		}
		if(from.getRequest().contains(to)){
			response.put("status", "The User has already sent you a request");
			return response;
		}
		to.addRequest(from);
		usersRepo.save(to);
		response.put("status" , "Request sent to " + to.getUsername());
		return response;
	}



	public ResponseEntity<Object> getAllRequest(int id){
		Optional<Users> user = usersRepo.findById(id);
		if(user.isEmpty()){
			Map<String, String> response = new LinkedHashMap<>();
			response.put("error", "Invalid UserID");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		RequestsDto requestsDto = new RequestsDto(user.get());
		return ResponseEntity.status(HttpStatus.OK).body(requestsDto);
	}
	
	
	public Optional<Users> getUserById(int id){
		Optional<Users> users = usersRepo.findById(id);
		return users;

	}



	public ResponseEntity<Object> acceptFriend(int uid, int id) {
		Map<String, String> response = new LinkedHashMap<>();
		Optional<Users> toUser = usersRepo.findById(uid);
		Optional<Users> fromUser = usersRepo.findById(id);

		if(toUser.isEmpty()){
			throw new IllegalArgumentException("Invalid to_UserID");
		}
		if (fromUser.isEmpty()){
			throw new IllegalArgumentException("Invalid from_UserID");
		}

		Users to = toUser.get();
		Users from = fromUser.get();

		if(!to.getRequest().contains(from)){
			response.put("error", "Friend Requests does not contain this UserID");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		to.deleteRequest(from);
		to.addFriends(from);
		from.addFriends(to);
		usersRepo.save(to);
		usersRepo.save(from);
		response.put("status", "Friend Request Accepted - " + from.getUsername());
		return ResponseEntity.ok(response);
	}


	public ResponseEntity<Object> deleteFriend(int uid, int id) {
		Map<String, String> response = new LinkedHashMap<>();
		Optional<Users> toUser = usersRepo.findById(uid);
		Optional<Users> fromUser = usersRepo.findById(id);

		if(toUser.isEmpty()){
			throw new IllegalArgumentException("Invalid UserID");
		}
		if (fromUser.isEmpty()){
			throw new IllegalArgumentException("Invalid UserID");
		}

		Users to = toUser.get();
		Users from = fromUser.get();

		if(!to.getFriends().contains(from)){
			response.put("error", "Friendlist does not contain this UserID");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		to.deleteFriend(from);
		from.deleteFriend(to);
		usersRepo.save(to);
		usersRepo.save(from);
		response.put("status", "Friend Deleted - " + from.getUsername());
		return ResponseEntity.ok(response);
	}

	public ResponseEntity<Object> deleteRequest(int uid, int id) {
		Map<String, String> response = new LinkedHashMap<>();
		Optional<Users> toUser = usersRepo.findById(uid);
		Optional<Users> fromUser = usersRepo.findById(id);

		if(toUser.isEmpty()){
			throw new IllegalArgumentException("Invalid to_UserID");
		}
		if (fromUser.isEmpty()){
			throw new IllegalArgumentException("Invalid from_UserID");
		}

		Users to = toUser.get();
		Users from = fromUser.get();

		if(!to.getRequest().contains(from)){
			response.put("error", "Friend Requests does not contain this UserID");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		to.deleteRequest(from);
		usersRepo.save(to);
		response.put("status", "Friend Request Deleted - " + from.getUsername());
		return ResponseEntity.ok(response);
	}



	public ResponseEntity<Object> getFriends(int id){
		Optional<Users> user = usersRepo.findById(id);
		if(user.isEmpty()){
			Map<String, String> response = new LinkedHashMap<>();
			response.put("error", "Invalid UserID");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		FriendsDto friendsDto = new FriendsDto(user.get());
		return ResponseEntity.status(HttpStatus.OK).body(friendsDto);
	}

}
