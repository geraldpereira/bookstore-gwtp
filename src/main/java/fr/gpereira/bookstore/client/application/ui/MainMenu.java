package fr.gpereira.bookstore.client.application.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * A simple menu that can be reused.
 */
public class MainMenu extends Composite {
    interface MainMenuUiBinder extends UiBinder<Widget, MainMenu> {
    }

    private static MainMenuUiBinder uiBinder = GWT.create(MainMenuUiBinder.class);

    public MainMenu() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
