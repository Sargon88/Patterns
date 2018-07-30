package com.esardini.patterns.bean.response;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import com.esardini.patterns.bean.PointBean;

public class SpaceResponse extends GenericResponse {

	private String space = "";

	public String getSpace() {
		return space;
	}

	public void setSpace(CopyOnWriteArrayList<PointBean> plane) {
		
		String pointsList = "";
				
		Iterator<PointBean> pointsIterator = plane.iterator();
		
		while (pointsIterator.hasNext()) {
			PointBean point = pointsIterator.next();
			
			pointsList += point.toString();
			
			if(pointsIterator.hasNext()) {
				pointsList += ", ";
			}
			
		}
		
		
		this.space = pointsList;
	}
	
	
	
	
}
