package com.report.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="stage")
public class Stage {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long stage_id;
	private String stage_name;
	public long getStage_id() {
		return stage_id;
	}
	public void setStage_id(long stage_id) {
		this.stage_id = stage_id;
	}
	public String getStage_name() {
		return stage_name;
	}
	public void setStage_name(String stage_name) {
		this.stage_name = stage_name;
	}
	
	

}
