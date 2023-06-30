package com.challenge.library.entities;

import java.io.Serializable;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {

	private static final long serialVersionUID = -6013956741413173437L;
	
	@Id
	private Long id;
	private String title;
	@OneToOne
	@JoinColumn(name = "author_id", referencedColumnName = "id")
	private Author author;
	@OneToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private Category category;
	private boolean enabled;
}
