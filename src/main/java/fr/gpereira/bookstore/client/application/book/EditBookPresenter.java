package fr.gpereira.bookstore.client.application.book;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.common.base.Optional;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.validation.client.impl.Validation;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import fr.gpereira.bookstore.client.application.ApplicationPresenter;
import fr.gpereira.bookstore.client.application.ErrorEvent;
import fr.gpereira.bookstore.client.place.NameTokens;
import fr.gpereira.bookstore.client.rest.BookResourceAsync;
import fr.gpereira.bookstore.model.Book;
import fr.gpereira.bookstore.model.validation.CreateChecks;
import fr.gpereira.bookstore.model.validation.UpdateChecks;

public class EditBookPresenter extends Presenter<EditBookPresenter.MyView, EditBookPresenter.MyProxy> implements EditBookUiHandlers {
	public interface MyView extends View, HasUiHandlers<EditBookUiHandlers>, Editor<Book> {
		void setMode(Mode mode);
		
		void setLoading(boolean loading);

		void setErrors(List<EditorError> errors);

		void clear();
	}
	
	enum Mode {
		ADD {
			@Override
			void submit(final EditBookPresenter presenter,final Book book) {
				// Validate the bean
				ArrayList<ConstraintViolation<?>> violations = getViolations(book, CreateChecks.class, Default.class);
				
				// Apply the errors, the messages are automatically displayed
				if (!violations.isEmpty()) {
					if (presenter.driver.setConstraintViolations(violations)) {
						// Well, not all messages in fact
						presenter.getView().setErrors(presenter.driver.getErrors());
					}
					presenter.getView().setLoading(false);
				} else {
					BookResourceAsync.Util.get().addBook(book, new MethodCallback<Integer>() {
						@Override
						public void onFailure(Method method, Throwable e) {
							ErrorEvent.fire(presenter, Optional.of("Failed to create book: " + e.getMessage()));
							presenter.setMode(Mode.ADD);
							presenter.getView().setLoading(false);							
						}

						@Override
						public void onSuccess(Method method, Integer response) {
							book.setId(response);
							BookAddEvent.fire(presenter, book);
							presenter.cancel();	
						}
					});
				}
			}
		},
		EDIT {
			@Override
			void submit(final EditBookPresenter presenter,final  Book book) {
				// Validate the bean
				ArrayList<ConstraintViolation<?>> violations = getViolations(book, UpdateChecks.class, Default.class);

				// Apply the errors, the messages are automatically displayed
				if (!violations.isEmpty()) {
					if (presenter.driver.setConstraintViolations(violations)) {
						// Well, not all messages in fact
						presenter.getView().setErrors(presenter.driver.getErrors());
					}
					presenter.getView().setLoading(false);
				} else {
					BookResourceAsync.Util.get().editBook(book, new MethodCallback<Void>() {
						@Override
						public void onFailure(Method method, Throwable e) {
							ErrorEvent.fire(presenter, Optional.of("Failed to edit book: " + e.getMessage()));
							presenter.setMode(Mode.ADD);
							presenter.getView().setLoading(false);							
						}

						@Override
						public void onSuccess(Method method, Void response) {
							BookEditEvent.fire(presenter, book);
							presenter.cancel();							
						}
					});
				}
				
			}
		};
		abstract void submit(EditBookPresenter presenter, Book book);
		
		private static ArrayList<ConstraintViolation<?>> getViolations(final Book book, Class<?>... groups){
			// Validate the bean
			Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
			ArrayList<ConstraintViolation<?>> violations = new ArrayList<ConstraintViolation<?>>(validator.validate(book, groups));
			return violations;
		}
	}

	@ProxyStandard
	@NameToken(NameTokens.books)
	public interface MyProxy extends ProxyPlace<EditBookPresenter> {
	}

	interface Driver extends SimpleBeanEditorDriver<Book, EditBookView> {
	}

	private final Driver driver;
	private Mode mode;

	@Inject
	public EditBookPresenter(EventBus eventBus, MyView view, MyProxy proxy, Driver driver) {
		super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);
		getView().setUiHandlers(this);
		this.driver = driver;
		this.driver.initialize((EditBookView) getView());
	}
	
	@Override
	protected void onReveal() {
		super.onReveal();
		setMode(Mode.ADD);		
	}
	
	public void setBook(Book book) {
		driver.edit(book);
	}

	public void setMode(Mode mode) {
		this.mode = mode;
		getView().setMode(mode);
	}

	@Override
	protected void onReset() {
		super.onReset();
		Book book = new Book();
		driver.edit(book);
	}

	@Override
	public void submit() {
		getView().setLoading(true);
		final Book book = driver.flush();
		mode.submit(this, book);
	}

	@Override
	public void cancel() {
		driver.edit(new Book());
		setMode(Mode.ADD);
		getView().clear();
	}
	
}
