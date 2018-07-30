package com.esardini.patterns.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class LineBean {
	
	private ArrayList<PointBean> points = new ArrayList<PointBean>();

	public ArrayList<PointBean> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<PointBean> points) {
		this.points = points;
	}

	public void addPoint(PointBean p) {
		this.points.add(p);
	}
	
	public void removePoint(PointBean p) {
		this.points.remove(p);
	}
	
	public Integer getSize() {
		return points.size();
	}
	
	public void sortPoints() {
		Collections.sort(points);
	}

	public Boolean isEqual(LineBean l) {
		
		if(!this.getSize().equals(l.getSize())) {
			return Boolean.FALSE;
		}
		
		for(int i=0; i<points.size(); i++) {
			
			PointBean p1 = points.get(i);
			PointBean p2 = l.getPoints().get(i);
			
			if(p1.compareTo(p2) != 0) {
				return Boolean.FALSE;
			}		
		}
		
		return Boolean.TRUE;
	}
	
	public Boolean containsPoint(PointBean p) {
		Boolean containsPoint = Boolean.FALSE;
		
		Iterator<PointBean> i = points.iterator();
		
		while(i.hasNext()) {
			PointBean n = i.next();
			
			if(p.compareTo(n) == 0) {
				containsPoint = Boolean.TRUE;
				break;
			}
		}
		
				
		return containsPoint;		
	}

	@Override
	public String toString() {
		
		String s = "[";
		
		Iterator<PointBean> i = points.iterator();
		
		while(i.hasNext()) {
			PointBean p = i.next();
			
			s+= p.toString();
			
			if(i.hasNext()) {
				s+=", ";
			}
		}
		
		return s + "]";
	}
	
	
}
