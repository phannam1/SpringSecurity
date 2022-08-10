package com.sercurity.api.getall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sercurity.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api")
@PreAuthorize("isAuthenticated()")
public class GetAllProcess {
	@Autowired
	UserRepository userRepository;

	@GetMapping(value = "/users")
	@ResponseBody
	GetAllResponse getAll() {
		GetAllResponse res = new GetAllResponse();
		res.lsUser = userRepository.findAll() ;
		return res;
	}
}
