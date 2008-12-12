/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.model;

import java.util.Comparator;

import org.apache.maven.model.Contributor;

public class ContributorComparator
    implements Comparator<Contributor>
{
    public int compare( Contributor o1, Contributor o2 )
    {
        return o1.getName().compareTo(o2.getName());
    }

}
