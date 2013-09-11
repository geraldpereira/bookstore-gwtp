package fr.gpereira.bookstore.client.application;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

/**
 * This is the top-level view of the application. Every time another presenter wants to reveal itself,
 * {@link ApplicationView} will add its content of the target inside the {@code mainContantPanel}.
 */
public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView {
    interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    @UiField
    SimplePanel mainContentPanel;

    @UiField
    Element loadingDiv;

    @Inject
    ApplicationView(Binder binder) {
        initWidget(binder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == ApplicationPresenter.TYPE_SetMainContent) {
            mainContentPanel.setWidget(content);
        } else {
            super.setInSlot(slot, content);
        }
    }

    @Override
    public void setLoading(boolean loading) {
        this.loadingDiv.getStyle().setVisibility(loading ? Visibility.VISIBLE : Visibility.HIDDEN);
        this.mainContentPanel.setVisible(!loading);
    }
}
