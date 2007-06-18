/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.wizard.core;

import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultArchetypeListProvider
    implements IArchetypeListProvider
{
    public Map<String, Archetype> getArchetypes()
    {
        Map<String, Archetype> arch = new LinkedHashMap<String, Archetype>();

        arch.put( "maven-archetype-quickstart", new Archetype( "maven-archetype-j2ee-simple",
                                                               "org.apache.maven.archetypes", "",
                                                               "http://www.ibiblio.org/maven2",
                                                               "A simple J2EE Java application" ) );
        arch.put( "maven-archetype-j2ee-simple", new Archetype( "maven-archetype-j2ee-simple",
                                                                "org.apache.maven.archetypes", "",
                                                                "http://www.ibiblio.org/maven2",
                                                                "A simple J2EE Java application" ) );
        arch.put( "maven-archetype-marmalade-mojo", new Archetype( "maven-archetype-marmalade-mojo", "org.apache.maven.archetypes", "",
                                                                   "http://www.ibiblio.org/maven2", "A Maven plugin development project using marmalade" ) );
        arch.put( "maven-archetype-mojo", new Archetype( "maven-archetype-mojo", "org.apache.maven.archetypes", "",
                                                         "http://www.ibiblio.org/maven2",
                                                         "A Maven Java plugin development project" ) );
        arch.put( "maven-archetype-portlet", new Archetype( "maven-archetype-portlet", "org.apache.maven.archetypes",
                                                            "", "http://www.ibiblio.org/maven2",
                                                            "A simple portlet application" ) );
        arch.put( "maven-archetype-site-simple", new Archetype( "maven-archetype-site-simple",
                                                                "org.apache.maven.archetypes", "",
                                                                "http://www.ibiblio.org/maven2",
                                                                "A simple site generation project" ) );
        arch.put( "maven-archetype-site", new Archetype( "maven-archetype-site", "org.apache.maven.archetypes", "",
                                                         "http://www.ibiblio.org/maven2", "A more complex site project" ) );
        arch.put( "maven-archetype-webapp", new Archetype( "maven-archetype-webapp", "org.apache.maven.archetypes", "",
                                                           "http://www.ibiblio.org/maven2",
                                                           "A simple Java web application" ) );
        arch.put( "maven-archetype-simple", new Archetype( "maven-archetype-simple", "org.apache.maven.archetypes", "",
                                                           "http://www.ibiblio.org/maven2", "" ) );
        arch.put( "maven-archetype-profiles", new Archetype( "maven-archetype-profiles", "org.apache.maven.archetypes",
                                                             "", "http://www.ibiblio.org/maven2", "" ) );

        return arch;
    }

}
