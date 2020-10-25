package com.facebook.api.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facebook.api.dto.ProfileDto;
import com.facebook.api.models.Profile;
import com.facebook.api.services.ProfileService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/profiles")
public class ProfileController {
	
	private final ProfileService profileService;
	
	@PostMapping("/create")
	public ResponseEntity<String> createProfile(@RequestBody ProfileDto profileDto) {
		profileService.save(profileDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProfileDto> getProfile(@PathVariable long id) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(profileService.getProfile(id));
	}
	
	@GetMapping("/{id}/friends")
	public ResponseEntity<List<ProfileDto>> getAllFriends(@PathVariable long id) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(profileService.getAllFriends(id));
	}
	
	@GetMapping("/{id}/friends/{distance}")
	public ResponseEntity<Set<ProfileDto>> getAllFriendsWithinDistance(@PathVariable long id, @PathVariable int distance) {
		
		if(distance == 0) return ResponseEntity.status(HttpStatus.OK).build();
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(profileService.getAllFriendsWithinDistance(id, distance));
	}
	
	@PostMapping("/{id}/addFriend/{friendId}")
	public ResponseEntity<Set<Profile>> addFriend(@PathVariable long id, @PathVariable long friendId) {
		profileService.addFriend(id, friendId);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping("/{id}/unfriend/{friendId}")
	public ResponseEntity<Set<Profile>> unfriend(@PathVariable long id, @PathVariable long friendId) {
		profileService.unfriend(id, friendId);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
