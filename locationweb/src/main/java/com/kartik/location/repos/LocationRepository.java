package com.kartik.location.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kartik.location.entities.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {

	@Query("select type,count(type) from Location group by type")
	public List<Object[]> findTypeAndTypeCount();
	// this above method will return list of object array,
	// JPA or HQL will execute the query get the result, like type and count
	//  it wil put them into an object array so we will see list of object array
	
}
