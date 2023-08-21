package com.justreadit.blog.entities;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; 
import java.util.ArrayList; 

@Entity
@Table(name = "posts")
@Getter 
@Setter
@NoArgsConstructor
public class Post{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(name="post_title",length = 100,nullable = false) 
	private String title ; 
	
	@Column(length = 1000)
	private String content; 
	
	private String imageName;   
	private Date addedDate;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;  
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();
	
}
