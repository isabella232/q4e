/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.model;

import java.util.Comparator;
import org.apache.maven.model.Developer;

public class DeveloperComparator implements Comparator<Developer>{
	
	public int compare(Developer o1, Developer o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
