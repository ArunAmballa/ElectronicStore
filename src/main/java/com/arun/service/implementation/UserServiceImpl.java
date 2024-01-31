package com.arun.service.implementation;

import java.awt.print.Pageable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arun.controllers.UserController;
import com.arun.dtos.PageableResponse;
import com.arun.dtos.UserDto;
import com.arun.entities.User;
import com.arun.exceptions.ResourceNotFoundException;
import com.arun.helper.Helper;
import com.arun.repositories.UserRepository;
import com.arun.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Value("${user.profile.image.path}")
	private String imagePath;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	private Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
	
	

	@Override
	public UserDto createUser(UserDto userDto) {
		
		String userId=UUID.randomUUID().toString();
		userDto.setUserId(userId);
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		User user=dtoToEntity(userDto);
		User savedUser=userRepository.save(user);
		UserDto newDto=entityToDto(savedUser);
		return newDto;
	}

	private UserDto entityToDto(User savedUser) {
		
//		UserDto userDto=UserDto.builder()
//		 	.userId(savedUser.getUserId())
//		 	.name(savedUser.getName())
//		 	.email(savedUser.getEmail())
//		 	.password(savedUser.getPassword())
//		 	.about(savedUser.getAbout())
//		 	.gender(savedUser.getGender())
//		 	.imageName(savedUser.getImageName())
//		 	.build();
		
		return mapper.map(savedUser, UserDto.class);
	}

	private User dtoToEntity(UserDto userDto) {
		
//		User user=User.builder()
//			.userId(userDto.getUserId())
//			.name(userDto.getName())
//			.email(userDto.getEmail())
//			.password(userDto.getPassword())
//			.about(userDto.getAbout())
//			.gender(userDto.getGender())
//			.imageName(userDto.getImageName())
//			.build();
		
		return mapper.map(userDto, User.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, String userId) {
		
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found with given Id"));
		user.setName(userDto.getName());
		user.setGender(userDto.getGender());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		user.setImageName(userDto.getImageName());
		
		User updatedUser=userRepository.save(user);
		UserDto updatedDto=entityToDto(updatedUser);
		return updatedDto;
	}

	@Override
	public void deleteUser(String userId) throws IOException {
		
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found with given Id"));
		
		//Delete user Profile Image
		
		String fullPath=imagePath+user.getImageName();
		
		try {
			Path path=Paths.get(fullPath);
			
			Files.delete(path);
		}catch(NoSuchFileException ex)
		{
			logger.info("User Image Not Found in Folder");
			ex.printStackTrace();
		} 
		
		userRepository.delete(user);
		
	}

	@Override
	public 	PageableResponse<UserDto> getAllUsers(int pageNumber,int pageSize,String sortBy,String sortDir) {
		
		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
		
		PageRequest pageable = PageRequest.of(pageNumber, pageSize,sort);
		
		Page<User> page = userRepository.findAll(pageable);
		
		PageableResponse<UserDto> pageableResponse = Helper.getPageableResponse(page, UserDto.class);
		
		return pageableResponse;
	}

	@Override
	public UserDto getUserById(String userId) {
		
		User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found with Given ID"));
		return entityToDto(user);
	}

	@Override
	public UserDto getUserByEmail(String email) {
		User user=userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("user not found with given email"));
		return entityToDto(user);
	}

	@Override
	public List<UserDto> searchUser(String keyword) {
		List<User> users=userRepository.findByNameContaining(keyword);
		List<UserDto> dtoList=users.stream().map(user->entityToDto(user)).collect(Collectors.toList());
		return dtoList;
	}

}
