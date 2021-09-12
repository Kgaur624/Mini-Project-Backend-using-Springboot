 package com.kartik.location.controllers;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kartik.location.entities.Location;
import com.kartik.location.repos.LocationRepository;
import com.kartik.location.service.LocationService;
import com.kartik.location.util.EmailUtil;
import com.kartik.location.util.ReportUtil;
//import com.kartik.location.util.EmailUtil;
//import com.kartik.location.util.ReportUtil;

@Controller
public class LocationController {

	@Autowired
	LocationService service;

	@Autowired
	LocationRepository repository;
	
	@Autowired
	EmailUtil emailUtil;

	
	@Autowired
	ReportUtil reportUtil;
	
	@Autowired
	ServletContext sc;

	// this below method should return a String which is JSP page to which the user should be directed to
	@RequestMapping("/showCreate")
	public String showCreate() {
		return "createLocation"; // like return jsp page that will display to the end user 
	}
/*
 * when we click on the save button form should be submitted on server side 
 * spring container will take all the fields that come in the request, it will convert into modal object and (hands it to the controller method then invokes the service method
 * service uses the locationRepository, the repository uses internally, spring uses hibernate and hibernate is responsible for converting the modal object into  database record) 
 *  we can reterive that obj inside our 
 * controller using @ModelAttribute in spring so we get the object, take the object and then save into the DB.
 *
 **/
	
	@RequestMapping("/saveLoc")
	public String saveLocation(@ModelAttribute("location") Location location, ModelMap modelMap) {
		Location locationSaved = service.saveLocation(location);
		String msg = "Location saved with id: " + locationSaved.getId();
		modelMap.addAttribute("msg", msg);
		emailUtil.sendEmail("gourkartik189@gmail.com", "Location Saved",
			"Location Saved Successfully and about to return a response");
		return "createLocation";
	}

	
	
	@RequestMapping("/displayLocations")
	public String displayLocations(ModelMap modelMap) {
		List<Location> locations = service.getAllLocations();
		modelMap.addAttribute("locations", locations);
		return "displayLocations";
	}
	
	
	/*
	 * When we click on delete button,(in the url) deleteLocation is the URI, id goes to the controller
	 * 1 spring will bind that id into this id and we are invoking then we are setting the id into  
	 * 2 new location that we are creating then
	 * 3 we are invoking deleteLocation by passing in the location
	 * 
	 * 4 whatever remaining data there , it is being displayed because in the response we are reteriving all the location, setting in the modal map 
	 */

	@RequestMapping("deleteLocation")//              this id
	public String deleteLocation(@RequestParam("id") int id, ModelMap modelMap) {
		// Location location = service.getLocationById(id);
		Location location = new Location();//1
		location.setId(id);//2
		service.deleteLocation(location);//3
		List<Location> locations = service.getAllLocations();//4 
		modelMap.addAttribute("locations", locations);//
		return "displayLocations";
	}

	@RequestMapping("/showUpdate")//           
	public String showUpdate(@RequestParam("id") int id, ModelMap modelMap) {
		Location location = service.getLocationById(id);
		modelMap.addAttribute("location", location);
		return "updateLocation";
	}

	/* 
	 *  this method is going to display the update page like 
	 * 
	 * 
	 * 
	 * 
	 */
	@RequestMapping("/updateLoc")
	public String updateLocation(@ModelAttribute("location") Location location, ModelMap modelMap) {
		service.updateLocation(location);
		List<Location> locations = service.getAllLocations();
		modelMap.addAttribute("locations", locations);
		return "displayLocations";
	}
		
	
	// here we are using ServletContext.getPath because i want to save that image relative to
	// my web application so that my jsp can use it 
	
	@RequestMapping("/generateReport")
	public String generateReport() {
		 String path = sc.getRealPath("/");
		List<Object[]> data = repository.findTypeAndTypeCount();
		reportUtil.generatePieChart(path, data);
		return "report";

	}
	

	}


