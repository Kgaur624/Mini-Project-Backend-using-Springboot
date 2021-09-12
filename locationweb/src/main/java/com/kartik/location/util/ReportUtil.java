package com.kartik.location.util;

import java.util.List;

public interface ReportUtil {

	void generatePieChart(String path,List<Object[]> data);
	
	// path--> where we want the final JPEG to live in, the entire path to the JPEG
	// will be passed in form the controller 
	
}
