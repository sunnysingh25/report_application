package com.report.dao;

import java.util.List;

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

public interface Admin_DAO {
	
	public boolean updateAdmin(Admin admin);
	
	public boolean updatePartner(Partner partner);
	public Partner getPartnerById(long partner_id);
	
	public List<Object[]> getPartnerList(Condition condition);
	public long getPartnerListCount(Condition condition);
	public List<Object[]> getPartnerListOnly(long partner_id);
	
	public Admin getAdminByEmail(String email);
	public List<Company> getCompanies();
	
	public long saveQualitative(Qualitative qualitative);
	public boolean updateQualitative(Qualitative qualitative);
	public Qualitative getQualitativeById(long id);
	public List<Object[]> getQualitativeInfo(long partner_id,Condition condition);
	public long getQualitativeCount(long partner_id,Condition condition);
	
	public List<Stage> getStages();
	public List<Object[]> getStageDistribution(long id);
	
	public long saveQuantative(Quantitative quantitative);
	public boolean updateQuantative(Quantitative quantitative);
	public Quantitative getQuantativeById(long id);
	public List<Object[]> getQuantativeList(Condition condition,long partner_id);
	public long getQuantativeCount(Condition condition,long partner_id);
	
	public long saveCandidate(CandidateInfo candidateInfo);
	public List<Object[]> getCandidateList(Condition condition,long id);
	public long getCandidateCount(Condition condition,long id);
	
	public Object[] getDashBoardInfo();
	
	public List<Long> getDistinctPartnerRelation(long partner_id);
	public List<Object[]> getGraphData(QuantativeData quantativeData);
	public List<Object[]> getRelatedPartnerList(long partner_id);
	
	public List<Object[]> getQualitativeInfoReport(QuantativeData quantativeData,Condition condition);
	public long getQualitativeCountReport(QuantativeData quantativeData,Condition condition);
	public List<Object[]> getQuantativeListReport(QuantativeData quantativeData,Condition condition);
	public long getQuantativeCountReport(QuantativeData quantativeData,Condition condition);


	
	

}
