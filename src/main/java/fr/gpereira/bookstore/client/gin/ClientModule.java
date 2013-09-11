package fr.gpereira.bookstore.client.gin;

import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

import fr.gpereira.bookstore.client.application.ApplicationModule;
import fr.gpereira.bookstore.client.place.NameTokens;

public class ClientModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new DefaultModule());
        install(new ApplicationModule());

        // DefaultPlaceManager Places
        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.home);
        bindConstant().annotatedWith(ErrorPlace.class).to(NameTokens.error);
        bindConstant().annotatedWith(UnauthorizedPlace.class).to(NameTokens.error);
    }
}
