package com.facebook.api.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.facebook.api.exceptions.FacebookAPIException;
import com.facebook.api.models.Profile;
import com.facebook.api.models.RelationType;
import com.facebook.api.models.Relationship;
import com.facebook.api.repositories.RelationshipRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RelationshipService {

	private final RelationshipRepository relationshipRepository;
	
	public List<Relationship> getAllUserFriends(Profile profile) {
		return relationshipRepository.findAllByRelatingfriendAndType(profile, RelationType.FRIEND);
	}

	public void addFriend(Profile relatingFriends, Profile relatedFriend) {
		
		Optional<Relationship> isAlreadyRelated = 
				relationshipRepository.findByRelatingfriendAndAndRelatedfriend(relatingFriends, relatedFriend);
		
		if(!isAlreadyRelated.isPresent()) {
			relationshipRepository.save(new Relationship(relatingFriends, relatedFriend, RelationType.FRIEND));
			relationshipRepository.save(new Relationship(relatedFriend, relatingFriends, RelationType.FRIEND));
		} else {
			Relationship relatingRelationship = isAlreadyRelated.get();
			Relationship relatedRelationship = relationshipRepository
					.findByRelatingfriendAndAndRelatedfriend(relatedFriend, relatingFriends)
					.orElseThrow(() -> new FacebookAPIException("Users are not Related"));
			
			relatingRelationship.setType(RelationType.FRIEND);
			relatedRelationship.setType(RelationType.FRIEND);
			
			relationshipRepository.save(relatingRelationship);
			relationshipRepository.save(relatedRelationship);
		}
	}

	public void unfriend(Profile relatingFriends, Profile relatedFriend) {
		
		Relationship relatingRelationship = relationshipRepository
				.findByRelatingfriendAndAndRelatedfriend(relatingFriends, relatedFriend)
				.orElseThrow(() -> new FacebookAPIException("Users are not Related"));
		Relationship relatedRelationship = relationshipRepository
				.findByRelatingfriendAndAndRelatedfriend(relatedFriend, relatingFriends)
				.orElseThrow(() -> new FacebookAPIException("Users are not Related"));		
		
		relatingRelationship.setType(RelationType.UNFRIEND);
		relatedRelationship.setType(RelationType.UNFRIEND);
		
		relationshipRepository.save(relatingRelationship);
		relationshipRepository.save(relatedRelationship);
		
	}
	
}
