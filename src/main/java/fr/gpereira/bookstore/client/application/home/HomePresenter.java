package fr.gpereira.bookstore.client.application.home;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import fr.gpereira.bookstore.client.application.ApplicationPresenter;
import fr.gpereira.bookstore.client.place.NameTokens;

public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy> implements HomeUiHandlers{
    public interface MyView extends View , HasUiHandlers<HomeUiHandlers>{
    }

    @ProxyStandard
    @NameToken(NameTokens.home)
    public interface MyProxy extends ProxyPlace<HomePresenter> {
    }

    @Inject
    public HomePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy, ApplicationPresenter.TYPE_SetMainContent);
        getView().setUiHandlers(this);
    }
    
    @Override
    protected void onReveal() {
    	super.onReveal();
    }
}
