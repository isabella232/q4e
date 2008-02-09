/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder;

import org.apache.maven.wagon.AbstractWagon;
import org.devzuz.q.maven.embedder.internal.MavenTransferStarted;

/**
 * Event fired if there's an error during the download of the file, after a {@link MavenTransferStarted} event. It's
 * known that Wagon is not raising these events as much as it should, needs to be properly handled in
 * {@link AbstractWagon}.
 */
public interface IMavenTransferError extends IMavenEvent
{

}
