package com.report.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import com.report.miscallaneous.Condition;
import com.report.miscallaneous.GraphData;
import com.report.miscallaneous.QuantativeData;
import com.report.model.Admin;
import com.report.model.CandidateInfo;
import com.report.model.Company;
import com.report.model.Partner;
import com.report.model.Qualitative;
import com.report.model.Quantitative;
import com.report.model.Relation;
import com.report.model.Stage;
import com.report.service.Admin_Service;

@Repository
public class Admin_DAO_Imp implements Admin_DAO{

	@Autowired private SessionFactory sessionFactory;
	@Autowired private Admin_Service admin_service;

	@Override
	public boolean updateAdmin(Admin admin) {
		boolean status=false;
		try {sessionFactory.getCurrentSession().update(admin);	
			status=true;		
		} 
		catch (ConstraintViolationException e) {
			throw new DuplicateKeyException("Username Exists");
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchElementException("Something went wrong");
		}
		return status;

	}

	@Override
	public boolean updatePartner(Partner partner) {
		boolean status=false;
		try {
			if(partner.getRelation().size()>0) {
				partner.getRelation().forEach(x -> x.setMaster_partner(partner));
			}else {
				 partner.setRelation(null);
			}
			partner.setUpdated_date(new Date());
			sessionFactory.getCurrentSession().update(partner);	
			status=true;		
		} 
		catch (ConstraintViolationException e) {
			throw new DuplicateKeyException("Username Exists");
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchElementException("Something went wrong");
		}
		return status;
	}

	@Override
	public Partner getPartnerById(long partner_id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Partner> theQuery = currentSession.createQuery("from Partner where partner_id=:partner_id",Partner.class);
		theQuery.setParameter("partner_id", partner_id);
		return theQuery.getResultList().stream().findFirst().orElse(null);
	}

	
	@Override
	public List<Object[]> getPartnerList(Condition condition) {CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();
	CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
	Root<Partner> pRoot = criteriaQuery.from(Partner.class);		
	criteriaQuery.multiselect(pRoot.get("partner_id"),
							 pRoot.get("partner_name"),
							 pRoot.get("partner_email"),
							 pRoot.get("contact_number"),
							 pRoot.get("branch"),
							 pRoot.get("status"));
	List <Predicate> p = new ArrayList <Predicate> ();
	if(condition.getText().length()>3) {
		p.add(criteriaBuilder.or(criteriaBuilder.like(pRoot.get("partner_name"), condition.getText()+"%"),
				criteriaBuilder.like(pRoot.get("partner_email"),condition.getText()+"%")));
	}
	if(!p.isEmpty()){
		Predicate[] pr = new Predicate[p.size()];
		p.toArray(pr);
		criteriaQuery.where(pr);    
	}
	criteriaQuery.orderBy(criteriaBuilder.desc(pRoot.get("partner_id")));
	return sessionFactory.getCurrentSession().createQuery(criteriaQuery).setMaxResults(condition.getPagesize()).
			setFirstResult((condition.getIndex()-1)*condition.getPagesize()).getResultList();	

	}

	@Override
	public long getPartnerListCount(Condition condition) {
		CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Partner> pRoot = criteriaQuery.from(Partner.class);
		criteriaQuery.select(criteriaBuilder.count(pRoot));	
		criteriaQuery.distinct(true);		
		List<Predicate> p = new ArrayList <Predicate>();
		if(condition.getText().length()>3) {
			p.add(criteriaBuilder.or(criteriaBuilder.like(pRoot.get("partner_name"), condition.getText()+"%"),
					criteriaBuilder.like(pRoot.get("partner_email"),condition.getText()+"%")));
		}	
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}	
		return sessionFactory.getCurrentSession().createQuery(criteriaQuery).getSingleResult();
	}

	@Override
	public Admin getAdminByEmail(String email) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Admin> theQuery = currentSession.createQuery("from Admin where username=:username",Admin.class);
		theQuery.setParameter("username", email);
		return theQuery.getResultList().stream().findFirst().orElse(null);	
	}

	@Override
	public List<Company> getCompanies() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Company> theQuery = currentSession.createQuery("from Company",Company.class);
		return theQuery.getResultList();	
	}

	@Override
	public List<Object[]> getPartnerListOnly(long partner_id) {
		Session currentSession = sessionFactory.getCurrentSession();
		String sql="SELECT partner_id,partner_name FROM partner where status=true and partner_id <> '"+partner_id+"'";		
		Query query = currentSession.createSQLQuery(sql);
		List<Object[]> list=query.getResultList();
		return list;
	}

	@Override
	public long saveQualitative(Qualitative qualitative) {
		long status=0;
		try {			
			qualitative.setCreated_date(new Date());
			status=(long) sessionFactory.getCurrentSession().save(qualitative);
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchElementException("Something went wrong");
		}
		return status;
	}	

	@Override
	public boolean updateQualitative(Qualitative qualitative) {
		boolean status=false;
		try {
			qualitative.setUpdated_date(new Date());
			sessionFactory.getCurrentSession().update(qualitative);	
			status=true;		
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchElementException("Something went wrong");
		}
		return status;
	}

	@Override
	public Qualitative getQualitativeById(long id) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Qualitative> theQuery = currentSession.createQuery("from Qualitative where id=:id",Qualitative.class);
		theQuery.setParameter("id", id);
		return theQuery.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<Object[]> getQualitativeInfo(long partner_id, Condition condition) {
		CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Qualitative> pRoot = criteriaQuery.from(Qualitative.class);		
		criteriaQuery.multiselect(pRoot.get("id"),
								 pRoot.get("partner").get("partner_id"),
								 pRoot.get("partner").get("partner_name"),
								 pRoot.get("title"),
								 pRoot.get("created_date"),
								 pRoot.get("updated_date"));
		List <Predicate> p = new ArrayList <Predicate> ();
		if(partner_id!=0) {
			p.add(criteriaBuilder.equal(pRoot.get("partner").get("partner_id"),partner_id));
		}
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}
		criteriaQuery.orderBy(criteriaBuilder.desc(pRoot.get("id")));
		return sessionFactory.getCurrentSession().createQuery(criteriaQuery).setMaxResults(condition.getPagesize()).
				setFirstResult((condition.getIndex()-1)*condition.getPagesize()).getResultList();	

	}

	@Override
	public long getQualitativeCount(long partner_id, Condition condition) {
		CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Qualitative> pRoot = criteriaQuery.from(Qualitative.class);
		criteriaQuery.select(criteriaBuilder.count(pRoot));	
		criteriaQuery.distinct(true);		
		List<Predicate> p = new ArrayList <Predicate>();
		if(partner_id!=0) {
			p.add(criteriaBuilder.equal(pRoot.get("partner").get("partner_id"),partner_id));
		}
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}	
		return sessionFactory.getCurrentSession().createQuery(criteriaQuery).getSingleResult();
	}

	@Override
	public long saveQuantative(Quantitative quantitative) {
		long status=0;
		try {			
			quantitative.setCreated_date(new Date());
			quantitative.getCandidateinfo().forEach(x -> x.setQuantitative(quantitative));
			quantitative.getCandidateinfo().forEach(x -> x.setCreated_date(new Date()));
			status=(long) sessionFactory.getCurrentSession().save(quantitative);
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchElementException("Something went wrong");
		}
		return status;
	}

	@Override
	public boolean updateQuantative(Quantitative quantitative) {
		boolean status=false;
		try {
			quantitative.setUpdated_date(new Date());
			quantitative.getCandidateinfo().forEach(x -> x.setCreated_date(new Date()));
			quantitative.getCandidateinfo().forEach(x -> x.setQuantitative(quantitative));
			sessionFactory.getCurrentSession().update(quantitative);
			status=true;		
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchElementException("Something went wrong");
		}
		return status;
	}

	@Override
	public Quantitative getQuantativeById(long id) {
		CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Quantitative> pRoot = criteriaQuery.from(Quantitative.class);		
		criteriaQuery.multiselect(pRoot.get("id"),
								 pRoot.get("company_name"),
								 pRoot.get("position_name"),
								 pRoot.get("mapping_count"),
								 pRoot.get("comment"),
								 pRoot.get("created_date"),
								 pRoot.get("updated_date"),
								 pRoot.get("partner").get("partner_id"));
		List <Predicate> p = new ArrayList <Predicate> ();
		if(id!=0) {
			p.add(criteriaBuilder.equal(pRoot.get("id"),id));
		}
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}
		Object[] objects=sessionFactory.getCurrentSession().
						createQuery(criteriaQuery).getResultList().stream().
						findFirst().orElseThrow(() -> new NoSuchElementException("Invalid Request"));
		Quantitative quantitative=new Quantitative();
		quantitative.setId(Long.valueOf(objects[0].toString()));
		quantitative.setCompany_name(objects[1].toString());
		quantitative.setPosition_name(objects[2].toString());
		quantitative.setMapping_count(Long.valueOf(objects[3].toString()));
		quantitative.setComment(objects[4].toString());
		quantitative.setCreated_date((Date)objects[5]);
		quantitative.setUpdated_date((Date)objects[6]);
		quantitative.setPartner(new Partner(Long.valueOf(objects[7].toString())));
		quantitative.setCandidateinfo(new ArrayList<CandidateInfo>());
		return quantitative;	

	}

	@Override
	public List<Object[]> getQuantativeList(Condition condition, long partner_id) {
		CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Quantitative> pRoot = criteriaQuery.from(Quantitative.class);			
		criteriaQuery.multiselect(pRoot.get("id"),
								 pRoot.get("company_name"),
								 pRoot.get("position_name"),
								 pRoot.get("mapping_count"),
								 pRoot.get("comment"),
								 pRoot.get("created_date"),
								 pRoot.get("updated_date"),
								 pRoot.get("partner").get("partner_id"));
		List <Predicate> p = new ArrayList <Predicate> ();
		if(partner_id!=0) {
			p.add(criteriaBuilder.equal(pRoot.get("partner").get("partner_id"),partner_id));
		}
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}
		criteriaQuery.orderBy(criteriaBuilder.desc(pRoot.get("id")));
		return sessionFactory.getCurrentSession().createQuery(criteriaQuery).setMaxResults(condition.getPagesize()).
				setFirstResult((condition.getIndex()-1)*condition.getPagesize()).getResultList();	


	}

	@Override
	public long getQuantativeCount(Condition condition, long partner_id) {
		CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Quantitative> pRoot = criteriaQuery.from(Quantitative.class);
		criteriaQuery.select(criteriaBuilder.count(pRoot));	
		criteriaQuery.distinct(true);		
		List<Predicate> p = new ArrayList <Predicate>();
		if(partner_id!=0) {
			p.add(criteriaBuilder.equal(pRoot.get("partner").get("partner_id"),partner_id));
		}
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}	
		return sessionFactory.getCurrentSession().createQuery(criteriaQuery).getSingleResult();
	}

	@Override
	public List<Stage> getStages() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Stage> theQuery = currentSession.createQuery("from Stage",Stage.class);
		return theQuery.getResultList();
	}

	@Override
	public List<Object[]> getStageDistribution(long id) {
		Session currentSession = sessionFactory.getCurrentSession();
		String sql = "SELECT \r\n" + 
				"(select count(candidate_id) from candidateinfo where stage_id=1 and quantitative_id='"+id+"') as cvsent,\r\n" + 
				"(select count(candidate_id) from candidateinfo where stage_id=2 and quantitative_id='"+id+"') as interview,\r\n" + 
				"(select count(candidate_id) from candidateinfo where stage_id=3 and quantitative_id='"+id+"') as offer,\r\n" + 
				"(select count(candidate_id) from candidateinfo where stage_id=4 and quantitative_id='"+id+"') as joining\r\n" + 
				" FROM dual" ;
		Query query = currentSession.createSQLQuery(sql);
		List<Object[]> list=query.setMaxResults(5).setFirstResult(0).getResultList();
		return list;
	}

	@Override
	public long saveCandidate(CandidateInfo candidateInfo) {
		long status=0;
		try {
			candidateInfo.setCreated_date(new Date());
			status=(long) sessionFactory.getCurrentSession().save(candidateInfo);
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new NoSuchElementException("Something went wrong");
		}
		return status;
	}

	@Override
	public List<Object[]> getCandidateList(Condition condition, long id) {
		CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<CandidateInfo> pRoot = criteriaQuery.from(CandidateInfo.class);			
		criteriaQuery.multiselect(pRoot.get("candidate_id"),
								 pRoot.get("candidate_name"),
								 pRoot.get("stage").get("stage_id"),
								 pRoot.get("stage").get("stage_name"),
								 pRoot.get("created_date"),
								 pRoot.get("updated_date"));
		List <Predicate> p = new ArrayList <Predicate> ();
		if(id!=0) {
			p.add(criteriaBuilder.equal(pRoot.get("quantitative").get("id"),id));
		}
		
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}
		criteriaQuery.orderBy(criteriaBuilder.desc(pRoot.get("candidate_id")));
		return sessionFactory.getCurrentSession().createQuery(criteriaQuery).setMaxResults(condition.getPagesize()).
				setFirstResult((condition.getIndex()-1)*condition.getPagesize()).getResultList();	

	}

	@Override
	public long getCandidateCount(Condition condition, long id) {
		CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<CandidateInfo> pRoot = criteriaQuery.from(CandidateInfo.class);
		criteriaQuery.select(criteriaBuilder.count(pRoot));	
		criteriaQuery.distinct(true);		
		List<Predicate> p = new ArrayList <Predicate>();
		if(id!=0) {
			p.add(criteriaBuilder.equal(pRoot.get("quantitative").get("id"),id));
		}
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}	
		return sessionFactory.getCurrentSession().createQuery(criteriaQuery).getSingleResult();
	
	}

	@Override
	public Object[] getDashBoardInfo() {
		Session currentSession = sessionFactory.getCurrentSession();
		String sql = "select (select count(partner_id) from partner where status=true) as active_partners,\r\n" + 
				"(select count(distinct(quantitative.partner_id))  from candidateinfo inner join quantitative\r\n" + 
				"on candidateinfo.quantitative_id= quantitative.id and candidateinfo.created_date=CURDATE())\r\n" + 
				"as submissions from dual" ;
		Query query = currentSession.createSQLQuery(sql);
		return (Object[]) query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<Long> getDistinctPartnerRelation(long partner_id) {
		Session currentSession = sessionFactory.getCurrentSession();
		String sql = "SELECT distinct slave_partner_id from relation where master_partner_id='"+partner_id+"'" ;
		Query query = currentSession.createSQLQuery(sql);
		List<Long> list=query.getResultList();
		list.add(partner_id);
		System.out.println(list);
		return list;
	}

	@Override
	public List<Object[]> getGraphData(QuantativeData quantativeData) {
		CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<CandidateInfo> pRoot = criteriaQuery.from(CandidateInfo.class);
		criteriaQuery.multiselect(pRoot.get("stage").get("stage_id"),
								  pRoot.get("stage").get("stage_name"),
								 criteriaBuilder.countDistinct(pRoot.get("candidate_id")));
		List <Predicate> p = new ArrayList <Predicate> ();
		
		if(!quantativeData.isTeam_status() && quantativeData.isAdmin_status()) {
			p.add(criteriaBuilder.equal(pRoot.get("quantitative").get("partner").get("partner_id"),quantativeData.getPartner_id()));
		}
		
		if(!quantativeData.isAdmin_status() && !quantativeData.isTeam_status()) {
			p.add(criteriaBuilder.equal(pRoot.get("quantitative").get("partner").get("partner_id"),quantativeData.getPartner_id()));
		}
		if(!quantativeData.isAdmin_status() && quantativeData.isTeam_status()) {
			p.add(pRoot.get("quantitative").get("partner").get("partner_id").in(admin_service.
					getDistinctPartnerRelation(quantativeData.getPartner_id())));
		}
		
		if(quantativeData.getFrom_date()==null && quantativeData.getTo_date()==null) {
			p.add(criteriaBuilder.equal(pRoot.get("created_date"),new Date()));			
		}
		if(quantativeData.getFrom_date()!=null && quantativeData.getTo_date()!=null) {
			p.add(criteriaBuilder.between(pRoot.get("created_date"),quantativeData.getFrom_date(),quantativeData.getTo_date()));			
		}
		
		criteriaQuery.groupBy(pRoot.get("stage").get("stage_id"));
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}
		return sessionFactory.getCurrentSession().createQuery(criteriaQuery).getResultList();	
	}

	@Override
	public List<Object[]> getRelatedPartnerList(long partner_id) {
		CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Relation> pRoot = criteriaQuery.from(Relation.class);
		criteriaQuery.multiselect(pRoot.get("slave_partner").get("partner_id"),
				pRoot.get("slave_partner").get("partner_name"));
		List <Predicate> p = new ArrayList <Predicate> ();
		p.add(criteriaBuilder.equal(pRoot.get("master_partner").get("partner_id"),partner_id));

		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}
		return sessionFactory.getCurrentSession().createQuery(criteriaQuery).getResultList();
	}

	@Override
	public List<Object[]> getQualitativeInfoReport(QuantativeData quantativeData,Condition condition) {
		CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Qualitative> pRoot = criteriaQuery.from(Qualitative.class);		
		criteriaQuery.multiselect(pRoot.get("id"),
								 pRoot.get("partner").get("partner_id"),
								 pRoot.get("partner").get("partner_name"),
								 pRoot.get("title"),
								 pRoot.get("created_date"),
								 pRoot.get("updated_date"));
		List <Predicate> p = new ArrayList <Predicate> ();
		if(quantativeData.getPartner_id()!=0) {
			p.add(criteriaBuilder.equal(pRoot.get("partner").get("partner_id"),quantativeData.getPartner_id()));
		}
		if(quantativeData.getFrom_date()!=null && quantativeData.getTo_date()!=null) {
			p.add(criteriaBuilder.between(pRoot.get("created_date"),quantativeData.getFrom_date(),quantativeData.getTo_date()));			
		}
		
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}
		criteriaQuery.orderBy(criteriaBuilder.desc(pRoot.get("id")));
		return sessionFactory.getCurrentSession().createQuery(criteriaQuery).setMaxResults(condition.getPagesize()).
				setFirstResult((condition.getIndex()-1)*condition.getPagesize()).getResultList();	

	}

	@Override
	public long getQualitativeCountReport(QuantativeData quantativeData,Condition condition) {
		CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Qualitative> pRoot = criteriaQuery.from(Qualitative.class);
		criteriaQuery.select(criteriaBuilder.count(pRoot));	
		criteriaQuery.distinct(true);		
		List<Predicate> p = new ArrayList <Predicate>();
		if(quantativeData.getPartner_id()!=0) {
			p.add(criteriaBuilder.equal(pRoot.get("partner").get("partner_id"),quantativeData.getPartner_id()));
		}
		if(quantativeData.getFrom_date()!=null && quantativeData.getTo_date()!=null) {
			p.add(criteriaBuilder.between(pRoot.get("created_date"),quantativeData.getFrom_date(),quantativeData.getTo_date()));			
		}
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}	
		return sessionFactory.getCurrentSession().createQuery(criteriaQuery).getSingleResult();
	
	}

	@Override
	public List<Object[]> getQuantativeListReport(QuantativeData quantativeData,Condition condition) {
		CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Quantitative> pRoot = criteriaQuery.from(Quantitative.class);			
		criteriaQuery.multiselect(pRoot.get("id"),
								 pRoot.get("company_name"),
								 pRoot.get("position_name"),
								 pRoot.get("mapping_count"),
								 pRoot.get("comment"),
								 pRoot.get("created_date"),
								 pRoot.get("updated_date"),
								 pRoot.get("partner").get("partner_id"));
		List <Predicate> p = new ArrayList <Predicate> ();
		if(quantativeData.getPartner_id()!=0) {
			p.add(criteriaBuilder.equal(pRoot.get("partner").get("partner_id"),quantativeData.getPartner_id()));
		}
		if(quantativeData.getFrom_date()!=null && quantativeData.getTo_date()!=null) {
			p.add(criteriaBuilder.between(pRoot.get("created_date"),quantativeData.getFrom_date(),quantativeData.getTo_date()));			
		}
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}
		criteriaQuery.orderBy(criteriaBuilder.desc(pRoot.get("id")));
		return sessionFactory.getCurrentSession().createQuery(criteriaQuery).setMaxResults(condition.getPagesize()).
				setFirstResult((condition.getIndex()-1)*condition.getPagesize()).getResultList();	

	}

	@Override
	public long getQuantativeCountReport(QuantativeData quantativeData,Condition condition) {
		CriteriaBuilder criteriaBuilder = sessionFactory.getCurrentSession().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Quantitative> pRoot = criteriaQuery.from(Quantitative.class);
		criteriaQuery.select(criteriaBuilder.count(pRoot));	
		criteriaQuery.distinct(true);		
		List<Predicate> p = new ArrayList <Predicate>();
		if(quantativeData.getPartner_id()!=0) {
			p.add(criteriaBuilder.equal(pRoot.get("partner").get("partner_id"),quantativeData.getPartner_id()));
		}
		if(quantativeData.getFrom_date()!=null && quantativeData.getTo_date()!=null) {
			p.add(criteriaBuilder.between(pRoot.get("created_date"),quantativeData.getFrom_date(),quantativeData.getTo_date()));			
		}
		if(!p.isEmpty()){
			Predicate[] pr = new Predicate[p.size()];
			p.toArray(pr);
			criteriaQuery.where(pr);    
		}	
		return sessionFactory.getCurrentSession().createQuery(criteriaQuery).getSingleResult();
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
