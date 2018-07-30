package com.esardini.patterns.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.esardini.patterns.bean.LineBean;
import com.esardini.patterns.bean.PointBean;


public interface MainService {

	public String addPoint(PointBean point);
	public CopyOnWriteArrayList<PointBean> getSpace();
	public Integer clearPlane();
	public List<LineBean> getLines(Integer pointsNumber);
	
	//only for test purpose
	public void testInsert();
	
	
}
