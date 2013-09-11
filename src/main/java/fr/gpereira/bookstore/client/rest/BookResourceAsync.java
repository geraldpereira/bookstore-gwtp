package fr.gpereira.bookstore.client.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;

import com.google.gwt.core.client.GWT;

import fr.gpereira.bookstore.model.Book;

public interface BookResourceAsync extends ResourceAsync {

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/add")
	void addBook(Book book, MethodCallback<Integer> id);

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/list")
	void listBooks(MethodCallback<List<Book>> books);

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/edit")
	void editBook(Book book, MethodCallback<Void> nothing);
	
	@DELETE
	@Path("/delete/{id}")
	void deleteBook(@PathParam("id") Integer bookId, MethodCallback<Void> nothing);
	
	/**
	 * Utility class to get the instance of the Rest Service
	 */
	public static final class Util {

		private static BookResourceAsync instance;

		public static final BookResourceAsync get() {
			if (instance == null) {
				instance = GWT.create(BookResourceAsync.class);
				((RestServiceProxy) instance).setResource(new Resource(PROXY_BASE_URL + "book/"));
			}
			return instance;
		}

		private Util() {
			// Utility class should not be instantiated
		}
	}
}
