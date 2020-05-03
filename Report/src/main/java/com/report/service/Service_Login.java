package com.report.service;

import java.util.List;
import com.report.model.Credentials;
import com.report.model.Partner;

public interface Service_Login {
	
    public Credentials getCredentialsByUsername(String username);
    public boolean updateCredetials(Credentials credentials);
    public long saveCredentials(Credentials credentials);
    
    public long savePartnerByCredentials(Partner partner);


}
