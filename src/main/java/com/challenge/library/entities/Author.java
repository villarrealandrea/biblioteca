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
public class Author implements Serializable {
	
	private static final long serialVersionUID = -3089660013220406458L;

	@Id
	private Long id;
	private String firstName;
	private String lastName;
}
