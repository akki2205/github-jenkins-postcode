package com.postcode.PostCoode.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.postcode.PostCoode.model.PostCodeResponse;
@Repository
public interface PostCodeRepo extends JpaRepository<PostCodeResponse, Integer> {

	
}
