package com.report.controller;

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

import com.report.miscallaneous.JwtResponse;
import com.report.model.Partner;
import com.report.security.JwtTokenUtil;
import com.report.service.UserDetailService;

import io.swagger.annotations.*;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"},allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/report")
@Api(value="Login Controller", description="Controller to perform login operations")
public class Login_Controller {

	@Autowired
	private UserDetailService partner_detial_service;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;	
	
	@PostMapping("validate")
	@ApiOperation(value = "Validate Login Request", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Something went wrong")
    })
	public ResponseEntity<?> validate(@RequestBody String[] credentials) throws Exception{
		authenticate(credentials[0], credentials[1]);		
		final UserDetails userDetails = partner_detial_service.loadUserByUsername(credentials[0]);
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception,BadCredentialsException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) { 
			throw new Exception();
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid Credentials");
		}		
	}

}
