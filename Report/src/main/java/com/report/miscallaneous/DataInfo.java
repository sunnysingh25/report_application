package com.report.miscallaneous;

import java.util.List;

public class DataInfo {
	
	private List<Object[]> data;
	private long length;
	public List<Object[]> getData() {
		return data;
	}
	public void setData(List<Object[]> data) {
		this.data = data;
	}
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	public DataInfo(List<Object[]> data, long length) {
		this.data = data;
		this.length = length;
	}
	
	
	
	
	
	

}
