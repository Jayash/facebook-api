package com.facebook.api.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profile extends Auditable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String firstname;
	private String lastname;
	private String address;
	private String primaryPhone;
	private Gender gender;
	
	@OneToOne
	private User user;
	
	@OneToMany(mappedBy = "relatingfriend", fetch = FetchType.LAZY)
	private List<Relationship> relationship;

}
