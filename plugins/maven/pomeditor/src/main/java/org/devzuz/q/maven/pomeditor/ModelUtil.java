package org.devzuz.q.maven.pomeditor;

import org.devzuz.q.maven.pom.PomFactory;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;

public class ModelUtil {
	public static EObject createOrGetContainer( EObject root, EReference[] path, EditingDomain domain )
	{
		EObject ret = root;
		for (EReference reference : path) 
		{
			EObject next = (EObject) ret.eGet( reference );
			if( null == next )
			{
				next = PomFactory.eINSTANCE.create( reference.getEReferenceType() );
				Command command = SetCommand.create( domain, ret, reference, next );
				domain.getCommandStack().execute( command );
			}
			ret = next;
		}
		return ret;
	}
	
	public static Object getValue( EObject root, EStructuralFeature[] path, EditingDomain domain, boolean create )
	{
		Object ret = root;
		for (EStructuralFeature feature : path) 
		{
			Object next = ((EObject) ret ).eGet( feature );
			if( null == next )
			{
				if( create && ret instanceof EObject && feature instanceof EReference )
				{
					EReference reference = (EReference) feature;
					next = PomFactory.eINSTANCE.create( reference.getEReferenceType() );
					Command command = SetCommand.create( domain, ret, reference, next );
					domain.getCommandStack().execute( command );
				}
				else
				{
					break;
				}
			}
			ret = next;
		}
		return ret;
	}
	
	public static void bindTable( final EObject root, final EStructuralFeature[] path, EStructuralFeature[] columns, Table table, final EditingDomain domain ) 
	{
		TableViewer viewer = new TableViewer( table );
		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		viewer.setContentProvider( contentProvider );
		
		IObservableValue ctx = new WritableValue();
		ctx.setValue( root );
		for ( int i = 0; i < path.length - 1; i++ )
		{
			ctx = EMFEditObservables.observeDetailValue( Realm.getDefault(), domain, ctx, path[i] );
		}
		
		IObservableList list = EMFEditObservables.observeDetailList( Realm.getDefault(), domain, ctx, path[path.length - 1] );
		viewer.setInput( list );
		
		if( columns != null )
		{
			IObservableMap[] labels = EMFEditObservables.observeMaps(domain, contentProvider.getKnownElements(), columns );
		
			viewer.setLabelProvider( new ObservableMapLabelProvider(labels) );
		}
		else
		{
			viewer.setLabelProvider( new ILabelProvider() {
				@Override
				public void addListener(ILabelProviderListener listener) {} 
				
				@Override
				public void dispose() {}
				
				@Override
				public Image getImage(Object element) 
				{ 
					return null; 
				}
				
				@Override
				public String getText(Object element) {
					return element.toString();
				}
				@Override
				public boolean isLabelProperty(Object element, String property) 
				{
					return false;
				}
				@Override
				public void removeListener(ILabelProviderListener listener) {}
				
			});
		}
		
	}
	
	public static void bind( final EObject root, final EStructuralFeature[] path, IObservableValue uiValue, final EditingDomain domain, DataBindingContext dbc )
	{
		IObservableValue ctx = new WritableValue();
		ctx.setValue( root );
		for (EStructuralFeature feature : path) 
		{
			ctx = EMFEditObservables.observeDetailValue( Realm.getDefault(), domain, ctx, feature );
		}
		ctx.getValue();
		dbc.bindValue(uiValue, ctx, new UpdateValueStrategy()
		{
			@Override
			protected IStatus doSet(IObservableValue observableValue, Object value) 
			{
				getValue(root, path, domain, true);
				return super.doSet(observableValue, value);
			}
		}, null);
	}
}
