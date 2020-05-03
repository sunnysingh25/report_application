package com.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.report.dao.Admin_DAO;
import com.report.miscallaneous.Condition;
import com.report.miscallaneous.GraphData;
import com.report.miscallaneous.QuantativeData;
import com.report.model.Admin;
import com.report.model.CandidateInfo;
import com.report.model.Company;
import com.report.model.Partner;
import com.report.model.Qualitative;
import com.report.model.Quantitative;
import com.report.model.Stage;

@Service
@Transactional
public class Admin_Service_Imp implements Admin_Service{
	
	@Autowired private Admin_DAO admin_dao;

	@Override
	public boolean updateAdmin(Admin admin) {
		return admin_dao.updateAdmin(admin);
	}

	@Override
	public boolean updatePartner(Partner partner) {
		return admin_dao.updatePartner(partner);
	}

	@Override
	public Partner getPartnerById(long partner_id) {
		return admin_dao.getPartnerById(partner_id);
	}	
	
	
	@Override
	public List<Object[]> getPartnerList(Condition condition) {
		return admin_dao.getPartnerList(condition);
	}

	@Override
	public long getPartnerListCount(Condition condition) {
		return admin_dao.getPartnerListCount(condition);
	}

	@Override
	public Admin getAdminByEmail(String email) {
		return admin_dao.getAdminByEmail(email);
	}

	@Override
	public List<Company> getCompanies() {
		return admin_dao.getCompanies();
	}

	@Override
	public List<Object[]> getPartnerListOnly(long partner_id) {
		return admin_dao.getPartnerListOnly(partner_id);
	}

	@Override
	public long saveQualitative(Qualitative qualitative) {
		return admin_dao.saveQualitative(qualitative);
	}	

	@Override
	public boolean updateQualitative(Qualitative qualitative) {
		return admin_dao.updateQualitative(qualitative);
	}

	@Override
	public Qualitative getQualitativeById(long id) {
		return admin_dao.getQualitativeById(id);
	}

	@Override
	public List<Object[]> getQualitativeInfo(long partner_id, Condition condition) {
		return admin_dao.getQualitativeInfo(partner_id, condition);
	}

	@Override
	public long getQualitativeCount(long partner_id, Condition condition) {
		return admin_dao.getQualitativeCount(partner_id, condition);
	}

	@Override
	public long saveQuantative(Quantitative quantitative) {
		return admin_dao.saveQuantative(quantitative);
	}

	@Override
	public boolean updateQuantative(Quantitative quantitative) {
		return admin_dao.updateQuantative(quantitative);
	}

	@Override
	public Quantitative getQuantativeById(long id) {
		return admin_dao.getQuantativeById(id);
	}

	@Override
	public List<Object[]> getQuantativeList(Condition condition, long partner_id) {
		return admin_dao.getQuantativeList(condition, partner_id);
	}

	@Override
	public long getQuantativeCount(Condition condition, long partner_id) {
		return admin_dao.getQuantativeCount(condition, partner_id);
	}

	@Override
	public List<Stage> getStages() {
		return admin_dao.getStages();
	}

	@Override
	public List<Object[]> getStageDistribution(long id) {
		return admin_dao.getStageDistribution(id);
	}

	@Override
	public long saveCandidate(CandidateInfo candidateInfo) {
		return admin_dao.saveCandidate(candidateInfo);
	}

	@Override
	public List<Object[]> getCandidateList(Condition condition, long id) {
		return admin_dao.getCandidateList(condition, id);
	}

	@Override
	public long getCandidateCount(Condition condition,long id) {
		return admin_dao.getCandidateCount(condition,id);
	}

	@Override
	public Object[] getDashBoardInfo() {
		return admin_dao.getDashBoardInfo();
	}

	@Override
	public List<Long> getDistinctPartnerRelation(long partner_id) {
		return admin_dao.getDistinctPartnerRelation(partner_id);
	}

	@Override
	public List<Object[]> getGraphData(QuantativeData quantativeData) {
		return admin_dao.getGraphData(quantativeData);
	}

	@Override
	public List<Object[]> getRelatedPartnerList(long partner_id) {
		return admin_dao.getRelatedPartnerList(partner_id);
	}

	@Override
	public List<Object[]> getQualitativeInfoReport(QuantativeData quantativeData,Condition condition) {
		return admin_dao.getQualitativeInfoReport(quantativeData,condition);
	}

	@Override
	public long getQualitativeCountReport(QuantativeData quantativeData,Condition condition) {
		return admin_dao.getQualitativeCountReport(quantativeData,condition);
	}

	@Override
	public List<Object[]> getQuantativeListReport(QuantativeData quantativeData,Condition condition) {
		return admin_dao.getQuantativeListReport(quantativeData,condition);
	}

	@Override
	public long getQuantativeCountReport(QuantativeData quantativeData,Condition condition) {
		return admin_dao.getQuantativeCountReport(quantativeData,condition);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
