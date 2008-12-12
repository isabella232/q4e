/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.devzuz.q.maven.embedder;

import org.devzuz.q.maven.embedder.internal.MavenTransferCompleted;
import org.devzuz.q.maven.embedder.internal.MavenTransferError;
import org.devzuz.q.maven.embedder.internal.MavenTransferInitated;
import org.devzuz.q.maven.embedder.internal.MavenTransferProgress;

/**
 * Event fired when Maven starts downloading a file. This event is fired after a {@link MavenTransferInitated} and
 * followed by {@link MavenTransferProgress}, {@link MavenTransferCompleted} or {@link MavenTransferError}
 */
public interface IMavenTransferStarted extends IMavenEvent
{

}
