package fr.gpereira.bookstore.client.application.book;

import com.gwtplatform.mvp.client.UiHandlers;

import fr.gpereira.bookstore.model.Book;

public interface BooksUiHandlers extends UiHandlers {
	void deleteBook(Book book);
	void startEditBook(Book book);
}
