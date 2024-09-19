package com.example.simplewebservice.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.simplewebservice.beans.SomeBean;

@RestController
public class FilteringController {

	public SomeBean filter() {
		return new SomeBean("field1","field2","field3");
	}
}
