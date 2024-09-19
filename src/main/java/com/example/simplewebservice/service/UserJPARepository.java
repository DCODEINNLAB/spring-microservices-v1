package com.example.simplewebservice.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.simplewebservice.beans.User;

public interface UserJPARepository extends JpaRepository<User, Integer> {

}
