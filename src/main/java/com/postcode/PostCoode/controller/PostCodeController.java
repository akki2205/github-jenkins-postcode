package com.postcode.PostCoode.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.postcode.PostCoode.Service.PostCodeService;
import com.postcode.PostCoode.model.PostCodeResponse;

@RestController
@RequestMapping("/postcode")
public class PostCodeController {
	
	@Autowired
	private PostCodeService postCodeService;
	
	@GetMapping("/get")
	public  List<Object>  getPostCodeDetails(@RequestParam String postcode ,@RequestParam String name ) throws JsonMappingException, JsonProcessingException {
		List<Object> postCodeData = postCodeService.getPostCodeData(postcode, name);
		return postCodeData;
	}
}
