package com.fresco.socialMediaApi.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fresco.socialMediaApi.Dto.Uid;
import com.fresco.socialMediaApi.Dto.UsersResponse;
import com.fresco.socialMediaApi.entities.Users;
import com.fresco.socialMediaApi.repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fresco.socialMediaApi.services.UsersService;

import javax.swing.text.html.Option;


@RestController
public class Controller {
	

	@Autowired
	UsersService usersService;

	@Autowired
	UsersRepo usersRepo;

	
	@PostMapping("/signup")
	public ResponseEntity<UsersResponse> register(@RequestBody Users user) {
		return usersService.register(user);
	}
	

	@GetMapping("/home")
	public ResponseEntity<Object> allUser(@RequestParam(required = false) Optional<Integer> userId){
		if(userId.isPresent()){
			Optional<Users> user = usersRepo.findById(userId.get());
			if (user.isPresent()){
				return ResponseEntity.ok(new UsersResponse(user.get()));
			}
			else {
				return ResponseEntity.notFound().build();
			}
		}
		else {
			List<Users> users = usersRepo.findAll();
			List<UsersResponse> usersResponses = users.stream()
					.map(UsersResponse::new)
					.collect(Collectors.toList());
			return ResponseEntity.ok(usersResponses);
		}
	}


	@PostMapping("addFriend/{id}")
	public ResponseEntity<Object> sendRequest(@PathVariable int id, @RequestBody Uid uid){
		Map<String, String> response = new LinkedHashMap<>();
		try {
			response = usersService.addFriend(uid.getUid(), id);
		}
		catch (IllegalArgumentException e){
			response.put("error", e.getMessage());
			return ResponseEntity.status(400).body(response);
		}
		catch (Exception e){
			response.put("error", "Internal Server Error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
		return ResponseEntity.ok(response);
	}


	@GetMapping("friendRequest/{id}")
	public ResponseEntity<Object> getAllRequest(@PathVariable int id){
		return usersService.getAllRequest(id);
	}

	
	@PostMapping("acceptFriend/{id}")
	public ResponseEntity<Object> acceptFriend(int id,Uid uid){
		return null;
	}
	
	@PutMapping("deleteRequest/{id}")
	public ResponseEntity<Object> deleteRequest(@PathVariable int id, @RequestBody Uid uid){
		return null;
	}
	
	@PutMapping("deleteFriend/{id}")
	public ResponseEntity<Object> deleteFriend(@PathVariable int id, @RequestBody Uid uid){
		return null;
	}
	
	@GetMapping("friendList/{id}")
	public ResponseEntity<Object> friendList(@PathVariable int id){
		return null;
	}
}
