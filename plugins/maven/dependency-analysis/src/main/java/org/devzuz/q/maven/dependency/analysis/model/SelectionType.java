/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.dependency.analysis.model;

/**
 * Types of selection. The primary selection is what the user clicks on, secondary are the related objects (eg if they
 * selected a version then the related objects are the instances and artifact), and tertiary is the upward selections in
 * the instance tree.
 * 
 * @author jake pezaro
 */
public enum SelectionType
{
    NONE, PRIMARY, SECONDARY, TERTIARY;
}
