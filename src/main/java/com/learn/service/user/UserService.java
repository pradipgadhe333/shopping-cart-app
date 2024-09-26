package com.learn.service.user;

import com.learn.dto.UserDto;
import com.learn.model.User;
import com.learn.request.CreateUserRequest;
import com.learn.request.UserUpdateRequest;

public interface UserService {
	
	User getUserById(Long userId);
	User createUser(CreateUserRequest request);
	User updateUser(UserUpdateRequest request, Long userId);
	void deleteUser(Long userId);
	UserDto convertUserToDto(User user);

	User getAuthenticatedUser();
}
