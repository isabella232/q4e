/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.pomeditor.model;

import java.util.Comparator;

import org.apache.maven.model.License;

/**
 * @author gbaal
 * 
 */
public class LicenseComparator implements Comparator<License>
{
    public int compare( License o1, License o2 )
    {
        return o1.getName().compareTo( o2.getName() );
    }
}