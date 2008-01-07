/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/	
package org.devzuz.q.maven.ui.core;

import java.util.Comparator;

/**
 * Fields that can be sorted
 * @author robdale
 *
 */
public abstract class Sorter implements Comparator<Dependency>
{
	public abstract int compare(Dependency lhs, Dependency rhs);

	public static class GroupIdSorter extends Sorter
	{
		public int compare(Dependency lhs, Dependency rhs)
		{
			return lhs.getGroupId().compareToIgnoreCase(rhs.getGroupId());
		}
	}

	public static class ArtifactIdSorter extends Sorter
	{
		public int compare(Dependency lhs, Dependency rhs)
		{
			return lhs.getArtifactId().compareToIgnoreCase(rhs.getArtifactId());
		}
	}

	public static class VersionSorter extends Sorter
	{
		public int compare(Dependency lhs, Dependency rhs)
		{
			return lhs.getVersion().compareToIgnoreCase(rhs.getVersion());
		}
	}
}
