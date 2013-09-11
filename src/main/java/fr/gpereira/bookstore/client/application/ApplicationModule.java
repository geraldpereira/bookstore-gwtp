package fr.gpereira.bookstore.client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

import fr.gpereira.bookstore.client.application.book.BookModule;
import fr.gpereira.bookstore.client.application.error.ErrorPresenter;
import fr.gpereira.bookstore.client.application.error.ErrorView;
import fr.gpereira.bookstore.client.application.home.HomePresenter;
import fr.gpereira.bookstore.client.application.home.HomeView;

public class ApplicationModule extends AbstractPresenterModule {
	
	@Override
    protected void configure() {
    	
    	install(new BookModule());
    	
        // Applicaiton Presenters
        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                      ApplicationPresenter.MyProxy.class);
        bindPresenter(HomePresenter.class, HomePresenter.MyView.class, HomeView.class, HomePresenter.MyProxy.class);
        bindPresenter(ErrorPresenter.class, ErrorPresenter.MyView.class, ErrorView.class, ErrorPresenter.MyProxy.class);
    }
}
