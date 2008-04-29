package org.devzuz.q.maven.ui.preferences.editor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.devzuz.q.maven.ui.Messages;
import org.devzuz.q.maven.ui.preferences.MavenUIPreferenceManagerAdapter;
import org.devzuz.q.maven.ui.views.MavenProfileView;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;

public class MavenProfileFieldEditor
    extends FieldEditor
{
    private Table profileTable;

    private TableViewer profileTableViewer;

    private List<String> profiles;

    private Button addButton;

    private Button editButton;

    private Button deleteButton;

    private SelectionListener buttonSelectionListener;

    private ProfileContentProvider profileContentProvider;

    public MavenProfileFieldEditor( String name, String labelText, Composite parent )
    {
        profiles = new ArrayList<String>( MavenUIPreferenceManagerAdapter.getInstance().getConfiguredProfiles() );
        init( name, labelText );
        createControl( parent );
    }

    @Override
    protected void adjustForNumColumns( int numColumns )
    {
        Control control = getLabelControl();
        ( (GridData) control.getLayoutData() ).horizontalSpan = numColumns;
        ( (GridData) profileTable.getLayoutData() ).horizontalSpan = numColumns - 1;
    }

    @Override
    protected void doFillIntoGrid( Composite parent, int numColumns )
    {
        Control control = getLabelControl( parent );
        GridData gd = new GridData();
        gd.horizontalSpan = numColumns;
        control.setLayoutData( gd );

        profileTable = new Table( parent, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI );
        profileTable.setFont( parent.getFont() );
        profileTable.setHeaderVisible( true );
        profileTable.setLinesVisible( true );

        gd = new GridData( GridData.FILL_BOTH );
        gd.verticalAlignment = GridData.FILL;
        gd.grabExcessHorizontalSpace = true;
        gd.grabExcessVerticalSpace = true;
        profileTable.setLayoutData( gd );

        TableColumn column = new TableColumn( profileTable, SWT.NONE, 0 );
        column.setText( Messages.MavenProfileView_ProfileName );
        column.setWidth( 300 );

        profileTableViewer = new TableViewer( profileTable );
        profileContentProvider = new ProfileContentProvider();
        profileTableViewer.setContentProvider( profileContentProvider );
        profileTableViewer.setLabelProvider( new ProfileLabelProvider() );
        profileTableViewer.setInput( profiles );
        profileTableViewer.addSelectionChangedListener( new ISelectionChangedListener()
        {
            public void selectionChanged( SelectionChangedEvent event )
            {
                editButton.setEnabled( !event.getSelection().isEmpty() );
                deleteButton.setEnabled( !event.getSelection().isEmpty() );
            }
        } );

        Composite box = new Composite( parent, SWT.NULL );
        box.setLayout( new GridLayout() );
        gd = new GridData();
        gd.verticalAlignment = GridData.BEGINNING;
        box.setLayoutData( gd );

        addButton = new Button( box, SWT.PUSH );
        addButton.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false ) );
        addButton.setText( "Add" );
        addButton.addSelectionListener( getSelectionListener() );

        editButton = new Button( box, SWT.PUSH );
        editButton.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false ) );
        editButton.setText( "Edit" );
        editButton.addSelectionListener( getSelectionListener() );
        editButton.setEnabled( false );

        deleteButton = new Button( box, SWT.PUSH );
        deleteButton.setLayoutData( new GridData( SWT.FILL, SWT.TOP, true, false ) );
        deleteButton.setText( "Delete" );
        deleteButton.addSelectionListener( getSelectionListener() );
        deleteButton.setEnabled( false );
    }

    private SelectionListener getSelectionListener()
    {
        if ( buttonSelectionListener == null )
        {
            buttonSelectionListener = new SelectionListener()
            {
                public void widgetDefaultSelected( SelectionEvent e )
                {
                    widgetSelected( e );
                }

                public void widgetSelected( SelectionEvent e )
                {
                    IInputValidator validator = new IInputValidator()
                    {
                        public String isValid( String newText )
                        {
                            if ( newText.equals( "" ) || newText == null )
                            {
                                return "Profile name should be more than one character.";
                            }
                            return null;
                        }
                    };

                    if ( e.getSource().equals( addButton ) )
                    {
                        InputDialog inputDialog =
                            new InputDialog( profileTable.getParent().getShell(), "Add profile name",
                                             "Enter profile name: ", "", validator );
                        int choice = inputDialog.open();

                        if ( choice == InputDialog.OK )
                        {
                            profileContentProvider.add( inputDialog.getValue() );
                        }
                    }
                    else if ( e.getSource().equals( editButton ) )
                    {
                        int index = profileTable.getSelectionIndex();

                        InputDialog inputDialog =
                            new InputDialog( profileTable.getParent().getShell(), "Edit profile name",
                                             "Edit profile name: ", profileTable.getItem( index ).getText(), validator );
                        int choice = inputDialog.open();

                        if ( choice == InputDialog.OK )
                        {
                            profileContentProvider.changeProfileName( index, inputDialog.getValue() );
                        }
                    }
                    else if ( e.getSource().equals( deleteButton ) )
                    {
                        TableItem[] items = profileTable.getSelection();

                        for ( TableItem item : items )
                        {
                            profileContentProvider.remove( item.getText() );
                        }
                    }
                }
            };
        }

        return buttonSelectionListener;
    }

    @Override
    protected void doLoad()
    {
        profiles = new ArrayList<String>( MavenUIPreferenceManagerAdapter.getInstance().getConfiguredProfiles() );
        profileTableViewer.setInput( profiles );
    }

    @Override
    protected void doLoadDefault()
    {

    }

    @Override
    protected void doStore()
    {
        MavenUIPreferenceManagerAdapter manager = MavenUIPreferenceManagerAdapter.getInstance();
        manager.setConfiguredProfiles( new HashSet<String>( profiles ) );

        String profileViewId = "org.devzuz.q.maven.ui.views.MavenProfileView";
        MavenProfileView profileView =
            (MavenProfileView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(
                                                                                                              profileViewId );
        if ( profileView != null )
        {
            profileView.setDefaultProfile( new HashSet<String>( profiles ) );
            profileView.updateTable( true );
        }
    }

    @Override
    public int getNumberOfControls()
    {
        return 2;
    }

    private final class ProfileContentProvider
        implements IStructuredContentProvider
    {
        public void inputChanged( Viewer viewer, Object oldInput, Object newInput )
        {
            viewer.refresh();
        }

        public Object[] getElements( Object inputElement )
        {
            return profiles.toArray();
        }

        public void dispose()
        {

        }

        public void add( String profile )
        {
            profiles.add( profile );
            profileTableViewer.add( profile );
        }

        public void remove( String profile )
        {
            profiles.remove( profile );
            profileTableViewer.remove( profile );
        }

        public void changeProfileName( int index, String profileName )
        {
            profiles.set( index, profileName );
            profileTableViewer.setInput( profiles );
        }
    }

    private final class ProfileLabelProvider
        implements ITableLabelProvider
    {
        public String getColumnText( Object element, int columnIndex )
        {
            return (String) element;
        }

        public Image getColumnImage( Object element, int columnIndex )
        {
            return null;
        }

        public void addListener( ILabelProviderListener listener )
        {

        }

        public void removeListener( ILabelProviderListener listener )
        {

        }

        public boolean isLabelProperty( Object element, String property )
        {
            return false;
        }

        public void dispose()
        {

        }
    }
}
