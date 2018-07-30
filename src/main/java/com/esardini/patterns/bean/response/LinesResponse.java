package com.esardini.patterns.bean.response;

import java.util.Iterator;
import java.util.List;

import com.esardini.patterns.bean.LineBean;

public class LinesResponse extends GenericResponse{
	
	String lineList;

	public String getLineList() {
		return lineList;
	}

	public void setLineList(List<LineBean> lineList) {
		
		Iterator<LineBean> i = lineList.iterator();
		
		String s = "Lines : {";
		
		while(i.hasNext()) {
			LineBean l = i.next();
			
			s+= l.toString();
			
			if(i.hasNext()) {
				s+= ", ";
			}
		}
		
		
		this.lineList = s + "}";
	}
	
	

}
