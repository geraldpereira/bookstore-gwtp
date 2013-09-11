package fr.gpereira.bookstore.client.application.book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.gwtbootstrap.client.ui.Alert;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.ButtonGroup;
import com.github.gwtbootstrap.client.ui.Column;
import com.github.gwtbootstrap.client.ui.FluidRow;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.Well;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.common.base.Optional;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.text.shared.SimpleSafeHtmlRenderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import fr.gpereira.bookstore.client.ViewConstants;
import fr.gpereira.bookstore.model.Book;

public class BooksView extends ViewWithUiHandlers<BooksUiHandlers> implements BooksPresenter.MyView {
	public interface Binder extends UiBinder<Widget, BooksView> {
	}

	@UiField
	SimplePanel editBookPanel;

	@UiField
	HTMLPanel listDiv;

	@UiField
	Alert error;

	private final Map<Integer, BookWidget> bookWidgets = new HashMap<Integer, BookWidget>();

	@Inject
	public BooksView(Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void setInSlot(Object slot, IsWidget content) {
		if (slot == BooksPresenter.TYPE_SetEditBookContent){
			editBookPanel.setWidget(content);
		} else {
			super.setInSlot(slot, content);
		}
	}

	@Override
	public void setError(Optional<String> errorText) {
		if (errorText.isPresent()) {
			error.setVisible(true);
			error.setText(errorText.get());
		} else {
			error.setVisible(false);
			error.setText("");
		}
	}

	@Override
	public void setBooks(List<Book> books) {
		listDiv.clear();
		bookWidgets.clear();
		for (Book book : books) {
			bookAdded(book);
		}
	}

	@Override
	public void bookDeleted(Book book) {
		bookWidgets.remove(book.getId()).removeFromParent();
	}

	@Override
	public void bookEdited(Book book) {
		BookWidget widget = bookWidgets.get(book.getId());
		widget.setBook(book);
		bookWidgets.put(book.getId(),widget);
	}
	
	@Override
	public void bookAdded(Book book) {
		BookWidget widget = new BookWidget(book);
		bookWidgets.put(book.getId(),widget);
		listDiv.add(widget);
	}
	
	private class BookWidget extends Well {

		public BookWidget(final Book book) {
			setBook(book);
		}

		public void setBook(final Book book) {
			clear();
			add(new Heading(2, SimpleSafeHtmlRenderer.getInstance().render(book.getId() + " " + book.getTitle()).asString()));
			
			final FluidRow row = new FluidRow();
			add(row);
			
			final Column datesCol = new Column(4, new Paragraph("Release date : " + ViewConstants.DATE_FORMAT.format(book.getReleaseDate())));
			row.add(datesCol);
			
			final Column authorCol = new Column(4, new Paragraph("Author : " + book.getAuthor()));
			row.add(authorCol);
			
			final Button deleteButton = new Button("Delete", IconType.REMOVE);
			deleteButton.setType(ButtonType.DANGER);
			deleteButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					deleteButton.setEnabled(false);
					getUiHandlers().deleteBook(book);
				}
			});
			
			final Button editButton = new Button("Edit", IconType.EDIT);
			editButton.setType(ButtonType.PRIMARY);
			editButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					getUiHandlers().startEditBook(book);
				}
			});
			final Column actionsCol = new Column(4, new ButtonGroup(editButton,deleteButton));
			row.add(actionsCol);			
		}
		
	}
}
