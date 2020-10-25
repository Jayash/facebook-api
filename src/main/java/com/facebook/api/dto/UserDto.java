package com.facebook.api.dto;

import java.util.Objects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
	
	private Long id;
	private String username;
	private String password;
	private Long profile;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDto other = (UserDto) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
}
