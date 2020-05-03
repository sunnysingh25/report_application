package com.report.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="quantitative")
public class Quantitative {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String company_name;
	private String position_name;
	
	@OneToMany(mappedBy="quantitative",fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
	@JsonIgnoreProperties("quantitative")
	private List<CandidateInfo> candidateinfo;
	
	private long mapping_count;	
	private String comment;
	@Temporal(TemporalType.DATE)
	private Date created_date;
	@Temporal(TemporalType.DATE)
	private Date updated_date;
	@OneToOne
	@JoinColumn(name="partner_id")
	@JsonIgnoreProperties("relation")
	private Partner partner;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getPosition_name() {
		return position_name;
	}
	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}
	public List<CandidateInfo> getCandidateinfo() {
		return candidateinfo;
	}
	public void setCandidateinfo(List<CandidateInfo> candidateinfo) {
		this.candidateinfo = candidateinfo;
	}
	public long getMapping_count() {
		return mapping_count;
	}
	public void setMapping_count(long mapping_count) {
		this.mapping_count = mapping_count;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public Date getUpdated_date() {
		return updated_date;
	}
	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}
	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	
	
	
	

}
