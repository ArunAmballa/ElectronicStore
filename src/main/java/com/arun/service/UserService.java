package com.arun.service;

import java.io.IOException;
import java.util.List;

import com.arun.dtos.PageableResponse;
import com.arun.dtos.UserDto;
import com.arun.entities.User;

public interface UserService {
	
	//create User
	
	UserDto createUser(UserDto userDto);
	
	
	//Update User
	
	UserDto updateUser(UserDto userDto,String userId);
	
	
	//Delete user
	
	void deleteUser(String userId) throws IOException;
	
	//get all users
	
	PageableResponse<UserDto> getAllUsers(int pageNumber,int pageSize,String sortBy,String sortDir);

	
	//get user by id
	UserDto getUserById(String userId);
	
	
	//get user by email
	
	UserDto getUserByEmail(String email);
	
	
	//search user
	
	List<UserDto> searchUser(String keyword);
	
	

	
}
