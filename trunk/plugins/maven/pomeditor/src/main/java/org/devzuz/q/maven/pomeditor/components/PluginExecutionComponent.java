/*
 * Copyright (c) 2007-2008 DevZuz, Inc. (AKA Simula Labs, Inc.) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.devzuz.q.maven.pomeditor.components;

import org.devzuz.q.maven.pomeditor.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class PluginExecutionComponent extends AbstractComponent
{
    private Text idText;

    private Text phaseText;

    private Button inheritedRadioButton;
    
    public PluginExecutionComponent( Composite parent, int style, IComponentModificationListener componentListener )
    {   
        this( parent , style );
        addComponentModifyListener( componentListener );
    }

    public PluginExecutionComponent( Composite parent, int style )
    {
        super( parent, style );

        setLayout( new GridLayout( 2, false ) );

        Label idLabel = new Label( this, SWT.NULL );
        idLabel.setLayoutData( createLabelLayoutData() );
        idLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Identity );

        idText = new Text( this, SWT.BORDER | SWT.SINGLE );
        idText.setLayoutData( createControlLayoutData() );

        Label phaseLabel = new Label( this, SWT.NULL );
        phaseLabel.setLayoutData( createLabelLayoutData() );
        phaseLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Phase );

        phaseText = new Text( this, SWT.BORDER | SWT.SINGLE );
        phaseText.setLayoutData( createControlLayoutData() );

        Label inheritedLabel = new Label( this, SWT.NULL );
        inheritedLabel.setLayoutData( createLabelLayoutData() );
        inheritedLabel.setText( Messages.MavenPomEditor_MavenPomEditor_Inherited );

        inheritedRadioButton = new Button( this, SWT.CHECK );
        inheritedRadioButton.setLayoutData( createControlLayoutData() );

        ModifyListener listener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                notifyListeners( ( Control ) e.widget );
            }
        };

        idText.addModifyListener( listener );
        phaseText.addModifyListener( listener );

        SelectionListener selectionListener = new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent arg0 )
            {
                notifyListeners( ( Control ) arg0.widget );
            }
        };

        inheritedRadioButton.addSelectionListener( selectionListener );
    }

    private GridData createControlLayoutData()
    {
        GridData controlData = new GridData( SWT.FILL, SWT.CENTER, true, false );
        controlData.horizontalIndent = 10;
        return controlData;
    }

    private GridData createLabelLayoutData()
    {
        GridData labelData = new GridData( SWT.BEGINNING, SWT.CENTER, false, false );
        labelData.widthHint = 50;
        return labelData;
    }

    public String getId()
    {
        return nullIfBlank( idText.getText().trim() );
    }

    public void setId( String id )
    {
        idText.setText( blankIfNull( id ) );
    }

    public String getPhase()
    {
        return nullIfBlank( phaseText.getText().trim() );
    }

    public void setPhase( String phase )
    {
        phaseText.setText( blankIfNull( phase ) );
    }

    public boolean isInherited()
    {
        return inheritedRadioButton.getSelection();
    }

    public void setInherited( boolean inherited )
    {
        inheritedRadioButton.setSelection( inherited );
    }
}
