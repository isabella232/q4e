package org.devzuz.q.maven.pomeditor.model;

import java.util.Comparator;
import org.apache.maven.model.Developer;

public class DeveloperComparator implements Comparator<Developer>{
	
	public int compare(Developer o1, Developer o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
