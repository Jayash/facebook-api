package com.facebook.api.dto;

import java.util.Objects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileDto {
	
	private Long id;
	private String firstname;
	private String lastname;
	private String address;
	private String primaryPhone;
	private String gender;
	private Long user;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfileDto other = (ProfileDto) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	
	
}
