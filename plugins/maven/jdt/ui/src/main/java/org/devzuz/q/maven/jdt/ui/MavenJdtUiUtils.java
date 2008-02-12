package org.devzuz.q.maven.jdt.ui;

import org.devzuz.q.maven.ui.views.MavenEventView;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class MavenJdtUiUtils
{
    public static void runMavenEventView()
    {
        IWorkbenchWindow ww = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        IWorkbenchPage wp = null;
        boolean isDisplayThread = true;
        
        // if no active workbench window, try to cycle thru all the workbenches to see 
        // who can offer us an active page or if non exist, a page.
        // This is important because if the main window is not on focus, there is no
        // active workbench window.
        if( ww == null )
        {
            isDisplayThread = false;
            IWorkbenchWindow[] workbenches = PlatformUI.getWorkbench().getWorkbenchWindows();
            for( IWorkbenchWindow workbench : workbenches )
            {
                IWorkbenchPage activePage = workbench.getActivePage();
                if( activePage != null )
                {
                    ww = workbench;
                    wp = activePage;
                    break;
                }
            }
            
            if ( wp == null )
            {
                for ( IWorkbenchWindow workbench : workbenches )
                {
                    if ( workbench.getPages().length > 0 )
                    {
                        ww = workbench;
                        wp = workbench.getPages()[0];
                        break;
                    }
                }
            }
        }
        else
        {
            wp = ww.getActivePage();
            if( ( wp == null ) && ( ww.getPages().length > 0 ) )
            {
                wp = ww.getPages()[0];
            }
        }
        
        if( ww != null )
        {
            if( wp != null )
            {
                if( isDisplayThread )
                {
                    showView( wp , MavenEventView.VIEW_ID );
                }
                else
                {
                    final IWorkbenchPage page = wp;
                    ww.getShell().getDisplay().asyncExec( new Runnable() 
                    {
                        public void run()
                        {
                            showView( page , MavenEventView.VIEW_ID );
                        }
                    });
                }
            }
            else
            {
                MavenJdtUiActivator.getLogger().info( "Unable to open Maven Event View, IWorkbenchPage is null" );
            }
        }
        else
        {
            MavenJdtUiActivator.getLogger().info( "Unable to open Maven Event View, IWorkbenchWindow is null" );
        }
    }

    private static void showView( IWorkbenchPage page , String viewId )
    {
        try
        {
            page.showView( viewId );
        }
        catch ( PartInitException e )
        {
            MavenJdtUiActivator.getLogger().log( "Unable to open Maven Event View", e );
            return;
        }
    }
}
