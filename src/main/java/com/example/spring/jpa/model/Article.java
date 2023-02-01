package com.example.spring.jpa.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.ToString;


@Builder (toBuilder = true)
@Data
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	private long id;
	
	@NonNull
	@OneToOne ( cascade = CascadeType.ALL )
	private User user;
	
    @Column(length = 40)
	@NonNull
	private String title;
    
	private String slug;
	@NonNull
	private String description;
	@NonNull
	private String body;
		
	private LocalDateTime createdAt;//@TODO: default to now
	private LocalDateTime updatedAt;
	
	@Singular
	@OneToMany( cascade = CascadeType.ALL )
	@JoinColumn
	private List<Comment> comments;
	
	@Singular
	//why one to many? see: https://stackoverflow.com/questions/8804572/hibernate-onetomany-and-unique-constraint/75266290#75266290
	@ManyToMany ( cascade = CascadeType.ALL )
	@NonNull
	private List<Tag> tags;
}
