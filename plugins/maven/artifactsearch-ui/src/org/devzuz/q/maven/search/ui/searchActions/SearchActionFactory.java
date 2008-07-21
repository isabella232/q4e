package org.devzuz.q.maven.search.ui.searchActions;

import java.util.Arrays;
import java.util.List;

public class SearchActionFactory
{
    public static List<ISearchAction> getSearchActions()
    {
        ISearchAction[] searchActions = new ISearchAction[] { new AddToDependencySearchAction() };
        return Arrays.asList( searchActions );
    }
}
