package org.devzuz.q.maven.ui.dialogs;

import org.devzuz.q.maven.ui.Activator;
import org.devzuz.q.maven.ui.Messages;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class ArchetypeListSourceDialog extends AbstractResizableDialog
{
    private static ArchetypeListSourceDialog archetypeListSourceDialog = null;

    public static synchronized ArchetypeListSourceDialog getArchetypeListSourceDialog()
    {
        if ( archetypeListSourceDialog == null )
        {
            archetypeListSourceDialog = new ArchetypeListSourceDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() );
        }

        return archetypeListSourceDialog;
    }
    
    public ArchetypeListSourceDialog( IShellProvider parentShell )
    {
        super( parentShell );
    }

    public ArchetypeListSourceDialog( Shell parentShell )
    {
        super( parentShell );
    }

    private Text archetypeListSourceText;

    private Combo typeText;

    private String archetypeListSource = "";

    private String type = "";

    public String getArchetypeListSource()
    {
        return archetypeListSource;
    }

    public void setArchetypeListSource( String archetypeListSource )
    {
        this.archetypeListSource = archetypeListSource;
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }
    
    protected Control internalCreateDialogArea( Composite container )
    {
        ModifyListener modifyingListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                validate();
            }
        };

        container.setLayout( new GridLayout( 2, false ) );

        // Key and type Label and Text
        Label label = new Label( container, SWT.NULL );
        label.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label.setText( Messages.MavenArchetypePreferencePage_sourceurl );

        archetypeListSourceText = new Text( container, SWT.BORDER | SWT.SINGLE );
        archetypeListSourceText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        archetypeListSourceText.addModifyListener( modifyingListener );

        Label label2 = new Label( container, SWT.NULL );
        label2.setLayoutData( new GridData( GridData.BEGINNING, GridData.CENTER, false, false ) );
        label2.setText( Messages.MavenArchetypePreferencePage_type );

        typeText = new Combo( container, SWT.READ_ONLY );
        typeText.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false ) );
        //typeText.addModifyListener( modifyingListener );
        typeText.add( "Wiki" );
        typeText.add( "XML" );
        return container;
    }

    public void onWindowActivate()
    {
        archetypeListSourceText.setText( archetypeListSource );
        if( !type.trim().equals( "" ) )
            typeText.setText( type );
        else
            typeText.select( 0 );
        validate();
    }

    protected void okPressed()
    {
        archetypeListSource = archetypeListSourceText.getText().trim();
        type = typeText.getText();

        super.okPressed();
    }

    public int openWithEntry( String archetypeListSource, String type )
    {
        setArchetypeListSource( archetypeListSource );
        setType( type );

        return super.open();
    }

	private static boolean isValidURL(String strURL)
	{
		URL url = null;
		boolean retVal = false;
    	try {
			url = new URL(strURL); 
			retVal = true;
    	} catch (java.net.MalformedURLException ex) {
    	    //System.out.println("Malformed URL exception: " + ex);
    	}
    	if(retVal) { 
        	try {

            	Pattern pattern = Pattern.compile("^"+url.getProtocol().trim()+"://[\\p{Alnum}]{3,}+.{1}[\\p{Alnum}]{3}[\\p{Alnum}.?+/=-]*$");
            	Matcher m = pattern.matcher(strURL);
            		if(m.matches()) {
            			retVal= true;

            		}else{
            			retVal= false;
            		}
            	}catch (Exception e){
            		retVal= false;
            	}
    	}
		return retVal;
	}
	
    public void validate()
    {
        getButton( IDialogConstants.OK_ID ).setEnabled( didValidate() );
    }

    private boolean didValidate()
    {
        return ( (archetypeListSourceText.getText().trim().length() > 0 && isValidURL(archetypeListSourceText.getText())) && (typeText.getText().length() > 0) );
    }

    @Override
    protected Preferences getDialogPreferences()
    {
        return Activator.getDefault().getPluginPreferences();
    }
}
