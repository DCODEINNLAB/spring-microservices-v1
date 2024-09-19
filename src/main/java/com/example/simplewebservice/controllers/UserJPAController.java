package com.example.simplewebservice.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.simplewebservice.beans.Post;
import com.example.simplewebservice.beans.User;
import com.example.simplewebservice.exceptions.UserNotFoundException;
import com.example.simplewebservice.service.PostJPARepository;
import com.example.simplewebservice.service.UserDaoService;
import com.example.simplewebservice.service.UserJPARepository;

import jakarta.validation.Valid;

@RestController
public class UserJPAController {
	
	@Autowired
	private UserJPARepository userRepository;
	
	@Autowired
	private PostJPARepository postRepository;
	
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers(){
		return userRepository.findAll();
	}
	
	@GetMapping("/jpa/users/{id}")
	public User retrieveUser(@PathVariable Integer id) {
		Optional<User> retrievedUser = userRepository.findById(id);
		
		if(retrievedUser.isEmpty()) {
			throw new UserNotFoundException("UserId: "+id);
		}
		return retrievedUser.get();
	}
	
	@GetMapping("jpa/users/hateoas/{id}")
	public EntityModel<User> retrieveUserHateoas(@PathVariable Integer id) {
		Optional<User> retrievedUser = userRepository.findById(id);
		
		if(retrievedUser.isEmpty()) {
			throw new UserNotFoundException("UserId: "+id);
		}
		
		EntityModel<User> entityModel = EntityModel.of(retrievedUser.get());
		
		WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(linkBuilder.withRel("all-users"));
		return entityModel;
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		//return userDaoService.save(user);
		User savedUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable Integer id){
		userRepository.deleteById(id);
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> getPosts(@PathVariable int id){
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("User: "+id+" not found.");
		}
		
		return user.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id,@RequestBody Post post){
		Optional<User> user = userRepository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("User: "+id+" not found.");
		}
		
		post.setUser(user.get());
		postRepository.save(post);
		
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
//	@PostMapping("/users-response")
//	public ResponseEntity<User> createUserWithResponseEntity(@RequestBody User user) {
//		User savedUser = userDaoService.save(user);
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
//		return ResponseEntity.created(location).build();
//	}
}
