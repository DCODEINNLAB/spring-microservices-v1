package com.example.simplewebservice.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.simplewebservice.beans.HelloWorldBean;

@RestController
public class SimpleController {
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(method=RequestMethod.GET, path="/hello-world")
	public String helloWorld() {
		return "Hello World!!";
	}

	@GetMapping("/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World!!!");
	}
	
	@GetMapping("/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		//return new HelloWorldBean("Hello World - "+name);
		return new HelloWorldBean(String.format("Hello World - %s",name));
	} 
	
	@GetMapping("/i18n")
	public String messageI18n() {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message", null, "Good Morning", locale);
	}
}
