package com.report.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.report.dao.DAO_Login;
import com.report.model.Credentials;


@Service
@Transactional
public class UserDetailService implements UserDetailsService{
	
	@Autowired  private DAO_Login login_dao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<GrantedAuthority> authorityList = new ArrayList<>();
		String password=null;
		Credentials credentials= Optional.ofNullable(login_dao.getCredentialsByUsername(username))
				  .orElseThrow(() -> new DisabledException("Username Not Found"));		
		
		if(credentials.getRole().equals("ADMIN")) {
			password=credentials.getPassword();
			authorityList.add(new SimpleGrantedAuthority("ADMIN"));		
			authorityList.add(new SimpleGrantedAuthority(String.valueOf(credentials.getAdmin().getAdmin_id())));
		}
		
		if(credentials.getRole().equals("PARTNER")) {
			if(!credentials.getPartner().isStatus()) {
				throw new DisabledException("Status Disabled For " + username);
			}
			password=credentials.getPassword();
			authorityList.add(new SimpleGrantedAuthority("PARTNER"));		
			authorityList.add(new SimpleGrantedAuthority(String.valueOf(credentials.getPartner().getPartner_id())));
		}		

		return new org.springframework.security.core.userdetails.User(username,password,authorityList);
	}
	
}
