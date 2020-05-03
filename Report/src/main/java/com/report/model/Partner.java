package com.report.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="partner")
public class Partner {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long partner_id;
	@NotNull
	private String partner_name;
	@NotNull
	@Column(unique=true)
	private String partner_email;
	
	@Transient
	private String password;
	
	@NotNull
	private String contact_number;
	@NotNull
	private String branch;
	@NotNull
	private boolean status;
	
	@OneToOne
	@JoinColumn(name="company_id")
	private Company company;
	
	@Temporal(TemporalType.DATE)
	private Date created_date;
	@Temporal(TemporalType.DATE)
	private Date updated_date;
	
	private String profile_pic;
	
	@OneToMany(mappedBy="master_partner",fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval = true)
	@JsonIgnoreProperties("master_partner")
	private List<Relation> relation;
	
	public Partner() {
		super();
	}	
	
	public Partner(long partner_id) {
		this.partner_id = partner_id;
	}

	public long getPartner_id() {
		return partner_id;
	}
	public void setPartner_id(long partner_id) {
		this.partner_id = partner_id;
	}
	public String getPartner_name() {
		return partner_name;
	}
	public void setPartner_name(String partner_name) {
		this.partner_name = partner_name;
	}
	public String getPartner_email() {
		return partner_email;
	}
	public void setPartner_email(String partner_email) {
		this.partner_email = partner_email;
	}
	public String getContact_number() {
		return contact_number;
	}
	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
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
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	
	public List<Relation> getRelation() {
		return relation;
	}
	public void setRelation(List<Relation> relation) {
		this.relation = relation;
	}
	public String getProfile_pic() {
		return profile_pic;
	}
	public void setProfile_pic(String profile_pic) {
		this.profile_pic = profile_pic;
	}
	
	
	
	
	
	
	

	
}
