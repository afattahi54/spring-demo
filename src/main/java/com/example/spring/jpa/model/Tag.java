package com.example.spring.jpa.model;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Tag {
	@Id
	private String id;
	private String name;
}
