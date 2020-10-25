package com.facebook.api.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Relationship extends Auditable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Profile relatingfriend;
	@ManyToOne
	private Profile relatedfriend;
	private RelationType type;
}
