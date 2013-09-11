package fr.gpereira.bookstore.client.application.book;

import java.util.List;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.common.base.Optional;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;

import fr.gpereira.bookstore.client.ViewConstants;
import fr.gpereira.bookstore.client.application.ApplicationPresenter;
import fr.gpereira.bookstore.client.application.ErrorEvent;
import fr.gpereira.bookstore.client.application.book.BookAddEvent.BookAddHandler;
import fr.gpereira.bookstore.client.application.book.BookEditEvent.BookEditHandler;
import fr.gpereira.bookstore.client.application.book.EditBookPresenter.Mode;
import fr.gpereira.bookstore.client.application.error.ErrorPresenter;
import fr.gpereira.bookstore.client.place.NameTokens;
import fr.gpereira.bookstore.client.rest.BookResourceAsync;
import fr.gpereira.bookstore.model.Book;

public class BooksPresenter extends Presenter<BooksPresenter.MyView, BooksPresenter.MyProxy> implements BooksUiHandlers, BookEditHandler, BookAddHandler, ErrorEvent.ErrorHandler {
	public interface MyView extends View, HasUiHandlers<BooksUiHandlers> {
		void setBooks(List<Book> books);

		void setError(Optional<String> errorMessage);

		void bookDeleted(Book book);

		void bookEdited(Book book);

		void bookAdded(Book book);
	}

	@ProxyStandard
	@NameToken(NameTokens.books)
	public interface MyProxy extends ProxyPlace<BooksPresenter> {
	}
	
	@ContentSlot
	public static final Type<RevealContentHandler<?>> TYPE_SetEditBookContent = new Type<RevealContentHandler<?>>();

	private final PlaceManager placeManager;
	private final EditBookPresenter editBookPresenter;
	
	@Inject
	public BooksPresenter(EventBus eventBus, MyView view, MyProxy proxy, PlaceManager manager, EditBookPresenter editBookPresenter) {
		super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);
		this.placeManager = manager;
		this.editBookPresenter = editBookPresenter;
		getView().setUiHandlers(this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		registerHandler(getEventBus().addHandler(ErrorEvent.getType(), this));
		registerHandler(getEventBus().addHandler(BookEditEvent.getType(), this));
		registerHandler(getEventBus().addHandler(BookAddEvent.getType(), this));
		setInSlot(TYPE_SetEditBookContent, editBookPresenter);
	}
	
	@Override
	protected void onReset() {
		super.onReset();
		getView().setError(ViewConstants.NO_ERROR);
	}
	
	
	@Override
	public boolean useManualReveal() {
		return true;
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		BookResourceAsync.Util.get().listBooks(new MethodCallback<List<Book>>() {
			@Override
			public void onFailure(Method method, Throwable e) {
				GWT.log("Failed to load books", e);
				getProxy().manualRevealFailed();
				ErrorPresenter.showErrorPage(e.getMessage(), placeManager);
			}

			@Override
			public void onSuccess(Method method, List<Book> response) {
				getView().setBooks(response);
				getProxy().manualReveal(BooksPresenter.this);
			}
		});
	}

	@Override
	public void deleteBook(final Book book) {
		editBookPresenter.cancel();
		BookResourceAsync.Util.get().deleteBook(book.getId(), new MethodCallback<Void>() {
			@Override
			public void onFailure(Method method, Throwable e) {
				getView().setError(Optional.of(method.getResponse().getText()));
			}

			@Override
			public void onSuccess(Method method, Void response) {
				getView().setError(ViewConstants.NO_ERROR);
				getView().bookDeleted(book);
			}
		});
	}

	@Override
	public void startEditBook(Book book) {
		editBookPresenter.setMode(Mode.EDIT);
		editBookPresenter.setBook(book);
	}

	@Override
	public void onErrorEvent(ErrorEvent event) {
		getView().setError(event.getMessage());
	}

	@Override
	public void onBookAddEvent(BookAddEvent event) {
		getView().bookAdded(event.getBook());
	}

	@Override
	public void onBookEditEvent(BookEditEvent event) {
		getView().bookEdited(event.getBook());
	}
}
