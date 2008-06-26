package org.devzuz.q.maven.pom.util;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;

public class PropertyUtil
{
    private static BasicExtendedMetaData emd = new BasicExtendedMetaData();
    
    public static EStructuralFeature getPropertyFeature( String name )
    {
        EStructuralFeature feature = emd.demandFeature( "", name, true, false );
        feature.setUpperBound( 1 );
        return feature;
    }
}
