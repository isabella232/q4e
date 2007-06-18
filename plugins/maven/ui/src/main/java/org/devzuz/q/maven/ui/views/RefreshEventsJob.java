/***************************************************************************************************
 * Copyright (c) 2007 DevZuz, Inc. (AKA Simula Labs, Inc.) All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 **************************************************************************************************/
package org.devzuz.q.maven.ui.views;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Table;

public class RefreshEventsJob
    implements Runnable
{

    private TableViewer tbv;

    public RefreshEventsJob( TableViewer tbv )
    {
        this.tbv = tbv;
    }

    public void run()
    {
        tbv.refresh();

        Table table = tbv.getTable();
        if ( table.getItemCount() * table.getItemHeight() > table.getClientArea().height )
        {
            table.select( table.getItemCount() - 1 );
        }
    }
}
