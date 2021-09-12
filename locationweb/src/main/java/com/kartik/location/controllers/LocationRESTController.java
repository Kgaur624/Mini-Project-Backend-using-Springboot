package com.kartik.location.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kartik.location.entities.Location;
import com.kartik.location.repos.LocationRepository;

@RestController
@RequestMapping("/locations")
public class LocationRESTController {

	@Autowired
	LocationRepository locationRepository;

	@GetMapping
	public List<Location> getLocations() {
		return locationRepository.findAll();

	}

	@PostMapping
	public Location createLocation(@RequestBody Location location) {
		return locationRepository.save(location);
	}

	@PutMapping
	public Location updateLocation(@RequestBody Location location) {
		return locationRepository.save(location);

	}

	@DeleteMapping("/{id}") // whatever number we are going to pass in the url so we telling to 
	// spring we need a URI path. this path var we need to map to id (using @pathvariable because id is coming as a part of the url)
	public void deleteLocation(@PathVariable("id") int id) {
		locationRepository.delete(null);
	}
	
	@GetMapping("/{id}")
	public Location getLocation(@PathVariable("id") int id) {
		return locationRepository.findById(id).get();
		
	}

}

// the request goes to the server, to our locationweb. spring will instantiate this
// controller and its knows that locations is mapped to this particular controller 
// and on location if we use get method spring will invoke the method findall
// and all the locations are being return spring will serialise these locations into 
//json 









