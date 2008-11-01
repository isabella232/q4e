/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.search.preferences;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.jface.preference.IPreferenceStore;

public class ArtifactSearchPreferencesManager
{
    public static final String PREF_ENABLED_PROVIDERS = "org.devzuz.q.maven.search.enabledProviders";

    private IPreferenceStore preferenceStore;

    public ArtifactSearchPreferencesManager( IPreferenceStore preferenceStore )
    {
        super();
        this.preferenceStore = preferenceStore;
    }

    public void setEnabledSearchProviderIds( Collection<String> ids )
    {
        String value = join( ids );
        preferenceStore.setValue( PREF_ENABLED_PROVIDERS, value );
    }

    public Collection<String> getEnabledSearchProviderIds()
    {
        String value = preferenceStore.getString( PREF_ENABLED_PROVIDERS );
        return "".equals( value ) ? Collections.<String> emptySet() : split( value );
    }

    public String join( Collection<String> components )
    {
        if ( !components.isEmpty() )
        {
            StringBuilder ret = new StringBuilder();
            for ( String string : components )
            {
                ret.append( "|" );
                ret.append( string );
            }
            return ret.substring( 1 );
        }
        else
        {
            return "";
        }
    }

    public Collection<String> split( String string )
    {
        return Arrays.asList( string.split( "\\|" ) );
    }
}
