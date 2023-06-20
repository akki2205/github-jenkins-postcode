package com.postcode.PostCoode.Dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FinalResponseDto {

	private HttpStatus status;
	private String Message;
	
}
