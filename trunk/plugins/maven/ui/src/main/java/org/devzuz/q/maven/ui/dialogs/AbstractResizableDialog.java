/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.dialogs;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractResizableDialog
    extends TrayDialog
{
    protected abstract Preferences getDialogPreferences();

    private final String TAG_X;

    private final String TAG_Y;

    private final String TAG_WIDTH;

    private final String TAG_HEIGHT;

    public AbstractResizableDialog( Shell parentShell )
    {
        super( parentShell );
        setShellStyle( getShellStyle() | SWT.RESIZE | SWT.MAX );

        TAG_X = getDialogXPositionPreferenceTag();
        TAG_Y = getDialogYPositionPreferenceTag();
        TAG_WIDTH = getDialogWidthPreferenceTag();
        TAG_HEIGHT = getDialogHeightPreferenceTag();
    }

    public AbstractResizableDialog( IShellProvider parentShell )
    {
        super( parentShell );
        setShellStyle( getShellStyle() | SWT.RESIZE | SWT.MAX );

        TAG_X = getDialogXPositionPreferenceTag();
        TAG_Y = getDialogYPositionPreferenceTag();
        TAG_WIDTH = getDialogWidthPreferenceTag();
        TAG_HEIGHT = getDialogHeightPreferenceTag();
    }

    protected Control createDialogArea( Composite parent )
    {
        internalInitializeWindow();
        Composite container = (Composite) super.createDialogArea( parent );
        return internalCreateDialogArea( container );
    }

    protected abstract Control internalCreateDialogArea( Composite parent );

    public void internalInitializeWindow()
    {
        ShellAdapter activateListener = new ShellAdapter()
        {
            public void shellActivated( ShellEvent e )
            {
                onWindowActivate();
            }

            public void shellClosed( ShellEvent e )
            {
                onWindowDeactivate();
            }
        };
        getShell().addShellListener( activateListener );
    }

    public void onWindowActivate()
    {
    }

    public void onWindowDeactivate()
    {
    }

    private Rectangle loadBounds()
    {
        Preferences settings = getDialogPreferences();
        try
        {
            return new Rectangle( settings.getInt( TAG_X ), settings.getInt( TAG_Y ), settings.getInt( TAG_WIDTH ),
                                  settings.getInt( TAG_HEIGHT ) );
        }
        catch ( NumberFormatException e )
        {
            return null;
        }
    }

    private void saveBounds( Rectangle bounds )
    {
        Preferences settings = getDialogPreferences();

        settings.setValue( TAG_X, bounds.x );
        settings.setValue( TAG_Y, bounds.y );
        settings.setValue( TAG_WIDTH, bounds.width );
        settings.setValue( TAG_HEIGHT, bounds.height );
    }

    protected Rectangle cachedBounds;

    protected Point getInitialSize()
    {
        // Track the current dialog bounds.
        getShell().addControlListener( new ControlListener()
        {
            public void controlMoved( ControlEvent arg0 )
            {
                cachedBounds = getShell().getBounds();
            }

            public void controlResized( ControlEvent arg0 )
            {
                cachedBounds = getShell().getBounds();
            }
        } );

        // Answer the size from the previous incarnation.
        Rectangle b1 = getShell().getDisplay().getBounds();
        Rectangle b2 = loadBounds();

        if ( b2 != null && !b2.isEmpty() )
            return new Point( b1.width < b2.width ? b1.width : b2.width, b1.height < b1.height ? b2.height : b2.height );

        return super.getInitialSize();
    }

    protected Point getInitialLocation( Point initialSize )
    {
        // Answer the location from the previous incarnation.
        Rectangle displayBounds = getShell().getDisplay().getBounds();
        Rectangle bounds = loadBounds();

        if ( bounds != null && !bounds.isEmpty() )
        {
            int x = bounds.x;
            int y = bounds.y;
            int maxX = displayBounds.x + displayBounds.width - initialSize.x;
            int maxY = displayBounds.y + displayBounds.height - initialSize.y;
            if ( x > maxX )
                x = maxX;
            if ( y > maxY )
                y = maxY;
            if ( x < displayBounds.x )
                x = displayBounds.x;
            if ( y < displayBounds.y )
                y = displayBounds.y;

            return new Point( x, y );
        }

        return super.getInitialLocation( initialSize );
    }

    public boolean close()
    {
        boolean closed = super.close();
        if ( closed && cachedBounds != null )
            saveBounds( cachedBounds );

        return closed;
    }

    protected String getDialogXPositionPreferenceTag()
    {
        return getClass().getName() + "_X";
    }

    protected String getDialogYPositionPreferenceTag()
    {
        return getClass().getName() + "_Y";
    }

    protected String getDialogWidthPreferenceTag()
    {
        return getClass().getName() + "_Width";
    }

    protected String getDialogHeightPreferenceTag()
    {
        return getClass().getName() + "_Height";
    }
}
