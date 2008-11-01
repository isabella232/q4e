/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.core;

import java.util.Comparator;

/**
 * Dynamic sorter, it allows us to add/remove/reorder sorted fields
 * @author crdale
 *
 */
public class DependencySorter implements Comparator<Dependency>
{
	Sorter[] sorters;

	public DependencySorter(Sorter[] sorters)
	{
		this.sorters = sorters;
	}

	public int compare(Dependency lhs, Dependency rhs)
	{
		int result = 0;
		for (Sorter sorter : sorters)
		{
			result = sorter.compare(lhs, rhs);
			if (result != 0)
			{
				break;
			}
		}
		return result;
	}

}
