package com.esardini.patterns.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.esardini.patterns.bean.LineBean;
import com.esardini.patterns.bean.PointBean;
import com.esardini.patterns.bean.response.GenericResponse;
import com.esardini.patterns.bean.response.LinesResponse;
import com.esardini.patterns.bean.response.SpaceResponse;
import com.esardini.patterns.service.MainService;



//importato dal main controller
@RestController
@RequestMapping(value="/patterns")
public class PatternMainController {

	private static final Logger log = Logger.getLogger(PatternMainController.class);
	
	@Autowired
	MainService mService;

	@RequestMapping(value="/point", method=RequestMethod.POST)
	public GenericResponse insertPoint(@RequestBody PointBean point){
		log.info("Create Point Request: " + point);
		
		GenericResponse response = new GenericResponse();
		response.setEsito(Boolean.TRUE);
		
		try{
			
			//verifico che tutti i campi siano valorizzati
			if(point.getXCoordinate().equals(null) || point.getYCoordinate().equals(null)){
				//TODO creare errore adeguato
				throw new java.lang.NumberFormatException("Invalid Point Coordinates");			
			}
			
			String insertMessage = mService.addPoint(point);
			
			response.setMessage(insertMessage);

			
		} catch (NumberFormatException ex){
			log.error(ex.getMessage());
			response.setEsito(Boolean.FALSE);
			response.setMessage(ex.getMessage());
			
		}		
		
		log.info("");
		return response;
		
	}
	
	@RequestMapping(value="/space", method=RequestMethod.GET)
	public SpaceResponse getSpace(){
		log.info("Get Space Request");
		
		SpaceResponse response = new SpaceResponse();
		response.setEsito(true);
		
		CopyOnWriteArrayList<PointBean> plane = mService.getSpace();
		
		if(plane.isEmpty()) {
			log.info("Space Empty");
			response.setMessage("Nessun Punto nel Piano");
			
		} else {
			log.info(plane.size() + " punti nel piano");
			
			response.setMessage(plane.size() + " punti nel piano");
			response.setSpace(plane);
			
			log.info(response.getSpace());
			
		}
		
		log.info("");
		return response;
	}
	
	@RequestMapping(value="/space", method=RequestMethod.DELETE)
	public GenericResponse deleteSpace() {
		log.info("Delete Space Request");
		
		GenericResponse response = new GenericResponse();
		
		Integer planeSize = mService.clearPlane();
		
		if(planeSize == 0) {
			response.setEsito(Boolean.TRUE);
			response.setMessage("Elimintati tutti i punti");
		} else {
			response.setEsito(Boolean.FALSE);
			response.setMessage("Errore nella cancellazione dei punti del piano");
		}
				
		log.info("Done");
		log.info("");
		return response;
	}
	
	@RequestMapping(value="/lines/{n}", method=RequestMethod.GET)
	public LinesResponse getLines(@PathVariable("n") Integer n) {
		log.info("Get Lines Request. " + n + " points lines");
		
		LinesResponse response = new LinesResponse();
		response.setEsito(Boolean.TRUE);
		
		List<LineBean> lineList = mService.getLines(n);
		
		if(lineList.isEmpty()){
			response.setMessage("Nessuna linea componibile con almeno " + n + " punti");
		} else {
			response.setMessage(lineList.size() + " linee di almeno " + n + " punti");
		}
		
		response.setLineList(lineList);
		
		return response;
	}
	
	/**
	 * for test purpose
	 * 
	 */
	@RequestMapping(value={"/test"})
	public String test() {
		log.info("Test Method");
		
		mService.testInsert();
		
		return "OK";
	}
	
}
