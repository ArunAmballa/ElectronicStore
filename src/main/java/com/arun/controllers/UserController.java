package com.arun.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.arun.dtos.ApiResponseMessage;
import com.arun.dtos.ImageResponse;
import com.arun.dtos.PageableResponse;
import com.arun.dtos.UserDto;
import com.arun.service.FileService;
import com.arun.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${user.profile.image.path}")
	private String imageUploadPath;
	
	private Logger logger=LoggerFactory.getLogger(UserController.class);

	//create
	@PostMapping
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto user=userService.createUser(userDto);
		
		return new ResponseEntity<>(user,HttpStatus.CREATED);
		
	}
	
	//update
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId,
			@Valid @RequestBody UserDto userDto)
	{
		UserDto user=userService.updateUser(userDto, userId);
		return new ResponseEntity<>(user,HttpStatus.OK);
		
	}
	
	
	//delete
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId) throws IOException
	{
		userService.deleteUser(userId); 
		
		ApiResponseMessage message=ApiResponseMessage.builder()
		.message("User Deleted Successfully")
		.success(true)
		.httpStatus(HttpStatus.OK)
		.build();
		
		return new ResponseEntity<>(message,HttpStatus.OK);
	}
	
	//get all
	@GetMapping
	public ResponseEntity<PageableResponse<UserDto>> getAllUsers(@RequestParam(value="pageNumber",defaultValue="0",required=false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="sortBy",defaultValue="ASC",required=false) String  sortBy,
			@RequestParam(value="sortDir",defaultValue="asc",required=false) String  sortDir
			)
	{
		return new ResponseEntity<>(userService.getAllUsers(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
	}
	
	//get single
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable("userId") String userId)
	{
		return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email)
	{
		return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
	}
	
	
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<UserDto>> getUserSearch(@PathVariable("keywords") String keywords)
	{
		return new ResponseEntity<>(userService.searchUser(keywords),HttpStatus.OK);
	}
	
	
	//upload user Image
	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable String userId) throws IOException
	{
		
		String imageName = fileService.uploadFile(image, imageUploadPath);
		
		UserDto user=userService.getUserById(userId);
		
		user.setImageName(imageName);
		
		UserDto userDto=userService.updateUser(user, userId);
		
		
		ImageResponse imageResponse=ImageResponse.builder().imageName(imageName).success(true).httpStatus(HttpStatus.CREATED).build();
		
		return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
		
		
	}
	
	
	
	//Serve user Image
	@GetMapping("/image/{userId}")
	public void serveUserimage(@PathVariable String userId,HttpServletResponse response) throws IOException
	{
		UserDto user=userService.getUserById(userId);
		
		logger.info("User Image Name {}",user.getImageName());
		
		InputStream resource=fileService.getResource(imageUploadPath, user.getImageName());
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		StreamUtils.copy(resource,response.getOutputStream());
			
	}
	
	
	
	
}
