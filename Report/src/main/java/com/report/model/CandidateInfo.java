package com.report.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="candidateinfo")
public class CandidateInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long candidate_id;
	private String candidate_name;
	@Temporal(TemporalType.DATE)
	private Date created_date;
	@Temporal(TemporalType.DATE)
	private Date updated_date;
	@OneToOne
	@JoinColumn(name="stage_id")
	private Stage stage;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.ALL)
	@JsonIgnoreProperties("candidateinfo")
    @JoinColumn(name="quantitative_id",nullable=false)
	private Quantitative quantitative;
	
	public long getCandidate_id() {
		return candidate_id;
	}
	public void setCandidate_id(long candidate_id) {
		this.candidate_id = candidate_id;
	}
	public String getCandidate_name() {
		return candidate_name;
	}
	public void setCandidate_name(String candidate_name) {
		this.candidate_name = candidate_name;
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
	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	public Quantitative getQuantitative() {
		return quantitative;
	}
	public void setQuantitative(Quantitative quantitative) {
		this.quantitative = quantitative;
	}
	
	
	

}
