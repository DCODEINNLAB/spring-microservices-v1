package com.example.simplewebservice.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.simplewebservice.beans.Post;

public interface PostJPARepository extends JpaRepository<Post, Integer> {

}
