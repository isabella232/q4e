package org.devzuz.q.maven.pomeditor.components;

import java.util.Iterator;
import java.util.List;

import org.devzuz.q.maven.pom.Dependency;
import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pom.PomFactory;
import org.devzuz.q.maven.pom.PomPackage;
import org.devzuz.q.maven.pomeditor.Messages;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.devzuz.q.maven.ui.dialogs.AddEditDependencyDialog;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;

public class DependencyTableComponent extends AbstractComponent
{
    private Table dependenciesTable;

    private Button addButton;

    private Button editButton;

    private Button removeButton;

    public int selectedIndex;
    
    private Model model;
    
    private EStructuralFeature[] path;
    
    private EditingDomain domain;
    
    private WritableValue selectedDependency;

    public DependencyTableComponent( Composite parent, int style,
    		Model model, EStructuralFeature[] path, EditingDomain domain, 
    		WritableValue selectedDependency )
    {
        super( parent, style );

        this.model = model;
        this.path = path;
        this.domain = domain;
        this.selectedDependency = selectedDependency;

        setLayout( new GridLayout( 2, false ) );

        dependenciesTable = new Table( this, SWT.BORDER | SWT.FULL_SELECTION | SWT.SINGLE );
        dependenciesTable.setLayoutData( new GridData( GridData.FILL, GridData.FILL, true, true ) );
        dependenciesTable.setLinesVisible( true );
        dependenciesTable.setHeaderVisible( true );

        DependenciesTableListener tableListener = new DependenciesTableListener();
        dependenciesTable.addSelectionListener( tableListener );

        TableColumn groupIdColumn = new TableColumn( dependenciesTable, SWT.BEGINNING, 0 );
        groupIdColumn.setText( Messages.MavenPomEditor_MavenPomEditor_GroupId );
        groupIdColumn.setWidth( 125 );

        TableColumn artifactIdColumn = new TableColumn( dependenciesTable, SWT.BEGINNING, 1 );
        artifactIdColumn.setText( Messages.MavenPomEditor_MavenPomEditor_ArtifactId );
        artifactIdColumn.setWidth( 125 );

        TableColumn versionColumn = new TableColumn( dependenciesTable, SWT.BEGINNING, 2 );
        versionColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Version );
        versionColumn.setWidth( 50 );

        TableColumn typeColumn = new TableColumn( dependenciesTable, SWT.BEGINNING, 3 );
        typeColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Type );
        typeColumn.setWidth( 50 );

        TableColumn classifierColumn = new TableColumn( dependenciesTable, SWT.BEGINNING, 4 );
        classifierColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Classifier );
        classifierColumn.setWidth( 75 );

        TableColumn scopeColumn = new TableColumn( dependenciesTable, SWT.BEGINNING, 5 );
        scopeColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Scope );
        scopeColumn.setWidth( 75 );

        TableColumn systemPathColumn = new TableColumn( dependenciesTable, SWT.BEGINNING, 6 );
        systemPathColumn.setText( Messages.MavenPomEditor_MavenPomEditor_SystemPath );
        systemPathColumn.setWidth( 125 );

        TableColumn optionalColumn = new TableColumn( dependenciesTable, SWT.BEGINNING, 7 );
        optionalColumn.setText( Messages.MavenPomEditor_MavenPomEditor_Optional );
        optionalColumn.setWidth( 50 );
        
        ModelUtil.bindTable( 
        		model, 
        		path, 
        		new EStructuralFeature[]{ PomPackage.Literals.DEPENDENCY__GROUP_ID, PomPackage.Literals.DEPENDENCY__ARTIFACT_ID, PomPackage.Literals.DEPENDENCY__VERSION, PomPackage.Literals.DEPENDENCY__TYPE, PomPackage.Literals.DEPENDENCY__CLASSIFIER, PomPackage.Literals.DEPENDENCY__SYSTEM_PATH, PomPackage.Literals.DEPENDENCY__SCOPE, PomPackage.Literals.DEPENDENCY__OPTIONAL }, 
        		dependenciesTable, 
        		domain);

        Composite container2 = new Composite( this, SWT.NULL );
        container2.setLayoutData( new GridData( GridData.CENTER, GridData.BEGINNING, false, true ) );
        RowLayout layout = new RowLayout( SWT.VERTICAL );
        layout.fill = true;
        container2.setLayout( layout );

        addButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        addButton.setText( Messages.MavenPomEditor_MavenPomEditor_AddButton );
        AddButtonListener addButtonListener = new AddButtonListener();
        addButton.addSelectionListener( addButtonListener );

        editButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        editButton.setText( Messages.MavenPomEditor_MavenPomEditor_EditButton );
        EditButtonListener editButtonListener = new EditButtonListener();
        editButton.addSelectionListener( editButtonListener );
        editButton.setEnabled( false );

        removeButton = new Button( container2, SWT.PUSH | SWT.CENTER );
        removeButton.setText( Messages.MavenPomEditor_MavenPomEditor_RemoveButton );
        RemoveButtonListener removeButtonListener = new RemoveButtonListener();
        removeButton.addSelectionListener( removeButtonListener );
        removeButton.setEnabled( false );

    }

    private class DependenciesTableListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            TableItem[] item = dependenciesTable.getSelection();

            if ( ( item != null ) && ( item.length > 0 ) )
            {
                addButton.setEnabled( true );
                removeButton.setEnabled( true );
                editButton.setEnabled( true );

                if ( dependenciesTable.getSelectionIndex() >= 0 )
                {
                    selectedIndex = dependenciesTable.getSelectionIndex();
                    List<Dependency> dependenciesList = (List<Dependency>) ModelUtil.getValue( model, path, domain, false );
                    selectedDependency.setValue( dependenciesList == null ? null : dependenciesList.get( selectedIndex ) );
                }
            }
        }

    }

    private class AddButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            AddEditDependencyDialog addDialog = AddEditDependencyDialog.getAddEditDependencyDialog();

            if ( addDialog.openWithDependency( null ) == Window.OK )
            {
                if ( !artifactAlreadyExist( addDialog.getGroupId(), addDialog.getArtifactId() ) )
                {
                    Dependency dependency = PomFactory.eINSTANCE.createDependency();

                    dependency.setGroupId( addDialog.getGroupId() );
                    dependency.setArtifactId( addDialog.getArtifactId() );
                    dependency.setVersion( nullIfBlank( addDialog.getVersion() ) );
                    dependency.setType( nullIfBlank( addDialog.getType() ) );
                    dependency.setClassifier( nullIfBlank( addDialog.getClassifier() ) );
                    dependency.setScope( nullIfBlank( addDialog.getScope() ) );
                    dependency.setSystemPath( nullIfBlank( addDialog.getSystemPath() ) );
                    dependency.setOptional( addDialog.isOptional() );

                    List<Dependency> dependenciesList = (List<Dependency>) ModelUtil.getValue( model, path, domain, true );
                    dependenciesList.add( dependency );
                }
                else
                {
                    String dependency =
                        addDialog.getGroupId() + ":" + addDialog.getArtifactId() + ":" + addDialog.getVersion();
                    MessageDialog.openWarning( getParent().getShell(), "Existing dependency found.", dependency
                                    + " already exists." );
                }
            }
        }
    }

    private class EditButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
            AddEditDependencyDialog addDialog = AddEditDependencyDialog.getAddEditDependencyDialog();
            Dependency oldDependency = (Dependency) selectedDependency.getValue();
            addDialog.setGroupId( oldDependency.getGroupId() );
            addDialog.setArtifactId( oldDependency.getArtifactId() );
            addDialog.setVersion( oldDependency.getVersion() );
            addDialog.setScope( oldDependency.getScope() );
            addDialog.setType( oldDependency.getType() );
            addDialog.setClassifier( oldDependency.getClassifier() );
            addDialog.setSystemPath( oldDependency.getSystemPath() );
            addDialog.setOptional( oldDependency.isOptional() );
            if ( addDialog.open() == Window.OK )
            {
                Dependency dependency = PomFactory.eINSTANCE.createDependency();

                dependency.setGroupId( addDialog.getGroupId() );
                dependency.setArtifactId( addDialog.getArtifactId() );
                dependency.setVersion( nullIfBlank( addDialog.getVersion() ) );
                dependency.setType( nullIfBlank( addDialog.getType() ) );
                dependency.setClassifier( nullIfBlank( addDialog.getClassifier() ) );
                dependency.setScope( nullIfBlank( addDialog.getScope() ) );
                dependency.setSystemPath( nullIfBlank( addDialog.getSystemPath() ) );
                dependency.setOptional( addDialog.isOptional() );

                List<Dependency> dependenciesList = (List<Dependency>) ModelUtil.getValue( model, path, domain, true );
                if ( artifactAlreadyExist( addDialog.getGroupId(), addDialog.getArtifactId() ) )
                {
                    // groupId and artifactId are unmodified
                    if ( ( oldDependency.getGroupId().equalsIgnoreCase( dependency.getGroupId() ) )
                                    && ( oldDependency.getArtifactId().equalsIgnoreCase( dependency.getArtifactId() ) ) )
                    {
                        dependenciesList.remove( oldDependency );

                        dependenciesList.add( dependency );
                    }
                    // this means user put in a duplicate artifact
                    else
                    {
                        MessageBox mesgBox =
                            new MessageBox( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                                            SWT.ICON_ERROR | SWT.OK );
                        mesgBox.setMessage( "Dependency already exists." );
                        mesgBox.setText( "Saving Dependency Error" );

                        mesgBox.open();
                    }
                }
                else
                {
                    dependenciesList.remove( oldDependency );

                    dependenciesList.add( dependency );
                }
            }

        }
    }

    private class RemoveButtonListener extends SelectionAdapter
    {
        public void widgetSelected( SelectionEvent e )
        {
        	List<Dependency> dependenciesList = (List<Dependency>) ModelUtil.getValue( model, path, domain, true );
            dependenciesList.remove( selectedDependency.getValue() );
            selectedDependency.setValue( null );
            
        }
    }

    private boolean artifactAlreadyExist( String groupId, String artifactId )
    {
    	List<Dependency> dependenciesList = (List<Dependency>) ModelUtil.getValue( model, path, domain, true );
        for ( Iterator<Dependency> it = dependenciesList.iterator(); it.hasNext(); )
        {
            Dependency artifact = it.next();
            if ( artifact.getGroupId().equals( groupId ) && artifact.getArtifactId().equals( artifactId ) )
            {
                return true;
            }
        }

        return false;
    }

    public void addDependencyTableListener( SelectionListener listener )
    {
        dependenciesTable.addSelectionListener( listener );
    }

    public void removeDependencyTableListener( SelectionListener listener )
    {
        dependenciesTable.removeSelectionListener( listener );
    }

    public void setAddButtonEnabled( boolean enabled )
    {
        addButton.setEnabled( enabled );
    }

    public void setEditButtonEnabled( boolean enabled )
    {
        editButton.setEnabled( enabled );
    }

    public void setRemoveButtonEnabled( boolean enabled )
    {
        removeButton.setEnabled( enabled );
    }

    public void addAddButtonListener( SelectionListener listener )
    {
        addButton.addSelectionListener( listener );
    }

    public void addEditButtonListener( SelectionListener listener )
    {
        editButton.addSelectionListener( listener );
    }

    public void addRemoveButtonListener( SelectionListener listener )
    {
        removeButton.addSelectionListener( listener );
    }

    public void removeAddButtonListener( SelectionListener listener )
    {
        addButton.removeSelectionListener( listener );
    }

    public void removeEditButtonListener( SelectionListener listener )
    {
        editButton.removeSelectionListener( listener );
    }

    public void removeRemoveButtonListener( SelectionListener listener )
    {
        removeButton.removeSelectionListener( listener );
    }

}
