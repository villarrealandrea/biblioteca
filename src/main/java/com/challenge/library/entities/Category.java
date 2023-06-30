package com.challenge.library.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {
	
	private static final long serialVersionUID = 518394453896090189L;
	
	@Id
	private Long id;
	private String name;
	private String description;

	
}
