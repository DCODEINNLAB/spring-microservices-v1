package com.example.simplewebservice.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.example.simplewebservice.beans.User;

@Component
public class UserDaoService {

	private static List<User> users = new ArrayList<User>();
	
	private static Integer userCount = 0;
	
	static {
		users.add(new User(++userCount,"test1", LocalDate.now().minusYears(30)));
		users.add(new User(++userCount,"test2", LocalDate.now().minusYears(20)));
		users.add(new User(++userCount,"test3", LocalDate.now().minusYears(25)));
	}
	
	//public List<User> findAll()
	public List<User> findAll(){
		return users;
	}
	
	public User save(User user) {
		user.setId(++userCount);
		users.add(user);
		return user;
	}
	
	public User findOne(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		return users.stream().filter(predicate).findFirst().orElse(null);
	}
	
	public boolean delete(int id) {
		return users.removeIf(user -> user.getId().equals(id));	
	}
}
