package fr.gpereira.bookstore.client.application.book;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class BookModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(EditBookPresenter.class, EditBookPresenter.MyView.class, EditBookView.class,
        		EditBookPresenter.MyProxy.class);
        bindPresenter(BooksPresenter.class, BooksPresenter.MyView.class, BooksView.class,
                BooksPresenter.MyProxy.class);
    }
}
