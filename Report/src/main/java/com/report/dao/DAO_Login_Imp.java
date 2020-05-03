package com.report.dao;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.report.model.Credentials;
import com.report.model.Partner;

@Repository
public class DAO_Login_Imp implements DAO_Login {

	@Autowired private SessionFactory sessionFactory;
	@Autowired private PasswordEncoder bcryptEncoder;

	@Override
	public Credentials getCredentialsByUsername(String username) {
		Session currentSession=sessionFactory.getCurrentSession();
		Query<Credentials> theQuery = currentSession.createQuery("from Credentials where username=:username",Credentials.class);
		theQuery.setParameter("username",username );
		List<Credentials> list=theQuery.getResultList();
		return list.stream().findFirst().orElse(null);
	}

	@Override
	public boolean updateCredetials(Credentials credentials) {
		boolean status=false;
		try {
			sessionFactory.getCurrentSession().update(credentials);	
			status=true;		
		} 
		catch (ConstraintViolationException e) {
			throw new DuplicateKeyException("Username Exists");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public long saveCredentials(Credentials credentials) {
		long status=0;
		try {
			status=(long) sessionFactory.getCurrentSession().save(credentials);			
		} catch (ConstraintViolationException e) {
			throw new DuplicateKeyException("Username Exists");
		}
		return status;
	}

	@Override
	public long savePartnerByCredentials(Partner partner) {
		long status=0;
		try {
			Credentials credentials=new Credentials();
			credentials.setUsername(partner.getPartner_email());
			credentials.setPassword(bcryptEncoder.encode(partner.getPassword()));
			credentials.setRole("PARTNER");
			partner.setCreated_date(new Date());
			credentials.setPartner(partner);
			credentials.getPartner().setRelation(null);
			status=(long) sessionFactory.getCurrentSession().save(credentials);			
		} catch (ConstraintViolationException e) {
			throw new DuplicateKeyException("Username Exists");
		}
		catch (Exception e) {
			throw new NoSuchElementException("Something went wrong");
		}
		return status;
	}
	
	
}
