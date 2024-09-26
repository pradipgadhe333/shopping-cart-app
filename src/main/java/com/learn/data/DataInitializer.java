package com.learn.data;

import com.learn.model.Role;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.learn.model.User;
import com.learn.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Transactional
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
		Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
		createDefaultUserIfNotExists();
		createDefaultRoleIfNotExists(defaultRoles);
		createDefaultAdminIfNotExists();
	}
	
	private void createDefaultUserIfNotExists() {
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		for(int i=1; i<=5; i++) {
			String defaultEmail = "user"+i+"@gmail.com";
			if(userRepository.existsByEmail(defaultEmail)) {
				continue;
			}
			
			User user = new User();
			user.setFirstName("The User");
			user.setLastName("User" + i);
			user.setEmail(defaultEmail);
			user.setPassword(passwordEncoder.encode("123456"));
			user.setRoles(Set.of(userRole));

			userRepository.save(user);
			System.out.println("Default new user " +i+ " created successfully.");
		}
	}

	private void createDefaultAdminIfNotExists() {
		Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
		for(int i=1; i<=2; i++) {
			String defaultEmail = "admin"+i+"@gmail.com";
			if(userRepository.existsByEmail(defaultEmail)) {
				continue;
			}

			User user = new User();
			user.setFirstName("The Admin");
			user.setLastName("Admin" + i);
			user.setEmail(defaultEmail);
			user.setPassword(passwordEncoder.encode("123456"));
			user.setRoles(Set.of(adminRole));

			userRepository.save(user);
			System.out.println("Default new admin user " +i+ " created successfully.");
		}
	}

	private void createDefaultRoleIfNotExists(Set<String> roles){
		roles.stream()
				.filter(role -> roleRepository.findByName(role).isEmpty())
				.map(Role::new).forEach(roleRepository::save);
	}
	
}





