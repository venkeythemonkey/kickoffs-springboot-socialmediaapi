package com.fresco.socialMediaApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fresco.socialMediaApi.entities.Users;

@Repository
public interface UsersRepo extends JpaRepository<Users, Integer>{
	
}
