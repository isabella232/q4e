/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.devzuz.q.maven.pom.util;

import org.devzuz.q.maven.pom.translators.SSESyncResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource Factory</b> associated with the package.
 * <!-- end-user-doc -->
 * @see org.devzuz.q.maven.pom.util.PomResourceImpl
 */
public class PomResourceFactoryImpl extends ResourceFactoryImpl
{
    /**
     * Creates an instance of the resource factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public PomResourceFactoryImpl()
    {
        
    }
    
    @Override
    public Resource createResource( URI uri )
    {
        return new SSESyncResource( uri );
    }

    
} //PomResourceFactoryImpl
