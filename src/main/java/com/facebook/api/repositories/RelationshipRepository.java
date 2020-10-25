package com.facebook.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facebook.api.models.Profile;
import com.facebook.api.models.RelationType;
import com.facebook.api.models.Relationship;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long>  {

	List<Relationship> findAllByRelatingfriendAndType(Profile profile, RelationType type);
	
	Optional<Relationship> findByRelatingfriendAndAndRelatedfriendAndType(Profile relatingFriends, Profile relatedFriend, RelationType type);
	
	Optional<Relationship> findByRelatingfriendAndAndRelatedfriend(Profile relatingFriends, Profile relatedFriend);
}
