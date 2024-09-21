package com.learn.controller;

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

import com.learn.dto.UserDto;
import com.learn.exception.AlreadyExistsException;
import com.learn.exception.ResourceNotFoundException;
import com.learn.model.User;
import com.learn.request.CreateUserRequest;
import com.learn.request.UserUpdateRequest;
import com.learn.response.ApiResponse;
import com.learn.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/{userId}/user")
	public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
		try {
			User user = userService.getUserById(userId);
			UserDto userDto = userService.convertUserToDto(user);
			return ResponseEntity.ok(new ApiResponse("User retrieved successfully!", userDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
		try {
			User user = userService.createUser(request);
			UserDto userDto = userService.convertUserToDto(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("User created!", userDto));
		} catch (AlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping("/{userId}/update")
	public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId){
		try {
			User updatedUser = userService.updateUser(request, userId);
			UserDto userDto = userService.convertUserToDto(updatedUser);
			return ResponseEntity.ok(new ApiResponse("User updated!", userDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@DeleteMapping("/{userId}/delete")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
		try {
			userService.deleteUser(userId);
			return ResponseEntity.ok(new ApiResponse("User deleted!", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
}




