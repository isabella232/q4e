package org.devzuz.q.maven.pomeditor.model;

import org.devzuz.q.maven.pom.Model;
import org.devzuz.q.maven.pomeditor.ModelUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Acts as a proxy for the real root node of the tree.  Since the root may not yet exist,
 * and we cannot create it because we don't want to put elements the user didn't create in
 * our XML we use this as the root of the tree and create the model object backing it on demand.
 * 
 * @author Mike Poindexter
 *
 */
public class TreeRoot {
	private String name;
	private Model model;
	private EReference[] path;
	private EditingDomain domain;
	
	public TreeRoot( String name, Model model, EReference[] path, EditingDomain domain ) {
		super();
		this.name = name;
		this.model = model;
		this.path = path;
		this.domain = domain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Object createOrGetModelRoot()
	{
		return ModelUtil.getValue( model, path, domain, true );
	}
	
}
