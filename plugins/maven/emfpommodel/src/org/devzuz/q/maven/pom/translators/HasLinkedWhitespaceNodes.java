package org.devzuz.q.maven.pom.translators;

import java.util.List;

import org.w3c.dom.Node;

/**
 * An adapter which has a list of formatting whitespace nodes that should be removed
 * along with the main node.
 * 
 * @author Mike Poindexter
 *
 */
public interface HasLinkedWhitespaceNodes
{
    public List<Node> getLinkedWhitespaceNodes();
    public void setLinkedWhitespaceNodes( List<Node> node );
}
