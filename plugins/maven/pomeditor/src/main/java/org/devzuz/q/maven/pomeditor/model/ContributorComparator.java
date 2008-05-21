package org.devzuz.q.maven.pomeditor.model;

import java.util.Comparator;

import org.apache.maven.model.Contributor;

public class ContributorComparator
    implements Comparator<Contributor>
{
    public int compare( Contributor o1, Contributor o2 )
    {
        return o1.getName().compareTo(o2.getName());
    }

}
