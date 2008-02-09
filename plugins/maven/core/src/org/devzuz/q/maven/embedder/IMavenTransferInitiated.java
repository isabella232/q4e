/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder;

import org.devzuz.q.maven.embedder.internal.MavenTransferStarted;

/**
 * Event fired when Maven tries to download a file. If the file is found then a {@link MavenTransferStarted} is fired.
 */
public interface IMavenTransferInitiated extends IMavenEvent
{

}
