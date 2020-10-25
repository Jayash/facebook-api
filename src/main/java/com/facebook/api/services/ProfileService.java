package com.facebook.api.services;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.facebook.api.dto.ProfileDto;
import com.facebook.api.exceptions.FacebookAPIException;
import com.facebook.api.exceptions.NoSuchUserException;
import com.facebook.api.exceptions.UserProfileAlreadyCreatedException;
import com.facebook.api.models.Gender;
import com.facebook.api.models.Profile;
import com.facebook.api.models.User;
import com.facebook.api.repositories.ProfileRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class ProfileService {

	private final ProfileRepository profileRepository;
	private final RelationshipService relationshipService;
	private final UserService userService;
	
	public void save(ProfileDto profileDto) {
		Profile profile = mapDtoToProfile(profileDto);
		User user = profile.getUser();
		
		if(user.getProfile() != null) throw new UserProfileAlreadyCreatedException("User Profile Already Created");
		
		profile = profileRepository.save(profile);
		user.setProfile(profile);
		userService.save(user);
	}

	public ProfileDto getProfile(long id) {
		
		Profile profile = getProfileById(id);
		
		return mapProfileToDto(profile);
	}
	
	public List<ProfileDto> getAllFriends(long id) {
		Profile profile = getProfileById(id);
		
		return relationshipService
				.getAllUserFriends(profile)
				.stream()
				.map(relationship -> mapProfileToDto(relationship.getRelatedfriend()))
				.collect(Collectors.toList());
	}
	
	public void addFriend(long id, long friendId) {
		
		if(id == friendId) throw new FacebookAPIException("friend cannout be same user");
		
		Profile profile = getProfileById(id);
		
		Profile friendsProfile = getProfileById(friendId);
		
		relationshipService.addFriend(profile, friendsProfile);
	}
	
	public void unfriend(long id, long friendId) {
		
		Profile profile = getProfileById(id);
		
		Profile friendsProfile = getProfileById(friendId);
		
		relationshipService.unfriend(profile, friendsProfile);
		
	}
	
	// BFS to get all friends eliminating the duplicate profiles
	public Set<ProfileDto> getAllFriendsWithinDistance(long id, int distance) {
		
		Profile requestedUserProfile = getProfileById(id);
		
		ProfileDto requestedUserprofileDto = mapProfileToDto(requestedUserProfile);
		
		Queue<Profile> queue = new LinkedList<>();
		Set<ProfileDto> friends = new HashSet<>();
		queue.offer(requestedUserProfile);
		
		while(!queue.isEmpty() && distance > 0) {
			
			int size = queue.size();
			
			while(size-- > 0) {
				
				List<Profile> relatedFriends = relationshipService.getAllUserFriends(queue.poll())
								.stream()
								.map(relationship -> relationship.getRelatedfriend())
								.collect(Collectors.toList());
				
				for(Profile friend : relatedFriends) {
					
					ProfileDto profileDto = mapProfileToDto(friend);
					
					if(!friends.contains(profileDto) && !profileDto.equals(requestedUserprofileDto)) {					
						queue.add(friend);
						friends.add(profileDto);
					}
				}
				
			}
			
			distance--;
		}
		
		return friends;
	}
	
	private ProfileDto mapProfileToDto(Profile profile) {
		
		return ProfileDto.builder()
				.id(profile.getId())
				.firstname(profile.getFirstname())
				.lastname(profile.getLastname())
				.primaryPhone(profile.getPrimaryPhone())
				.gender(profile.getGender().name())
				.user(profile.getUser().getId())
				.build();
		
	}

	private Profile mapDtoToProfile(ProfileDto profileDto) {
		
		return Profile.builder()
				.address(profileDto.getAddress())
				.firstname(profileDto.getFirstname())
				.lastname(profileDto.getLastname())
				.primaryPhone(profileDto.getPrimaryPhone())
				.gender(Gender.valueOf(profileDto.getGender()))
				.user(userService.findById(profileDto.getUser()))
				.build();
		
	}
	
	private Profile getProfileById(long id) {
		return profileRepository
		.findById(id)
		.orElseThrow(() -> new NoSuchUserException("profile with id " + id + " not found"));
	}
	
}
