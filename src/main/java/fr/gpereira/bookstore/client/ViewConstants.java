package fr.gpereira.bookstore.client;

import com.google.common.base.Optional;
import com.google.gwt.i18n.client.DateTimeFormat;

public interface ViewConstants {
	public static final Optional<String> NO_ERROR = Optional.absent();
	public static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("dd/MM/yyyy");
}
