package com.report.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="relation",uniqueConstraints=
@UniqueConstraint(columnNames={"master_partner_id", "slave_partner_id"}))
public class Relation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.ALL)
	@JsonIgnoreProperties("relation")
    @JoinColumn(name="master_partner_id",nullable=false)
	private Partner master_partner;
	
	@NotNull
	@OneToOne
	@JoinColumn(name="slave_partner_id")
	@JsonIgnoreProperties("relation")
	private Partner slave_partner;
	
	private boolean status;	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Partner getMaster_partner() {
		return master_partner;
	}
	public void setMaster_partner(Partner master_partner) {
		this.master_partner = master_partner;
	}
	public Partner getSlave_partner() {
		return slave_partner;
	}
	public void setSlave_partner(Partner slave_partner) {
		this.slave_partner = slave_partner;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
	

}
