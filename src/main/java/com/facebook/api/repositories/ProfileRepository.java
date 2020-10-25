package com.facebook.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facebook.api.models.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
