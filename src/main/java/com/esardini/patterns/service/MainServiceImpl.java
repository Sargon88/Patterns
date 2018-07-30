package com.esardini.patterns.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.esardini.patterns.bean.LineBean;
import com.esardini.patterns.bean.PointBean;

@Component
public class MainServiceImpl implements MainService {
	
	private static final Logger log = Logger.getLogger(MainServiceImpl.class);	
	private static CopyOnWriteArrayList<PointBean> plane = new CopyOnWriteArrayList<PointBean>();
	private static CopyOnWriteArrayList<PointBean> consumablePlane = new CopyOnWriteArrayList<PointBean>();
	private static List<LineBean> lineList;

	@Override
	public String addPoint(PointBean point) {
		String responseMessage = "";
		
		//verifico che non esista già nel piano
		if(!pointInSpace(point)) {
			//lo inserisco
			log.info("Adding New Point");
			plane.add(point);
			
			responseMessage = "Inserito";
			
		} else {
			log.info("Point " + point + " present");
			responseMessage = "Punto " + point + " già presente";	
		}
		
		log.info("Plane: " + plane.toString());
		return responseMessage;
	}

	@Override
	public CopyOnWriteArrayList<PointBean> getSpace() {
		return plane;
	}

	@Override
	public Integer clearPlane() {
		plane.clear();
		
		return plane.size();
		
	}

	@Override
	public List<LineBean> getLines(Integer lineSize){
		log.debug("Start getLines: " + lineSize);
		
		Collections.sort(plane);
		lineList = new ArrayList<LineBean>();
		
		//verify if there're sufficient points
		if(plane.isEmpty() || plane.size() < lineSize) {
			log.info("Too few points");
			return lineList;
		}
		
		consumablePlane.addAll(plane);
		Iterator<PointBean> pointIterator = consumablePlane.iterator();
		
		//for each point P1 in the space
		while(pointIterator.hasNext()) {
			PointBean p1 = pointIterator.next();
			
			Iterator<PointBean> pointIterator2 = consumablePlane.iterator();
			//for each point P2 in all points except P1
			while(pointIterator2.hasNext()) {
				PointBean p2 = pointIterator2.next();
				
				LineBean line = new LineBean();
				if(!p1.equals(p2)) {
					//line is the unique line joining P1 & P2	
					line.addPoint(p1);
					line.addPoint(p2);
				} else {
					continue;
				}
				
				Iterator<PointBean> pointIterator3 = consumablePlane.iterator();
				//for each point P3 in all points
				while(pointIterator3.hasNext()) {
					PointBean p3 = pointIterator3.next();
					
					//if P3 lies on L and it's not P1 or P2
					if(!line.containsPoint(p3) && inLine(p1, p2, p3)) {
						//add P3 to line
						line.addPoint(p3);
					}				
				}
				
				//if size of line is >= lineSize
				if(line.getSize() >= lineSize) {
					
					line.sortPoints();
					//if line is not in lineList
					if(!lineInList(line)) {
						//add line to lineList
						lineList.add(line);
					}
				}						
			}
		}
		return lineList;
	}
	
	//verify if three points are on the same line
	private Boolean inLine(PointBean p1, PointBean p2, PointBean p3) {
				
		// (x3-x1)/(x2-x1) = (y3-y1)/(y2-y1)			
		Double txNum = (double) (p3.getXCoordinate() - p1.getXCoordinate());
		Double txDen = (double) (p2.getXCoordinate() - p1.getXCoordinate());
		Double tyNum = (double) (p3.getYCoordinate() - p1.getYCoordinate());
		Double tyDen = (double) (p2.getYCoordinate() - p1.getYCoordinate());
	
		Double m = null;
		if(tyDen.equals((double)0)) {
			m = (double) (p3.getYCoordinate() - p1.getYCoordinate()); 			
		} else if(txDen.equals((double)0)) {
			m = (double) (p3.getXCoordinate() - p1.getXCoordinate());
		} else {
			m = (txNum/txDen) - (tyNum/tyDen);
		}
		
		if(m == 0) {
			log.debug(p1 + " " + p2 + " " + p3 + " are aligned");
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}
	
	//verify if the line is in the list of valid lines
	private Boolean lineInList(LineBean l) {		
		log.debug("line: " + l);
		log.debug("LIST: " + lineList);
		
		Iterator<LineBean> i = lineList.iterator();
		
		while (i.hasNext()) {
			LineBean line = i.next();
			
			if(line.isEqual(l)) {
				return Boolean.TRUE;
			}
		}
		
		return Boolean.FALSE;		
	}
	
	//verify if the point is in the 
	private Boolean pointInSpace(PointBean p){
		Iterator<PointBean> spaceIterator = plane.iterator();
		
		while(spaceIterator.hasNext()) {
			PointBean spacePoint = spaceIterator.next();
			
			if(spacePoint.compareTo(p) == 0) {
				log.debug("Found in the space");
				return Boolean.TRUE;
			}
		}
		
		return Boolean.FALSE;
		
	}
	
	@Override
	public void testInsert() {
		
		PointBean p = new PointBean();
		p.setXCoordinate(1);
		p.setYCoordinate(1);
		plane.add(p);
		
		p = new PointBean();
		p.setXCoordinate(1);
		p.setYCoordinate(2);
		plane.add(p);
		
		p = new PointBean();
		p.setXCoordinate(1);
		p.setYCoordinate(3);
		plane.add(p);
		
		p = new PointBean();
		p.setXCoordinate(2);
		p.setYCoordinate(4);
		plane.add(p);
		
		p = new PointBean();
		p.setXCoordinate(2);
		p.setYCoordinate(5);
		plane.add(p);
		
		p = new PointBean();
		p.setXCoordinate(2);
		p.setYCoordinate(6);
		plane.add(p);
		
		p = new PointBean();
		p.setXCoordinate(3);
		p.setYCoordinate(7);
		plane.add(p);
		
		p = new PointBean();
		p.setXCoordinate(3);
		p.setYCoordinate(8);
		plane.add(p);
		
		p = new PointBean();
		p.setXCoordinate(1);
		p.setYCoordinate(4);
		plane.add(p);
		
	}
	
	

}
