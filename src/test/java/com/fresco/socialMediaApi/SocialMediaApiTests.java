package com.fresco.socialMediaApi;

import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fresco.socialMediaApi.Dto.Uid;
import com.fresco.socialMediaApi.entities.Users;
import com.fresco.socialMediaApi.repositories.UsersRepo;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SocialMediaApiTests {
	
	@Autowired
	UsersRepo usersRepo;
	
	@Autowired
	private MockMvc mmc;
	
	@Autowired
	ObjectMapper objmap;
	
	Users user1  = new Users("uwu","uwu","Female",23); 
	Users user2  = new Users("kik","kik","Female",24);
	Users user3  = new Users("pop","pop","Male",21);
	Users user4  = new Users("viv","viv","Female",27);
	Users user5  = new Users("bar","bar","Male",22);
	
	Uid uid1 = new Uid(1);
	Uid uid2 = new Uid(2);
	Uid uid3 = new Uid(3);
	Uid uid4 = new Uid(4);
	Uid uid6 = new Uid(6);
	Uid uid7 = new Uid(7);


	@Test
	@Order(1)
	public void addUserTest() throws Exception {
		mmc.perform(post("/signup").content(objmap.writeValueAsString(user1))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(201))
				.andExpect(jsonPath("$.username",containsStringIgnoringCase("uwu")))
				.andExpect(jsonPath("$.gender",containsStringIgnoringCase("female")))
				.andExpect(jsonPath("$.noOfFriends",is(0)))
				.andExpect(jsonPath("$.age", is(23)));

		
		mmc.perform(post("/signup").content(objmap.writeValueAsString(user2))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201))
				.andExpect(jsonPath("$.username",containsStringIgnoringCase("kik")))
				.andExpect(jsonPath("$.age", is(24)))
				.andExpect(jsonPath("$.gender",containsStringIgnoringCase("female")))
				.andExpect(jsonPath("$.noOfFriends",is(0)));
		
		mmc.perform(post("/signup").content(objmap.writeValueAsString(user3))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201))
				.andExpect(jsonPath("$.username",containsStringIgnoringCase("pop")))
				.andExpect(jsonPath("$.age", is(21)))
				.andExpect(jsonPath("$.gender",containsStringIgnoringCase("male")))
				.andExpect(jsonPath("$.noOfFriends",is(0)));
		
		mmc.perform(post("/signup").content(objmap.writeValueAsString(user4))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201))
				.andExpect(jsonPath("$.username",containsStringIgnoringCase("viv")))
				.andExpect(jsonPath("$.age", is(27)))
				.andExpect(jsonPath("$.gender",containsStringIgnoringCase("female")))
				.andExpect(jsonPath("$.noOfFriends",is(0)));
		
		mmc.perform(post("/signup").content(objmap.writeValueAsString(user5))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(201))
				.andExpect(jsonPath("$.username",containsStringIgnoringCase("bar")))
				.andExpect(jsonPath("$.age", is(22)))
				.andExpect(jsonPath("$.gender",containsStringIgnoringCase("male")))
				.andExpect(jsonPath("$.noOfFriends",is(0)));
	}
	
	
	
	
	@Test
	@Order(2)
	public void getUserTest() throws Exception {
		mmc.perform(get("/home?userId=2").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.username",containsStringIgnoringCase("kik")))
				.andExpect(jsonPath("$.age", is(24)))
				.andExpect(jsonPath("$.gender",containsStringIgnoringCase("female")))
				.andExpect(jsonPath("$.noOfFriends",is(0)));
				
		
		
		mmc.perform(get("/home?userId=4").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.username",containsStringIgnoringCase("viv")))
				.andExpect(jsonPath("$.age", is(27)))
				.andExpect(jsonPath("$.gender",containsStringIgnoringCase("female")))
				.andExpect(jsonPath("$.noOfFriends",is(0)));
	}
	
	
	
	@Test
	@Order(3)
	public void getAllUserTest() throws Exception {
		mmc.perform(get("/home").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.[4].username",containsStringIgnoringCase("bar")))
				.andExpect(jsonPath("$.[4].age", is(22)))
				.andExpect(jsonPath("$.[4].gender",containsStringIgnoringCase("male")))
				.andExpect(jsonPath("$.[4].noOfFriends",is(0)))
				.andExpect(jsonPath("$.[1].username",containsStringIgnoringCase("kik")))
				.andExpect(jsonPath("$.[1].age", is(24)))
				.andExpect(jsonPath("$.[1].gender",containsStringIgnoringCase("female")))
				.andExpect(jsonPath("$.[1].noOfFriends",is(0)));
	
	}
	
	
	@Test
	@Order(4)
	public void addFriendTest() throws Exception{
		mmc.perform(post("/addFriend/2").content(objmap.writeValueAsString(uid1))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.status",containsStringIgnoringCase("Request sent to kik")));
		mmc.perform(post("/addFriend/3").content(objmap.writeValueAsString(uid1))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.status",containsStringIgnoringCase("Request sent to pop")));
		mmc.perform(post("/addFriend/4").content(objmap.writeValueAsString(uid1))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.status",containsStringIgnoringCase("Request sent to viv")));
		mmc.perform(post("/addFriend/5").content(objmap.writeValueAsString(uid1))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.status",containsStringIgnoringCase("Request sent to bar")));
		
		
		
		
		mmc.perform(post("/addFriend/3").content(objmap.writeValueAsString(uid2))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.status",containsStringIgnoringCase("Request sent to pop")));
		mmc.perform(post("/addFriend/4").content(objmap.writeValueAsString(uid2))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.status",containsStringIgnoringCase("Request sent to viv")));
		mmc.perform(post("/addFriend/5").content(objmap.writeValueAsString(uid2))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.status",containsStringIgnoringCase("Request sent to bar")));
		
		
		
		mmc.perform(post("/addFriend/4").content(objmap.writeValueAsString(uid3))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.status",containsStringIgnoringCase("Request sent to viv")));
		mmc.perform(post("/addFriend/5").content(objmap.writeValueAsString(uid3))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.status",containsStringIgnoringCase("Request sent to bar")));
			
	}
	
	
	@Test
	@Order(5)
	public void addFriendStatusTest()throws Exception{
		mmc.perform(post("/addFriend/1").content(objmap.writeValueAsString(uid1))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(400))
				.andExpect(jsonPath("$.error",containsStringIgnoringCase("Invalid UserId")));
		mmc.perform(post("/addFriend/5").content(objmap.writeValueAsString(uid1))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.status",containsStringIgnoringCase("You have already sent request")));
		mmc.perform(post("/addFriend/1").content(objmap.writeValueAsString(uid3))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.status",containsStringIgnoringCase("The User has already sent you a request")));
		mmc.perform(post("/addFriend/10").content(objmap.writeValueAsString(uid3))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(400))
				.andExpect(jsonPath("$.error",containsStringIgnoringCase("Invalid to_UserID")));
		mmc.perform(post("/addFriend/10").content(objmap.writeValueAsString(uid6))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(400))
				.andExpect(jsonPath("$.error",containsStringIgnoringCase("Invalid from_UserID")));
	}
	
	
	@Test
	@Order(6)
	public void getAllFriendRequestTest() throws Exception{

		mmc.perform(get("/friendRequest/4").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.request.[0].username",containsStringIgnoringCase("uwu")))
				.andExpect(jsonPath("$.request.[1].username",containsStringIgnoringCase("kik")))
				.andExpect(jsonPath("$.request.[1].age",is(24)))
				.andExpect(jsonPath("$.request.[1].gender",containsStringIgnoringCase("female")))
				.andExpect(jsonPath("$.request.[1].noOfFriends",is(0)))
				.andExpect(jsonPath("$.request.[2].username",containsStringIgnoringCase("pop")))
				.andExpect(jsonPath("$.request.length()",is(3)));
		
	}
	
	@Test
	@Order(7)
	public void getAllFriendRequestErrorTest() throws Exception{

		mmc.perform(get("/friendRequest/10").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(400))
				.andExpect(jsonPath("$.error",containsStringIgnoringCase("Invalid UserID")));;
	}
	
	
	@Test
	@Order(8)
	public void acceptFriend() throws Exception{
		mmc.perform(post("/acceptFriend/1").content(objmap.writeValueAsString(uid4))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.status",containsStringIgnoringCase("Friend Request Accepted - uwu")));
		mmc.perform(post("/acceptFriend/2").content(objmap.writeValueAsString(uid4))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.status",containsStringIgnoringCase("Friend Request Accepted - kik")));
		
		mmc.perform(get("/friendRequest/4").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.request.[0].username",containsStringIgnoringCase("pop")))
				.andExpect(jsonPath("$.request.length()",is(1)));
		
	}
	
	
	@Test
	@Order(9)
	public void getAllFriendsTest() throws Exception{
		mmc.perform(get("/friendList/4").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.friendList.[0].username",containsStringIgnoringCase("uwu")))
				.andExpect(jsonPath("$.friendList.[1].username",containsStringIgnoringCase("kik")))
				.andExpect(jsonPath("$.friendList.[1].age",is(24)))
				.andExpect(jsonPath("$.friendList.[1].gender",containsStringIgnoringCase("female")))
				.andExpect(jsonPath("$.friendList.[1].noOfFriends",is(1)))
				.andExpect(jsonPath("$.friendList.length()",is(2)));
		
		
		mmc.perform(get("/friendList/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.friendList.[0].username",containsStringIgnoringCase("viv")))
				.andExpect(jsonPath("$.friendList.[0].noOfFriends",is(2)))
				.andExpect(jsonPath("$.friendList.length()",is(1)));
		
	}
	
	
	
	@Test
	@Order(10)
	public void deleteFriendTest() throws Exception {
		mmc.perform(put("/deleteFriend/1").content(objmap.writeValueAsString(uid4))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("status",containsStringIgnoringCase("Friend Deleted - uwu")));
		mmc.perform(get("/friendList/4").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.friendList.length()",is(1)));
		mmc.perform(put("/deleteFriend/2").content(objmap.writeValueAsString(uid4))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("status",containsStringIgnoringCase("Friend Deleted - kik")));
		
		mmc.perform(get("/friendList/4").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.friendList.length()",is(0)));
		mmc.perform(get("/home?userId=4").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.noOfFriends",is(0)));
	}
	
	
	@Test
	@Order(11)
	public void deleteRequest() throws Exception{
		mmc.perform(put("/deleteRequest/3").content(objmap.writeValueAsString(uid4))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.status",containsStringIgnoringCase("Friend Request Deleted - pop")));
		
		mmc.perform(get("/friendRequest/4").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(jsonPath("$.username",containsStringIgnoringCase("viv")))
				.andExpect(jsonPath("$.request.length()",is(0)));
	}
	
	@Test
	@Order(12)
	public void deleteRequestStatusTest() throws Exception{
		mmc.perform(put("/deleteRequest/1").content(objmap.writeValueAsString(uid4))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(404))
				.andExpect(jsonPath("$.error",containsStringIgnoringCase("Friend Requests does not contain this UserID")));
		mmc.perform(put("/deleteRequest/10").content(objmap.writeValueAsString(uid4))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(400))
				.andExpect(jsonPath("$.error",containsStringIgnoringCase("Invalid from_UserID")));
		mmc.perform(put("/deleteRequest/1").content(objmap.writeValueAsString(uid6))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(400))
				.andExpect(jsonPath("$.error",containsStringIgnoringCase("Invalid to_UserID")));
		
	}
	
	@Test
	@Order(13)
	public void deleteFriendStatusTest() throws Exception{
		mmc.perform(put("/deleteFriend/5").content(objmap.writeValueAsString(uid4))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(404))
				.andExpect(jsonPath("$.error",containsStringIgnoringCase("Friendlist does not contain this UserID")));
		mmc.perform(put("/deleteFriend/15").content(objmap.writeValueAsString(uid4))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(400))
				.andExpect(jsonPath("$.error",containsStringIgnoringCase("Invalid UserID")));
		
		mmc.perform(put("/deleteFriend/10").content(objmap.writeValueAsString(uid7))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(400))
				.andExpect(jsonPath("$.error",containsStringIgnoringCase("Invalid UserID")));
	}
	
	@Test
	@Order(14)
	public void acceptFriendStatusTest() throws Exception{
		mmc.perform(post("/acceptFriend/5").content(objmap.writeValueAsString(uid4))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(404))
				.andExpect(jsonPath("$.error",containsStringIgnoringCase("Friend Requests does not contain this UserID")));
		
		mmc.perform(post("/acceptFriend/15").content(objmap.writeValueAsString(uid4))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(400))
				.andExpect(jsonPath("$.error",containsStringIgnoringCase("Invalid from_UserID")));
		
		mmc.perform(post("/acceptFriend/15").content(objmap.writeValueAsString(uid6))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(400))
				.andExpect(jsonPath("$.error",containsStringIgnoringCase("Invalid to_UserID")));
		
	}
	
	@Test
	@Order(15)
	public void getAllFriendsErrorTest() throws Exception{
		mmc.perform(get("/friendList/41").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(400))
				.andExpect(jsonPath("$.error",containsStringIgnoringCase("Invalid UserID")));
	}
	

}
