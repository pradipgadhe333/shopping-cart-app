package com.learn.data;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.learn.model.User;
import com.learn.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
	
	private final UserRepository userRepository;

	@Override
	public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
		createDefaultUserIfNotExists();
	}
	
	private void createDefaultUserIfNotExists() {
		for(int i=1; i<=5; i++) {
			String defaultEmail = "user"+i+"@gmail.com";
			if(userRepository.existsByEmail(defaultEmail)) {
				continue;
			}
			
			User user = new User();
			user.setFirstName("The User");
			user.setLastName("User" + i);
			user.setEmail(defaultEmail);
			user.setPassword("123456");
			
			userRepository.save(user);
			System.out.println("Default new user " +i+ " created successfully.");
		}
	}

	
	
}





