/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder;

import java.util.ArrayList;
import java.util.List;

public class MavenRepositoryFactory {

    public static List<IMavenRepository> getRepositories(IMavenProject project) {
        // XXX To do need to pick up the repo repositories from te project

        List<IMavenRepository> repositories = new ArrayList<IMavenRepository>();
        repositories.add(MavenManager.getMaven().getLocalRepository());
        return repositories;

    }

    public static IMavenRepository getRepository(String uri) {
        // XXX To do need to pick up the repo repositories from te project

        return MavenManager.getMaven().getLocalRepository();

    }
}
