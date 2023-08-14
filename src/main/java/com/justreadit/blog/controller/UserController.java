package com.justreadit.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justreadit.blog.entities.User;
import com.justreadit.blog.payload.APIResponse;
import com.justreadit.blog.payload.UserDto;
import com.justreadit.blog.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	
	//PUT - update user 
	@PutMapping("/{userId}") 
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@PathVariable("userId") Integer userId){
		UserDto updatedUser = this.userService.updateUser(userDto, userId); 
		return ResponseEntity.ok(updatedUser);
		
	}
	
	//DELETE - delete user; 
	@DeleteMapping("/{userId}")
	public ResponseEntity<APIResponse> deleteUser(@PathVariable Integer userId){
		this.userService.deleteUser(userId); 
		
		return new ResponseEntity<>(new APIResponse("User deleted successfully",true),HttpStatus.OK);
	}
	
	
	//GET - user get
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
		return new ResponseEntity<>(this.userService.getUserById(userId),HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){ 
		return new ResponseEntity<>(this.userService.getAllUsers(),HttpStatus.OK);
	}
	
}
