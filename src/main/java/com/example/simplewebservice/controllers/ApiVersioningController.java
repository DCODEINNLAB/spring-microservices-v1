package com.example.simplewebservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.simplewebservice.beans.PersonDaoV1;
import com.example.simplewebservice.beans.PersonDaoV2;

@RestController
public class ApiVersioningController {

	@GetMapping("/v1/person")
	public PersonDaoV1 person() {
		return new PersonDaoV1("Harsh Vardhan");
	}
	
	@GetMapping("/v2/person")
	public PersonDaoV2 personV2() {
		return new PersonDaoV2("Harsh","Vardhan");
	}
	
	@GetMapping(path = "/person",params = "version=v1")
	public PersonDaoV1 personVersionRequestParam() {
		return new PersonDaoV1("Harsh Vardhan");
	}
	
	@GetMapping(path = "/person", params = "version=v2")
	public PersonDaoV2 personVersionRequestParamV2() {
		return new PersonDaoV2("Harsh","Vardhan");
	}
	
	@GetMapping(path = "/person-header",headers = "Y-API-VERSION=v1")
	public PersonDaoV1 personVersionHeaders() {
		return new PersonDaoV1("Harsh Vardhan");
	}
	
	@GetMapping(path = "/person-header", headers = "Y-API-VERSION=v2")
	public PersonDaoV2 personVersionHeadersV2() {
		return new PersonDaoV2("Harsh","Vardhan");
	}
	
	@GetMapping(path = "/person",produces = "application/abc.com.v1+json")
	public PersonDaoV1 personVersionMediaType() {
		return new PersonDaoV1("Harsh Vardhan");
	}
	
	@GetMapping(path = "/person",produces = "application/abc.com.v2+json")
	public PersonDaoV2 personVersionMediaTypeV2() {
		return new PersonDaoV2("Harsh","Vardhan");
	}
}
