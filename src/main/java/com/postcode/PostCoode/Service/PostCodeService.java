package com.postcode.PostCoode.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postcode.PostCoode.Dto.FinalResponseDto;

import com.postcode.PostCoode.Repository.PostCodeRepo;
import com.postcode.PostCoode.model.PostCodeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PostCodeService {
	@Autowired
   private PostCodeRepo postCodeRepo;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
	
	public List<Object> getPostCodeData(String PostCode, String name ) throws JsonMappingException, JsonProcessingException{
		   
		
	/*
	 * Object object =
	 * restTemplate.getForObject("http://api.postcodes.io/postcodes?q=" + PostCode,
	 * Object.class); PostCodeDataDto postCodeDataDto= new PostCodeDataDto();
	 * 
	 * 
	 * PostCodeDataDto savedResponse = modelMapper.map(object,
	 * PostCodeDataDto.class);
	 * 
	 * PostCodeResponse postCodeResponse = new PostCodeResponse();
	 * postCodeResponse.setPostcode(savedResponse.getResults().get(0).getPostcode())
	 * ;
	 * postCodeResponse.setLatitude(savedResponse.getResults().get(0).getLatitude())
	 * ;
	 * postCodeResponse.setLongitude(savedResponse.getResults().get(0).getLongitude(
	 * ));
	 * postCodeResponse.setCountry(savedResponse.getResults().get(0).getCountry());
	 * postCodeResponse.setEastings(savedResponse.getResults().get(0).getEastings())
	 * ;
	 * postCodeResponse.setNorthings(savedResponse.getResults().get(0).getNorthings(
	 * ));
	 * 
	 * postCodeRepo.save(postCodeResponse); return postCodeResponse;
	 * 
	 */
		
		String responseJson =  restTemplate.getForObject("http://api.postcodes.io/postcodes?q=" + PostCode, String.class);
	

		          // Parse the JSON response and extract the relevant data
		          // Assuming you are using Jackson ObjectMapper here
		          ObjectMapper objectMapper = new ObjectMapper();
		          JsonNode rootNode = objectMapper.readTree(responseJson);
		          JsonNode resultNode = rootNode.get("result").get(0);

		          PostCodeResponse postCodeResponse = new PostCodeResponse();
		          
		          postCodeResponse.setName(name);
		          postCodeResponse.setPostcode(resultNode.get("postcode").asText());
		          postCodeResponse.setQuality(resultNode.get("quality").asInt());
		          postCodeResponse.setEastings(resultNode.get("eastings").asInt());
		          postCodeResponse.setNorthings(resultNode.get("northings").asInt());
		          postCodeResponse.setCountry(resultNode.get("country").asText());
		          postCodeResponse.setNhs_ha(resultNode.get("nhs_ha").asText());
		          double latitude = resultNode.get("latitude").asDouble();
		          double longitude = resultNode.get("longitude").asDouble();
		          postCodeResponse.setLatitude(latitude);
		          postCodeResponse.setLongitude(longitude);
		          // Set other properties
		          
		          
		          String sunset = getSunsetTme(postCodeResponse);
		  		
		  		OffsetDateTime sunsetDateTime = OffsetDateTime.parse(sunset, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		          LocalDateTime currentDateTime = LocalDateTime.now(ZoneOffset.UTC);
		          OffsetDateTime currentOffsetDateTime = OffsetDateTime.of(currentDateTime, ZoneOffset.UTC);

		          FinalResponseDto finalResponseDto= new FinalResponseDto();
		  		if(currentOffsetDateTime.isBefore(sunsetDateTime)){
		  			
		  			finalResponseDto.setStatus(HttpStatus.OK);
		  			finalResponseDto.setMessage("Congratulations "+name+", you are allowed");
		  			
		  		}else {
		  			finalResponseDto.setStatus(HttpStatus.FORBIDDEN);
		  			finalResponseDto.setMessage("Sorry "+name+", you are not allowed");
		  		}
		  		

		          // Save the address using the repository
		          postCodeRepo.save(postCodeResponse);
		          List<Object> instances = new ArrayList<>();
		          instances.add(finalResponseDto);
		          instances.add(postCodeResponse);
		          
		          return instances;
		          
		      }
	
	private String getSunsetTme(PostCodeResponse postCodeResponse) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String date = LocalDate.now().format(formatter);
		
		Object objects1 =restTemplate.getForObject("https://api.sunrise-sunset.org/json?lat="+postCodeResponse.getLatitude()+"&lng="+postCodeResponse.getLongitude()+"&date="+date+"&formatted="+0, Object.class);
		
		Map<String, Object> responseMap = (Map<String, Object>) objects1;

		Map<String, Object> resultsMap = (Map<String, Object>) responseMap.get("results");

		return (String) resultsMap.get("sunset");
	}
		          
		      
		  

		  
		  
		  
		  
	

}
