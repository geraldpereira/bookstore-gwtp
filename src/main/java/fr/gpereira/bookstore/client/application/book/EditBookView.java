package fr.gpereira.bookstore.client.application.book;

import java.util.Collections;
import java.util.List;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.ControlGroup;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.HelpInline;
import com.github.gwtbootstrap.client.ui.IntegerBox;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.constants.ControlGroupType;
import com.github.gwtbootstrap.datepicker.client.ui.DateBoxAppended;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.EditorError;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import fr.gpereira.bookstore.client.application.book.EditBookPresenter.Mode;

public class EditBookView extends ViewWithUiHandlers<EditBookUiHandlers> implements EditBookPresenter.MyView {
	public interface Binder extends UiBinder<Widget, EditBookView> {
	}

	@UiField
	Button submitButton;

	@UiField
	Heading editBookHeading;
	
	@UiField
	ControlGroup idControlGroup;
	@UiField
	IntegerBox id;

	@UiField
	ControlGroup titleControlGroup;
	@UiField
	TextBox title;
	@UiField
	@Editor.Ignore
	HelpInline titleHelp;

	@UiField
	ControlGroup releaseDateControlGroup;
	@UiField
	DateBoxAppended releaseDate;
	@UiField
	@Editor.Ignore
	HelpInline releaseDateHelp;

	@UiField
	ControlGroup authorControlGroup;
	@UiField
	TextBox author;
	@UiField
	@Editor.Ignore
	HelpInline authorHelp;

	@Inject
	public EditBookView(EventBus eventBus, Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
		
		title.setControlGroup(titleControlGroup);
		title.setErrorLabel(titleHelp);
		
		author.setControlGroup(authorControlGroup);
		author.setErrorLabel(authorHelp);

	}

	@UiHandler("submitButton")
	public void onSubmitForm(ClickEvent e) {
		getUiHandlers().submit();
	}
	
	@UiHandler("cancelButton")
	public void onCancelForm(ClickEvent e) {
		getUiHandlers().cancel();
	}

	@Override
	public void setLoading(boolean loading) {
		submitButton.setEnabled(!loading);
	}

	@Override
	public void setErrors(List<EditorError> errors) {
		releaseDateControlGroup.setType(ControlGroupType.NONE);
		releaseDateHelp.setText(null);

		for (EditorError error : errors) {
			if (error.getPath().equals("releaseDate")) {
				releaseDateControlGroup.setType(ControlGroupType.ERROR);
				releaseDateHelp.setText(error.getMessage());
			} 
		}
	}

	@Override
	public void setMode(Mode mode) {
		switch (mode) {
		case ADD:
			idControlGroup.setVisible(false);
			submitButton.setText("Add");
			editBookHeading.setText("Add");
			break;
		case EDIT:
			idControlGroup.setVisible(true);
			submitButton.setText("Edit");
			editBookHeading.setText("Edit");
			break;
		}
	}

	@Override
	public void clear() {
		setLoading(false);
		setErrors(Collections.<EditorError> emptyList());
	}
}
