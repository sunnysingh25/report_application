package com.report.miscallaneous;

import java.util.Date;

public class QuantativeData {
	
	private long partner_id;
	private boolean team_status;
	private boolean admin_status;
	private Date from_date;
	private Date to_date;
	private Condition condition;
	public long getPartner_id() {
		return partner_id;
	}
	public void setPartner_id(long partner_id) {
		this.partner_id = partner_id;
	}
	public boolean isTeam_status() {
		return team_status;
	}
	public void setTeam_status(boolean team_status) {
		this.team_status = team_status;
	}
	public Date getFrom_date() {
		return from_date;
	}
	public void setFrom_date(Date from_date) {
		this.from_date = from_date;
	}
	public Date getTo_date() {
		return to_date;
	}
	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}
	public boolean isAdmin_status() {
		return admin_status;
	}
	public void setAdmin_status(boolean admin_status) {
		this.admin_status = admin_status;
	}
	public Condition getCondition() {
		return condition;
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	
	
	
	
	
	
}
