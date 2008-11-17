package org.apache.maven.artifact.resolver.conflict;

/**
 * 
 * @author <a href="mailto:oleg@codehaus.org">Oleg Gusakov</a>
 * 
 * @version $Id: GraphConflictResolutionException.java 658725 2008-05-21 15:23:31Z jvanzyl $
 */

public class GraphConflictResolutionException
extends Exception
{
	private static final long serialVersionUID = 2677613140287940255L;

	public GraphConflictResolutionException()
	{
	}

	public GraphConflictResolutionException(String message)
	{
		super(message);
	}

	public GraphConflictResolutionException(Throwable cause)
	{
		super(cause);
	}

	public GraphConflictResolutionException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
