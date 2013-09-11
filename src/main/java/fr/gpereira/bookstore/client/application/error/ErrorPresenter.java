
package fr.gpereira.bookstore.client.application.error;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import fr.gpereira.bookstore.client.place.NameTokens;
import fr.gpereira.bookstore.client.place.TokenParameters;

public class ErrorPresenter extends Presenter<ErrorPresenter.MyView, ErrorPresenter.MyProxy>
        implements ErrorUiHandlers {
    /**
     * {@link ErrorPresenter}'s proxy.
     */
    @ProxyCodeSplit
    @NameToken(NameTokens.error)
    public interface MyProxy extends ProxyPlace<ErrorPresenter> {
    }

    /**
     * {@link ErrorPresenter}'s view.
     */
    public interface MyView extends View, HasUiHandlers<ErrorUiHandlers> {
        void setErrorMessage(String errorMessage);
    }

    private final PlaceManager placeManager;

    private String errorMessage;

    @Inject
    ErrorPresenter(EventBus eventBus,
                      MyView view,
                      MyProxy proxy,
                      PlaceManager placeManager) {
        super(eventBus, view, proxy, RevealType.Root);
        this.placeManager = placeManager;
        getView().setUiHandlers(this);
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);
        errorMessage = request.getParameter(TokenParameters.ERROR_MESSAGE, null);
    }

    @Override
    public void onClose() {
        PlaceRequest homePlaceRequest = new PlaceRequest.Builder().nameToken(NameTokens.home).build();
        placeManager.revealPlace(homePlaceRequest);
    }

    @Override
    protected void onReset() {
        super.onReset();
        getView().setErrorMessage(errorMessage);
    }
    
    public static void showErrorPage (final String errorMessage, final PlaceManager placeManager){
        PlaceRequest responsePlaceRequest = new PlaceRequest.Builder()
                .nameToken(NameTokens.error)
                .with(TokenParameters.ERROR_MESSAGE, errorMessage)
                .build();
        placeManager.revealPlace(responsePlaceRequest);
    }
}
