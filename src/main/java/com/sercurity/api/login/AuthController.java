package com.sercurity.api.login;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sercurity.conf.AuthenticationFilter;
import com.sercurity.conf.JwtUtils;
import com.sercurity.entity.RefreshToken;
import com.sercurity.entity.User;
import com.sercurity.repository.RoleRepository;
import com.sercurity.repository.UserRepository;
import com.sercurity.service.JwtUserDetailsService;
import com.sercurity.service.RefreshTokenService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RefreshTokenService RefreshTokenService;
	
	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	JwtUtils jwtUtils;

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

	@PostMapping("/signin")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
		User user = userRepository.findByUsername(loginRequest.username);
		authenticate(loginRequest.username, loginRequest.password);
		if (user != null) {
			final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.username);
			String jwt = jwtUtils.generateJwtToken(userDetails);
			RefreshToken refreshToken = RefreshTokenService.createRefreshToken(user.getId());
			List<String> roles = user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList());
			return ResponseEntity.ok(
					new LoginResponse(jwt, refreshToken.getToken(), userDetails.getUsername(), user.getEmail(), roles));
		} else {
			logger.error(" user not exits: {}");
		}
		return null;

	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
