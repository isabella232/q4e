/*******************************************************************************
 * Copyright (c) 2007 Simula Labs
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.devzuz.q.maven.embedder.internal;

import java.util.Date;

import org.devzuz.q.maven.embedder.EventType;
import org.devzuz.q.maven.embedder.IMavenEvent;
import org.devzuz.q.maven.embedder.Severity;

public abstract class AbstractMavenEvent implements IMavenEvent
{

    private EventType type;

    private String target;

    private long time;

    private Throwable throwable;

    public Date createdDate = new Date();

    protected AbstractMavenEvent()
    {
    }

    public AbstractMavenEvent( String type, String target, long time )
    {
        this( type, target, time, null );
    }

    public AbstractMavenEvent( String type, String target, long time, Throwable throwable )
    {
        this.setType( EventType.parseEvent( type ) );
        this.setTarget( target );
        this.setTime( time );
        this.setThrowable( throwable );
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    protected void setType( EventType type )
    {
        this.type = type;
    }

    public EventType getType()
    {
        return type;
    }

    protected void setTarget( String target )
    {
        this.target = target;
    }

    protected String getTarget()
    {
        return target;
    }

    protected void setTime( long time )
    {
        this.time = time;
    }

    protected long getTime()
    {
        return time;
    }

    protected void setThrowable( Throwable throwable )
    {
        this.throwable = throwable;
    }

    protected Throwable getThrowable()
    {
        return throwable;
    }

    public abstract String getTypeText();

    public abstract String getDescriptionText();

    protected String mergeMessages( String description, Object... objects )
    {
        for ( int i = 0; i < objects.length; i++ )
        {
            if ( objects[i] != null )
                description = oldReplace( description, "%" + i, objects[i].toString() );
            else
                description = oldReplace( description, "%" + i, "" );
        }

        /* change tabs to 2 spaces */
        description = description.replaceAll( "\t", "  " );

        return description;
    }

    public static String oldReplace( final String aInput, final String aOldPattern, final String aNewPattern )
    {
        if ( aOldPattern.equals( "" ) )
        {
            throw new IllegalArgumentException( "Old pattern must have content." );
        }

        final StringBuilder result = new StringBuilder();
        // startIdx and idxOld delimit various chunks of aInput; these
        // chunks always end where aOldPattern begins
        int startIdx = 0;
        int idxOld = 0;
        while ( ( idxOld = aInput.indexOf( aOldPattern, startIdx ) ) >= 0 )
        {
            // grab a part of aInput which does not include aOldPattern
            result.append( aInput.substring( startIdx, idxOld ) );
            // add aNewPattern to take place of aOldPattern
            result.append( aNewPattern );

            // reset the startIdx to just after the current match, to see
            // if there are any further matches
            startIdx = idxOld + aOldPattern.length();
        }
        // the final chunk will go to the end of aInput
        result.append( aInput.substring( startIdx ) );
        return result.toString();
    }

    public Severity getSeverity()
    {
        return Severity.debug;
    }

    @Override
    public String toString()
    {
        return getSeverity() + " : " + getDescriptionText();
    }

}
