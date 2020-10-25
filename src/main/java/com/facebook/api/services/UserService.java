package com.facebook.api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facebook.api.dto.UserDto;
import com.facebook.api.exceptions.NoSuchUserException;
import com.facebook.api.models.User;
import com.facebook.api.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	/*
	 * add a new user
	 */
	public void save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	public List<UserDto> getAllUsers() {
		return userRepository
				.findAll()
				.stream()
				.map(user -> mapUserToDto(user))
				.collect(Collectors.toList());
	}

	public User findById(Long userId) {
		return userRepository
				.findById(userId)
				.orElseThrow(() -> new NoSuchUserException("User not found"));
	}
	
	private UserDto mapUserToDto(User user) {
		
		return UserDto.builder()
				.id(user.getId())
				.username(user.getUsername())
				.profile(user.getProfile().getId())
				.build();
		
	}
}
