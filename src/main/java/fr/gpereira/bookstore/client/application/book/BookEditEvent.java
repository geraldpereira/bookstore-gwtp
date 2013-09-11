package fr.gpereira.bookstore.client.application.book;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

import fr.gpereira.bookstore.model.Book;

public class BookEditEvent extends GwtEvent<BookEditEvent.BookEditHandler> {
	private Book book;
    
    protected BookEditEvent() {
        // Possibly for serialization.
    }
    
    public BookEditEvent(Book book) {
        this.book = book;
    }

    public static void fire(HasHandlers source, Book book) {
    	BookEditEvent eventInstance = new BookEditEvent(book);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, BookEditEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasBookEditHandlers extends HasHandlers {
        HandlerRegistration addErrorEventHandler(BookEditHandler handler);
    }

    public interface BookEditHandler extends EventHandler {
        public void onBookEditEvent(BookEditEvent event);
    }

    private static final Type<BookEditHandler> TYPE = new Type<BookEditHandler>();

    public static Type<BookEditHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<BookEditHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(BookEditHandler handler) {
        handler.onBookEditEvent(this);
    }

    public Book getBook() {
		return book;
	}
}