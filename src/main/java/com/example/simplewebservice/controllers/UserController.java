package com.example.simplewebservice.controllers;

import java.net.URI;
import java.util.List;

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

import com.example.simplewebservice.beans.User;
import com.example.simplewebservice.exceptions.UserNotFoundException;
import com.example.simplewebservice.service.UserDaoService;

import jakarta.validation.Valid;

@RestController
public class UserController {
	
	@Autowired
	private UserDaoService userDaoService;
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return userDaoService.findAll();
	}
	
	@GetMapping("/users/{id}")
	public User retrieveUser(@PathVariable Integer id) {
		User retrievedUser = userDaoService.findOne(id);
		
		if(retrievedUser == null) {
			throw new UserNotFoundException("UserId: "+id);
		}
		return retrievedUser;
	}
	
	@GetMapping("/users/hateoas/{id}")
	public EntityModel<User> retrieveUserHateoas(@PathVariable Integer id) {
		User retrievedUser = userDaoService.findOne(id);
		
		if(retrievedUser == null) {
			throw new UserNotFoundException("UserId: "+id);
		}
		
		EntityModel<User> entityModel = EntityModel.of(retrievedUser);
		
		WebMvcLinkBuilder linkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(linkBuilder.withRel("all-users"));
		return entityModel;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		//return userDaoService.save(user);
		User savedUser = userDaoService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable Integer id){
		boolean status = userDaoService.delete(id);
		if(!status) {
			throw new UserNotFoundException("User not found, User id: "+id);
		}
		return new ResponseEntity("User Successfully Deleted!!",HttpStatus.OK);
	}
	
//	@PostMapping("/users-response")
//	public ResponseEntity<User> createUserWithResponseEntity(@RequestBody User user) {
//		User savedUser = userDaoService.save(user);
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
//		return ResponseEntity.created(location).build();
//	}
}
