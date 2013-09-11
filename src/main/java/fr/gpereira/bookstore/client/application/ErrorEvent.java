package fr.gpereira.bookstore.client.application;

import com.google.common.base.Optional;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class ErrorEvent extends GwtEvent<ErrorEvent.ErrorHandler> {
    private Optional<String> message;
    
    protected ErrorEvent() {
        // Possibly for serialization.
    }
    
    public ErrorEvent(Optional<String> message) {
        this.message = message;
    }

    public static void fire(HasHandlers source, Optional<String> message) {
    	ErrorEvent eventInstance = new ErrorEvent(message);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, ErrorEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasErrorHandlers extends HasHandlers {
        HandlerRegistration addErrorEventHandler(ErrorHandler handler);
    }

    public interface ErrorHandler extends EventHandler {
        public void onErrorEvent(ErrorEvent event);
    }

    private static final Type<ErrorHandler> TYPE = new Type<ErrorHandler>();

    public static Type<ErrorHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<ErrorHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ErrorHandler handler) {
        handler.onErrorEvent(this);
    }
    
    public Optional<String> getMessage() {
		return message;
	}
}