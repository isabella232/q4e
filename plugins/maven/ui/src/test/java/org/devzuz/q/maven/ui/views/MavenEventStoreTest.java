package org.devzuz.q.maven.ui.views;

import java.util.Date;

import junit.framework.TestCase;

import org.devzuz.q.maven.embedder.EventType;
import org.devzuz.q.maven.embedder.IMavenEvent;
import org.devzuz.q.maven.embedder.Severity;

public class MavenEventStoreTest
    extends TestCase
{
    private static final int EVENTS_VIEW_SIZE = 10;

    private MavenEventStore store = new MockEventStore();

    public void testHandleEvent()
    {
        IMavenEvent event = new MockEvent();
        for ( int i = 0; i < EVENTS_VIEW_SIZE; i++ )
        {
            store.handleEvent( event );
        }
        store.handleEvent( event );
        store.handleEvent( event );
        
        assertEquals( store.getEventsBuffer().size(), EVENTS_VIEW_SIZE );
    }

    class MockEventStore
        extends MavenEventStore
    {
        protected int getEventsViewSize()
        {
            return EVENTS_VIEW_SIZE;
        }
    }

    class MockEvent
        implements IMavenEvent
    {

        public Date getCreatedDate()
        {
            return new Date();
        }

        public String getDescriptionText()
        {
            return "";
        }

        public Severity getSeverity()
        {
            return Severity.info;
        }

        public EventType getType()
        {
            return EventType.projectExecution;
        }

        public String getTypeText()
        {
            return "";
        }
    };
}
