package com.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.report.dao.DAO_Login;
import com.report.model.Credentials;
import com.report.model.Partner;

@Service
@Transactional
public class Service_Login_Imp implements Service_Login {

	@Autowired
	private DAO_Login dao_Login;	

	@Override
	public Credentials getCredentialsByUsername(String username) {
		return dao_Login.getCredentialsByUsername(username);
	}

	@Override
	public boolean updateCredetials(Credentials credentials) {
		return dao_Login.updateCredetials(credentials);
	}

	@Override
	public long saveCredentials(Credentials credentials) {
		return dao_Login.saveCredentials(credentials);
	}

	@Override
	public long savePartnerByCredentials(Partner partner) {
		return dao_Login.savePartnerByCredentials(partner);
	}
	
	

}
