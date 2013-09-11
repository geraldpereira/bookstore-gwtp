package fr.gpereira.bookstore.client.application.book;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

import fr.gpereira.bookstore.model.Book;


public class BookAddEvent extends GwtEvent<BookAddEvent.BookAddHandler> {
	private Book book;
    
    protected BookAddEvent() {
        // Possibly for serialization.
    }
    
    public BookAddEvent(Book book) {
        this.book = book;
    }

    public static void fire(HasHandlers source, Book book) {
    	BookAddEvent eventInstance = new BookAddEvent(book);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, BookAddEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasBookQddHandlers extends HasHandlers {
        HandlerRegistration addErrorEventHandler(BookAddHandler handler);
    }

    public interface BookAddHandler extends EventHandler {
        public void onBookAddEvent(BookAddEvent event);
    }

    private static final Type<BookAddHandler> TYPE = new Type<BookAddHandler>();

    public static Type<BookAddHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<BookAddHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(BookAddHandler handler) {
        handler.onBookAddEvent(this);
    }

    public Book getBook() {
		return book;
	}
}