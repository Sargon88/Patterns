package com.esardini.patterns.bean;


import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PointBean implements Comparable<PointBean> {
		
	@JsonProperty(value="x")
	@NonNull
	private Integer xCoordinate;
	
	@JsonProperty(value="y")
	@NonNull
	private Integer yCoordinate;
	
	
	public Integer getXCoordinate() {
		return xCoordinate;
	}
	
	public void setXCoordinate(Integer xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	
	public Integer getYCoordinate() {
		return yCoordinate;
	}
	
	public void setYCoordinate(Integer yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
	public String getIndex() {
		return this.getXCoordinate().toString() + this.getYCoordinate().toString();
	}
	
	@Override
	public String toString() {
		return "{x:" + xCoordinate + ", y:" + yCoordinate + "}";
	}

	@Override
	public int compareTo(PointBean p) {
		
		Integer xDiff = this.getXCoordinate().compareTo(p.getXCoordinate());
		
		if(xDiff != 0) {
			return xDiff;
		} else {
			return this.getYCoordinate().compareTo(p.getYCoordinate());
		}
	}

}
